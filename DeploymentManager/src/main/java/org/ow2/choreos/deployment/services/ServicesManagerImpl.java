/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services;

import java.util.Set;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.services.preparer.PrepareDeploymentFailedException;
import org.ow2.choreos.deployment.services.preparer.ServiceDeploymentPreparer;
import org.ow2.choreos.deployment.services.preparer.ServiceDeploymentPreparerFactory;
import org.ow2.choreos.deployment.services.registry.DeployedServicesRegistry;
import org.ow2.choreos.deployment.services.update.ServiceUpdater;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServiceNotDeletedException;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class ServicesManagerImpl implements ServicesManager {

    private DeployedServicesRegistry registry = DeployedServicesRegistry.getInstance();

    private Logger logger = Logger.getLogger(ServicesManagerImpl.class);

    @Override
    public DeployableService createService(DeployableServiceSpec serviceSpec) throws ServiceNotCreatedException {

        DeployableService service = null;
        try {
            service = new DeployableService(serviceSpec);
            service.generateUUID();
        } catch (IllegalArgumentException e1) {
            ServiceNotCreatedException e = new ServiceNotCreatedException(serviceSpec.getName());
            logger.error(e.getMessage());
            throw e;
        }

        Set<CloudNode> selectedNodes = prepareDeployment(serviceSpec, service.getUUID());
        service.setSelectedNodes(selectedNodes);

        registry.addService(service.getUUID(), service);
        logger.info("Created service " + service);
        return service;

    }

    private Set<CloudNode> prepareDeployment(DeployableServiceSpec spec, String serviceUUID)
            throws ServiceNotCreatedException {
        ServiceDeploymentPreparer deploymentPreparer = ServiceDeploymentPreparerFactory.getNewInstance(spec, serviceUUID);
        try {
            return deploymentPreparer.prepareDeployment();
        } catch (PrepareDeploymentFailedException e1) {
            ServiceNotCreatedException e = new ServiceNotCreatedException(spec.getName());
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public DeployableService getService(String uuid) throws ServiceNotFoundException {
        DeployableService s = registry.getService(uuid);
        if (s == null)
            throw new ServiceNotFoundException(uuid);
        return s;
    }

    @Override
    public void deleteService(String uuid) throws ServiceNotDeletedException {

        DeployableService service = null;
        try {
            service = this.getService(uuid);
        } catch (ServiceNotFoundException e) {
            logger.error("Could not get service " + uuid);
        }

        this.executeUndeployment(service);

        registry.deleteService(uuid);
        if (registry.getService(uuid) != null)
            throw new ServiceNotDeletedException(uuid);
    }

    private void executeUndeployment(DeployableService service) {
        for (ServiceInstance instance : service.getServiceInstances())
            executeServiceInstanceUndeployment(instance);
    }

    private void executeServiceInstanceUndeployment(ServiceInstance instance) {
        // ssh to execute (cd $HOME/chef-solo; sed -i
        // '/300b87ed-cca9-4858-8779-6987da782b18/d' ./node.json)
    }

    @Override
    public DeployableService updateService(String serviceUUID, DeployableServiceSpec serviceSpec) throws UnhandledModificationException, ServiceNotFoundException {
        DeployableService service = this.getService(serviceUUID);
        ServiceUpdater updater = new ServiceUpdater(service, serviceSpec);
        updater.updateService();
        return service;
    }
}
