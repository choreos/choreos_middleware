/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.services.diff.UpdateAction;
import org.ow2.choreos.deployment.services.registry.DeployedServicesRegistry;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServiceNotDeletedException;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.DeploymentRequest;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceSpec;

public class ServicesManagerImpl implements ServicesManager {

    private Logger logger = Logger.getLogger(ServicesManagerImpl.class);

    private DeployedServicesRegistry registry = DeployedServicesRegistry.getInstance();

    @Override
    public DeployableService createService(DeployableServiceSpec serviceSpec) throws ServiceNotCreatedException {

	DeployableService service = null;
	try {
	    service = new DeployableService(serviceSpec);
	} catch (IllegalArgumentException e) {
	    String message = "Invalid service spec";
	    logger.error(message, e);
	    throw new ServiceNotCreatedException(serviceSpec.getName());
	}

	runGenerateAndApplyScript(service, service.getSpec().getNumberOfInstances());

	registry.addService(serviceSpec.getUuid(), service);
	return service;

    }

    private void runGenerateAndApplyScript(DeployableService service, int numberOfInstances)
	    throws ServiceNotCreatedException {

	DeploymentRequest deploymentRequest = new DeploymentRequest(service);
	deploymentRequest
		.setDeploymentManagerURL(DeploymentManagerConfiguration.get("EXTERNAL_DEPLOYMENT_MANAGER_URL"));

	try {
	    DeploymentPreparer deploymentPreparer = new DeploymentPreparer();
	    deploymentPreparer.prepareDeployment(deploymentRequest);
	} catch (PrepareDeploymentFailedException e) {
	    logger.error("Service " + service.getSpec().getUuid() + " not created: " + e.getMessage());
	} catch (Exception e) {
	    logger.error("Service " + service.getSpec().getUuid() + " not created: " + e.getMessage());
	}
    }

