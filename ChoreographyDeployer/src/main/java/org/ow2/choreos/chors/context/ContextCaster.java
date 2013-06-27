/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ContextCaster {

    private Logger logger = Logger.getLogger(ContextCaster.class);
    private static final int MAX_TRIALS = 5;
    private static final int DELAY_BETWEEN_TRIALS = 30000;

    private ContextSenderFactory senderFactory;

    public ContextCaster(ContextSenderFactory senderFactory) {
        this.senderFactory = senderFactory;
    }

    public void cast(Choreography chor) {
        logger.info("Passing context to deployed services on choreograghy " + chor.getId());
        Map<String, DeployableService> deployedServices = chor.getMapOfDeployableServicesBySpecNames();
        for (Map.Entry<String, DeployableService> entry : deployedServices.entrySet()) {
            Service deployed = entry.getValue();
            castContext(deployedServices, deployed);
        }
    }

    private void castContext(Map<String, DeployableService> deployedServices, Service deployed) {

        ServiceSpec spec = deployed.getSpec();
        if (spec.getDependencies() == null)
            return;

        List<String> serviceUris = deployed.getUris();

        for (ServiceDependency dep : spec.getDependencies()) {

            Service providerService = deployedServices.get(dep.getServiceSpecName());

            if (providerService == null) {
                logger.error("Service " + dep.getServiceSpecName() + " (" + spec.getName()
                        + "dependency) not deployed. Not goint to pass this context to " + spec.getName());
            } else {
                try {
                    trySendContext(spec, serviceUris, dep, providerService);
                } catch (ContextNotSentException e) {
                    logger.error("Could not setInvocationAddress to " + e.getServiceUri());
                }
            }
        }
    }

    /**
     * Get URIs from service that will be used in the setInvocationAddress.
     * 
     * Implementation: if possible, uses the SOAP bus URI; if not use the
     * nativeUri
     * 
     * @param providerService
     * @return
     */
    private List<String> getUris(Service providerService) {

        List<String> uris = new ArrayList<String>();
        for (ServiceInstance instance : ((DeployableService) providerService).getInstances()) {
            String proxifiedUri = instance.getBusUri(ServiceType.SOAP);
            if (proxifiedUri != null) {
                uris.add(proxifiedUri);
            } else {
                uris.add(instance.getNativeUri());
            }
        }
        return uris;
    }

    private void trySendContext(ServiceSpec consumerServiceSpec, List<String> consumerServiceInstanceUris,
            ServiceDependency consumerServiceDependency, Service providerService) throws ContextNotSentException {

        List<String> providerUris = this.getUris(providerService);
        int trial = 0;

        for (String serviceUri : consumerServiceInstanceUris) {
            while (trial < MAX_TRIALS) {
                try {
                    ServiceType serviceType = consumerServiceSpec.getServiceType();
                    ContextSender sender = senderFactory.getNewInstance(serviceType);
                    sender.sendContext(serviceUri, consumerServiceDependency.getServiceSpecRole(),
                            consumerServiceDependency.getServiceSpecName(), providerUris);
                    logger.debug(consumerServiceSpec.getName() + " has received "
                            + consumerServiceDependency.getServiceSpecName() + " as "
                            + consumerServiceDependency.getServiceSpecRole() + ": " + providerUris);
                    return;
                } catch (ContextNotSentException e) {
                    trial = tryRecoveryNotSentContext(consumerServiceSpec, consumerServiceDependency, trial);
                    logger.error("Trial=" + trial);
                }
            }
            throw new ContextNotSentException(serviceUri, consumerServiceDependency.getServiceSpecRole(),
                    consumerServiceDependency.getServiceSpecName(), providerUris);
        }
    }

    private int tryRecoveryNotSentContext(ServiceSpec spec, ServiceDependency dep, int trial) {
        trial++;
        if (trial == MAX_TRIALS) {
            logger.error("Could not set " + dep.getServiceSpecName() + " as " + dep.getServiceSpecRole() + " to "
                    + spec.getName());
        }
        try {
            Thread.sleep(DELAY_BETWEEN_TRIALS);
        } catch (InterruptedException e1) {
            logger.error("Exception at sleeping. This should not happen.");
        }
        return trial;
    }
}
