/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.Proxification;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public class ContextCaster {

    private final int timeout;
    private final int trials;
    private final int pauseBetweenTrials;

    private Choreography chor;
    private DeployableService consumer, provider;
    private Map<String, DeployableService> deployedServicesMap;
    private String providerRole, providerName, consumerName;
    private ExecutorService executor;

    private Logger logger = Logger.getLogger(ContextCaster.class);

    public ContextCaster(Choreography chor) {
        this.timeout = TimeoutsAndTrials.get("SET_INVOCATION_ADDRESS_TIMEOUT");
        this.trials = TimeoutsAndTrials.get("SET_INVOCATION_ADDRESS_TRIALS");
        this.pauseBetweenTrials = TimeoutsAndTrials.get("SET_INVOCATION_ADDRESS_PAUSE");
        this.chor = chor;
    }

    public void cast() {
        if (!inputOK()) {
            logger.warn("No deployed services founf. Not going to cast cotnext.");
            return;
        }
        int nThreads = chor.getDeployableServices().size();
        this.executor = Executors.newFixedThreadPool(nThreads); 
        logger.info("Passing context to deployed services on choreograghy " + chor.getId());
        deployedServicesMap = chor.getMapOfDeployableServicesBySpecNames();
        for (DeployableService service : chor.getDeployableServices()) {
            consumer = service;
            consumerName = consumer.getSpec().getName();
            castContextsToConsumer();
        }
        waitContextCasting();
    }

    private boolean inputOK() {
        return chor.getDeployableServices() != null && !chor.getDeployableServices().isEmpty();
    }
    
    private void waitContextCasting() {
        int totalTimeout = trials * (timeout + pauseBetweenTrials);
        totalTimeout += 0.3 * totalTimeout;
        Concurrency.waitExecutor(executor, totalTimeout, TimeUnit.SECONDS, logger, "Could not wait context casting");
    }

    private void castContextsToConsumer() {
        if (consumer.getSpec().getDependencies() == null)
            return;
        for (ServiceDependency dep : consumer.getSpec().getDependencies()) {
            providerName = dep.getServiceSpecName();
            providerRole = dep.getServiceSpecRole();
            provider = deployedServicesMap.get(providerName);
            if (provider == null) {
                logger.error("Service " + providerName + " not deployed. Not goint to pass this context to "
                        + consumerName + " on chor " + chor.getId());
            } else {
                if (provider.getSpec().getRoles().contains(providerRole))
                    sendProviderContextToConsumer();
                else
                    logger.error("Service " + providerName + " does not have role + " + providerRole
                            + "; not going to pass it to service " + consumerName + " on chor " + chor.getId());
            }
        }
    }

    private void sendProviderContextToConsumer() {
        ServiceType consumerType = consumer.getSpec().getServiceType();
        List<String> consumerUris = consumer.getUris();
        List<String> providerUris = this.getUris(provider);
        for (String consumerEndpoint : consumerUris) {
            InvokeContextSenderTask task = new InvokeContextSenderTask(consumerType, consumerEndpoint, providerRole, providerName,
                    providerUris);
            executor.submit(task);
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
            Proxification prox = instance.getProxification();
            boolean isProxified = prox != null && prox.getBusUri(ServiceType.SOAP) != null; 
            if (isProxified) {
                String proxifiedUri = prox.getBusUri(ServiceType.SOAP);
                uris.add(proxifiedUri);
            } else {
                uris.add(instance.getNativeUri());
            }
        }
        return uris;
    }
    
    private class InvokeContextSenderTask implements Callable<Void> {

        ServiceType consumerType;
        String consumerEndpoint, providerRole, providerName;
        List<String> providerEndpoints;

        public InvokeContextSenderTask(ServiceType consumerType, String consumerEndpoint, String providerRole,
                String providerName, List<String> providerEndpoints) {
            this.consumerType = consumerType;
            this.consumerEndpoint = consumerEndpoint;
            this.providerRole = providerRole;
            this.providerName = providerName;
            this.providerEndpoints = providerEndpoints;
        }
        
        @Override
        public Void call() throws Exception {
            ContextSenderTask task = new ContextSenderTask(consumerType, consumerEndpoint, providerRole, providerName,
                    providerEndpoints);
            Invoker<Void> invoker = new InvokerBuilder<Void>(task, timeout).trials(trials)
                    .pauseBetweenTrials(pauseBetweenTrials).build();
            try {
                invoker.invoke();
            } catch (InvokerException e) {
                logger.error("Could not set " + providerName + " as " + providerRole + " to " + consumerName
                        + " on chor " + chor.getId());
            }
            return null;
        }
        
    }

    private class ContextSenderTask implements Callable<Void> {

        ServiceType consumerType;
        String consumerEndpoint, providerRole, providerName;
        List<String> providerEndpoints;

        public ContextSenderTask(ServiceType consumerType, String consumerEndpoint, String providerRole,
                String providerName, List<String> providerEndpoints) {
            this.consumerType = consumerType;
            this.consumerEndpoint = consumerEndpoint;
            this.providerRole = providerRole;
            this.providerName = providerName;
            this.providerEndpoints = providerEndpoints;
        }

        @Override
        public Void call() throws Exception {
            logger.info("Casting " + providerName + " to " + consumerEndpoint);
            ContextSender sender = ContextSenderFactory.getNewInstance(consumerType);
            sender.sendContext(consumerEndpoint, providerRole, providerName, providerEndpoints);
            return null;
        }
    }

}