    @Override
    public DeployableService getService(String serviceId) throws ServiceNotFoundException {

	DeployableService s = registry.getService(serviceId);
	if (s == null)
	    throw new ServiceNotFoundException(serviceId, "Error while getting service from service map.");
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
    public DeployableService updateService(DeployableServiceSpec serviceSpec) throws UnhandledModificationException {

	logger.info("Requested to update service " + serviceSpec.getUuid() + " with spec " + serviceSpec);
	DeployableService current;
	try {
	    logger.info("Trying to get service with id " + serviceSpec.getUuid() + " from \n" + registry.getServices());
	    current = getService(serviceSpec.getUuid());
	    logger.info("getService got " + current);
	} catch (ServiceNotFoundException e) {
	    logger.info("ServiceNotFoundException happened when trying to get service with id " + serviceSpec.getUuid());
	    logger.info("Exception message " + e.getMessage());
	    throw new UnhandledModificationException();
	}

	DeployableServiceSpec currentSpec = current.getSpec();
	logger.info("Current service spec " + currentSpec);
	List<UpdateAction> actions = getActions(currentSpec, serviceSpec);
	applyUpdate(current, serviceSpec, actions);
	logger.info("Updated service = " + current);
	return current;
    }

    private void applyUpdate(DeployableService currentService, DeployableServiceSpec requestedSpec,
	    List<UpdateAction> actions) throws UnhandledModificationException {
	logger.info("Going to apply scheduled updates...");
	for (UpdateAction a : actions) {
	    logger.info("Selected update " + a);
	    switch (a) {
	    case INCREASE_NUMBER_OF_REPLICAS:
		logger.info("Requesting to increase amount of replicas");
		requestToIncreaseNumberOfInstances(currentService, requestedSpec);
		break;

	    case DECREASE_NUMBER_OF_REPLICAS:
		logger.info("Requesting to decrease amount of replicas");
		requestToDecreaseNumberOfInstances(currentService, requestedSpec);
		break;

	    case MIGRATE:
		logger.info("Requesting to migrate replicas");
		requestToMigrateServiceInstances(currentService, requestedSpec);
		break;

	    default:
		logger.info("Cannot apply modification " + a + "; Raising UnhandledModificationException");
		throw new UnhandledModificationException();
	    }
	}
	logger.info("Setting the new service spec for service " + currentService);

	String uuid = currentService.getSpec().getUuid();
	currentService.setSpec(requestedSpec);
	registry.addService(currentService.getSpec().getUuid(), currentService);
	registry.deleteService(uuid);
    }

    private List<UpdateAction> getActions(DeployableServiceSpec currentSpec, DeployableServiceSpec serviceSpec) {
	boolean foundKnownModification = false;

	List<UpdateAction> actions = new ArrayList<UpdateAction>();

	logger.info("Calculating changes on service spec \nOld = " + currentSpec + "\nNew = " + serviceSpec);
	if (currentSpec.getNumberOfInstances() < serviceSpec.getNumberOfInstances()) {
	    logger.info("getAction has detected that must increase number of replicas");
	    actions.add(UpdateAction.INCREASE_NUMBER_OF_REPLICAS);
	    foundKnownModification = true;
	} else if (currentSpec.getNumberOfInstances() > serviceSpec.getNumberOfInstances()) {
	    logger.info("getAction has detected that must decrease number of replicas");
	    actions.add(UpdateAction.DECREASE_NUMBER_OF_REPLICAS);
	    foundKnownModification = true;
	}

	if (!(currentSpec.getResourceImpact() == null || currentSpec.getResourceImpact().getMemory() == null)) {
	    if (!(currentSpec.getResourceImpact().getMemory().ordinal() == serviceSpec.getResourceImpact().getMemory()
		    .ordinal())) {
		logger.info("getAction has detected that must to migrate service");
		actions.add(UpdateAction.MIGRATE);
		foundKnownModification = true;
	    }
	}

	if (!foundKnownModification) {
	    logger.info("getAction has not detected any changes to do");
	    actions.add(UpdateAction.UNKNOWN_MODIFICATION);
	}

	logger.info("Detected changes: " + actions);
	return actions;
    }

    private void requestToDecreaseNumberOfInstances(DeployableService currentService,
	    DeployableServiceSpec requestedSpec) {
	int decreaseAmount = currentService.getSpec().getNumberOfInstances() - requestedSpec.getNumberOfInstances();
	removeServiceInstances(currentService, decreaseAmount);
    }

    private void requestToIncreaseNumberOfInstances(DeployableService currentService,
	    DeployableServiceSpec requestedSpec) {
	int increaseAmount = requestedSpec.getNumberOfInstances() - currentService.getSpec().getNumberOfInstances();

	logger.info("requestToIncreaseNumberOfInstances: Increase amount = " + increaseAmount);
	addServiceInstances(currentService, increaseAmount);
    }

    private void requestToMigrateServiceInstances(DeployableService currentService, ServiceSpec requestedSpec)
	    throws UnhandledModificationException {
	currentService.setSpec(requestedSpec);
	currentService.getServiceInstances().clear();
	migrateServiceInstances(currentService);
    }

    private void migrateServiceInstances(DeployableService currentService) throws UnhandledModificationException {
	try {
	    runGenerateAndApplyScript(currentService, currentService.getSpec().getNumberOfInstances());
	} catch (ServiceNotCreatedException e) {
	    throw new UnhandledModificationException();
	}
    }

    private void removeServiceInstances(DeployableService currentService, int amount) {
	if (amount < currentService.getServiceInstances().size()) {
	    for (int i = 0; i < amount; i++) {
		executeServiceInstanceUndeployment(currentService.getServiceInstances().get(0));
		currentService.getServiceInstances().remove(0);
	    }
	} else if (amount < currentService.getServiceInstances().size()) {
	    try {
		this.deleteService(currentService.getSpec().getUuid());
	    } catch (ServiceNotDeletedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    private void addServiceInstances(DeployableService current, int amount) {
	logger.info("Requesting to execute creation of " + amount + " replicas for" + current);
	try {
	    runGenerateAndApplyScript(current, amount);
	} catch (ServiceNotCreatedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
