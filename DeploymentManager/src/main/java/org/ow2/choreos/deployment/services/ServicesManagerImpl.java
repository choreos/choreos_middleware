package org.ow2.choreos.deployment.services;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.diff.UnhandledModificationException;
import org.ow2.choreos.deployment.services.diff.UpdateAction;
import org.ow2.choreos.deployment.services.recipe.Recipe;
import org.ow2.choreos.deployment.services.recipe.RecipeBuilder;
import org.ow2.choreos.deployment.services.recipe.RecipeBuilderFactory;
import org.ow2.choreos.deployment.services.registry.DeployedServicesRegistry;

public class ServicesManagerImpl implements ServicesManager {

	private Logger logger = Logger.getLogger(ServicesManagerImpl.class);

	private DeployedServicesRegistry registry = DeployedServicesRegistry.getInstance();
	private NodePoolManager npm;
	protected Knife knife;

	public ServicesManagerImpl(NodePoolManager npm) {

		final String CHEF_REPO = Configuration.get("CHEF_REPO");
		final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
		this.npm = npm;
		this.knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO); 
	}

	@Override
	public DeployedService createService(DeployedServiceSpec serviceSpec) throws ServiceNotDeployedException {

		DeployedService service = null;
		try {
			service = new DeployedService(serviceSpec);
		} catch (IllegalArgumentException e) {
			String message = "Invalid service spec"; 
			logger.error(message, e);
			throw new ServiceNotDeployedException(serviceSpec.getUUID(), message);
		}

		if (serviceSpec.getPackageType() != PackageType.LEGACY) {
			service = deployService(service);
		} 

		registry.addService(serviceSpec.getUUID(), service);
		return service;

	}

	private DeployedService deployService(DeployedService service) throws ServiceNotDeployedException {

		prepareDeployment(service);
		logger.debug("prepare deployment complete");
		executeDeployment(service, service.getSpec().getNumberOfInstances());
		logger.debug("execute deployment complete");

		return service;
	}

	private void prepareDeployment(DeployedService service) throws ServiceNotDeployedException {

		Recipe serviceRecipe = this.createRecipe(service);

		try {
			this.uploadRecipe(serviceRecipe);
		} catch (KnifeException e) {
			logger.error("Could not upload recipe: " + e.getMessage());
			throw new ServiceNotDeployedException(service.getSpec().getUUID());
		}
	}

	private Recipe createRecipe(DeployedService service) {

		PackageType type = service.getSpec().getPackageType();
		RecipeBuilder builder = RecipeBuilderFactory.getRecipeBuilderInstance(type);
		Recipe serviceRecipe = builder.createRecipe(service.getSpec());
		service.setRecipe(serviceRecipe);
		return serviceRecipe;
	}

	private void uploadRecipe(Recipe serviceRecipe) throws KnifeException {

		File folder = new File(serviceRecipe.getCookbookFolder());
		String parent = folder.getParent();
		logger.debug("Uploading recipe " + serviceRecipe.getName());
		String result = this.knife.cookbook().upload(serviceRecipe.getCookbookName(), parent);
		logger.debug(result);
	}

	private void executeDeployment(DeployedService service, int numberOfNewInstances) {

		Recipe serviceRecipe = service.getRecipe();
		String configName = serviceRecipe.getCookbookName() + "::" + serviceRecipe.getName();
		Config config = new Config(configName, service.getSpec().getResourceImpact(), numberOfNewInstances);

		List<Node> nodes = new ArrayList<Node>();
		try {
			nodes = npm.applyConfig(config);
		} catch (ConfigNotAppliedException e) {
			logger.error("Service " + service.getSpec().getUUID() + " not deployed: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Service " + service.getSpec().getUUID() + " not deployed: " + e.getMessage());
		}

		for(Node node:nodes) {
			if (!((node.getHostname() == null || node.getHostname().isEmpty()) 
					&& (node.getIp() == null || node.getIp().isEmpty()))) {
				logger.debug("nodeLocation= " + node.getHostname() + "; node IP=" + node.getIp());
				service.addInstance(new ServiceInstance(node));
			} else {
				logger.debug("request to create a node with no IP or hostname!");
			}
		}

	}

	@Override
	public DeployedService getService(String serviceId) throws ServiceNotFoundException {

		DeployedService s = registry.getService(serviceId);
		if(s == null)
			throw new ServiceNotFoundException(serviceId, "Error while getting service from service map.");
		return s;
	}


	@Override
	public void deleteService(String serviceName) throws ServiceNotDeletedException {

		registry.deleteService(serviceName);
		if (registry.getService(serviceName) != null)
			throw new ServiceNotDeletedException(serviceName);
	}

	@Override
	public DeployedService updateService(DeployedServiceSpec serviceSpec) throws UnhandledModificationException {

		DeployedService current;
		try {
			current = getService(serviceSpec.getUUID());
		} catch (ServiceNotFoundException e) {
			throw new UnhandledModificationException();
		}

		DeployedServiceSpec currentSpec = current.getSpec();

		List<UpdateAction> actions = getActions(currentSpec, serviceSpec);

		applyUpdate(current, serviceSpec, actions);

		return current;
	}

	private void applyUpdate(DeployedService currentService, DeployedServiceSpec requestedSpec, List<UpdateAction> actions) throws UnhandledModificationException {
		for ( UpdateAction a : actions ) {
			switch (a) {
			case INCREASE_NUMBER_OF_REPLICAS:
				requestToIncreaseNumberOfInstances(currentService,
						requestedSpec);
				break;

			case DECREASE_NUMBER_OF_REPLICAS:
				requestToDecreaseNumberOfInstances(currentService,
						requestedSpec);
				break;

			case MIGRATE:
				requestToMigrateServiceInstances(currentService, requestedSpec);
				break;

			default:
				throw new UnhandledModificationException();
			}
		}
	}

	private List<UpdateAction> getActions(DeployedServiceSpec currentSpec,
			DeployedServiceSpec serviceSpec) {
		boolean foundKnownModification = false;

		List<UpdateAction> actions = new ArrayList<UpdateAction>();

		if(currentSpec.getNumberOfInstances() < serviceSpec.getNumberOfInstances()) {
			actions.add(UpdateAction.INCREASE_NUMBER_OF_REPLICAS);
			foundKnownModification = true;
		} else if(currentSpec.getNumberOfInstances() > serviceSpec.getNumberOfInstances()) {
			actions.add(UpdateAction.DECREASE_NUMBER_OF_REPLICAS);
			foundKnownModification = true;
		}

		if( !(currentSpec.getResourceImpact() == null || currentSpec.getResourceImpact().getMemory() == null) ) {
			if(! (currentSpec.getResourceImpact().getMemory().ordinal() == serviceSpec.getResourceImpact().getMemory().ordinal() ) ) {
				actions.add(UpdateAction.MIGRATE);
				foundKnownModification = true;
			}
		}

		if(!foundKnownModification) {
			actions.add(UpdateAction.UNKNOWN_MODIFICATION);
		}

		return actions;
	}
	
	
	
	

	private void requestToDecreaseNumberOfInstances(DeployedService currentService,
			ServiceSpec requestedSpec) {
		int decreaseAmount = currentService.getSpec().getNumberOfInstances() - requestedSpec.getNumberOfInstances();
		removeServiceInstances(currentService, decreaseAmount);
	}
	
	private void requestToIncreaseNumberOfInstances(DeployedService currentService,
			ServiceSpec requestedSpec) {
		int increaseAmount = requestedSpec.getNumberOfInstances() - currentService.getSpec().getNumberOfInstances();
		addServiceInstances(currentService, increaseAmount);
	}
	
	private void requestToMigrateServiceInstances(DeployedService currentService, ServiceSpec requestedSpec) throws UnhandledModificationException {
		currentService.setSpec(requestedSpec);
		currentService.getInstances().clear();
		migrateServiceInstances(currentService);
	}

	private void migrateServiceInstances(DeployedService currentService)
			throws UnhandledModificationException {
		try {
			deployService(currentService);
		} catch (ServiceNotDeployedException e) {
			throw new UnhandledModificationException();
		}
	}
	
	private void removeServiceInstances(DeployedService currentService, int amount) {
		if(amount <= currentService.getInstances().size()) {
			for(int i = 0; i < amount; i++) {
				currentService.getInstances().remove(0);
			}
		}
	}
	
	private void addServiceInstances(DeployedService current, int amount) {
		executeDeployment(current, amount);
	}
}
