/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.services;

import java.util.Set;

import org.apache.log4j.Logger;
import org.ow2.choreos.ee.services.preparer.PrepareDeploymentFailedException;
import org.ow2.choreos.ee.services.preparer.ServiceDeploymentPreparer;
import org.ow2.choreos.ee.services.preparer.ServiceDeploymentPreparerFactory;
import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparer;
import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparerFactory;
import org.ow2.choreos.ee.services.registry.DeployedServicesRegistry;
import org.ow2.choreos.ee.services.update.ServiceUpdater;
import org.ow2.choreos.ee.services.update.UpdateActionFailedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServiceNotDeletedException;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

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
	    logger.error("Could not create service " + serviceSpec.getName());
	    throw new ServiceNotCreatedException(serviceSpec.getName());
	}

	Set<CloudNode> selectedNodes = prepareDeployment(serviceSpec, service.getUUID());
	service.setSelectedNodes(selectedNodes);

	registry.addService(service.getUUID(), service);
	logger.info("Created service " + service);
	return service;

    }

    private Set<CloudNode> prepareDeployment(DeployableServiceSpec spec, String serviceUUID)
	    throws ServiceNotCreatedException {
	ServiceDeploymentPreparer deploymentPreparer = ServiceDeploymentPreparerFactory.getNewInstance(spec,
		serviceUUID);
	try {
	    return deploymentPreparer.prepareDeployment();
	} catch (PrepareDeploymentFailedException e1) {
	    logger.error("Could not prepare the deployment of the service " + serviceUUID + " (" + spec.getName() + ")");
	    throw new ServiceNotCreatedException(spec.getName());
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

	try {
	    this.executeUndeployment(service);
	} catch (UpdateActionFailedException e) {
	    throw new ServiceNotDeletedException(uuid);
	}

	registry.deleteService(uuid);
	if (registry.getService(uuid) != null)
	    throw new ServiceNotDeletedException(uuid);
    }

    private void executeUndeployment(DeployableService service) throws UpdateActionFailedException {
	ServiceUndeploymentPreparer undeploymentPreparer = ServiceUndeploymentPreparerFactory.getNewInstance(service,
		service.getServiceInstances().size());

	try {
	    undeploymentPreparer.prepareUndeployment();
	} catch (Exception e) {
	    throw new UpdateActionFailedException();
	}
    }

    @Override
    public DeployableService updateService(String serviceUUID, DeployableServiceSpec serviceSpec)
	    throws UnhandledModificationException, ServiceNotFoundException {
	DeployableService service = this.getService(serviceUUID);
	ServiceUpdater updater = new ServiceUpdater(service, serviceSpec);
	updater.updateService();
	return service;
    }
}
