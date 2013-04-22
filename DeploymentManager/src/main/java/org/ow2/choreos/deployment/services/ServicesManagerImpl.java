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

	private DeployedServicesRegistry registry = DeployedServicesRegistry
			.getInstance();
	private NodePoolManager npm;
	protected Knife knife;

	public ServicesManagerImpl(NodePoolManager npm) {

		final String CHEF_REPO = Configuration.get("CHEF_REPO");
		final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
		this.npm = npm;
		this.knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
	}

	@Override
	public DeployedService createService(DeployedServiceSpec serviceSpec)
			throws ServiceNotDeployedException {

		DeployedService service = null;
		try {
			service = new DeployedService(serviceSpec);
		} catch (IllegalArgumentException e) {
			String message = "Invalid service spec";
			logger.error(message, e);
			throw new ServiceNotDeployedException(serviceSpec.getUUID(),
					message);
		}

		if (serviceSpec.getPackageType() != PackageType.LEGACY) {
			service = deployService(service);
		}

		registry.addService(serviceSpec.getUUID(), service);
		return service;

	}

	private DeployedService deployService(DeployedService service)
			throws ServiceNotDeployedException {

		prepareDeployment(service);
		logger.debug("prepare deployment complete");
		executeDeployment(service, service.getSpec().getNumberOfInstances());
		logger.debug("execute deployment complete");

		return service;
	}

	private void prepareDeployment(DeployedService service)
			throws ServiceNotDeployedException {

		Recipe serviceRecipe = this.createRecipe(service);

		for (int i = 0; i < 5; i++) {
			try {
				this.uploadRecipe(serviceRecipe);
			} catch (KnifeException e) {
				if (i >= 4) {
					logger.error("Could not upload recipe: " + e.getMessage());
					throw new ServiceNotDeployedException(service.getSpec()
							.getUUID());
				} else
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
			}
		}
	}

	private Recipe createRecipe(DeployedService service) {

		PackageType type = service.getSpec().getPackageType();
		RecipeBuilder builder = RecipeBuilderFactory
				.getRecipeBuilderInstance(type);
		Recipe serviceRecipe = builder.createRecipe(service.getSpec());
		service.setRecipe(serviceRecipe);
		return serviceRecipe;
	}

	private void uploadRecipe(Recipe serviceRecipe) throws KnifeException {

		File folder = new File(serviceRecipe.getCookbookFolder());
		String parent = folder.getParent();
		logger.debug("Uploading recipe " + serviceRecipe.getName());
		String result = this.knife.cookbook().upload(
				serviceRecipe.getCookbookName(), parent);
		logger.debug(result);
	}

	private void executeDeployment(DeployedService service,
			int numberOfNewInstances) {

		Recipe serviceRecipe = service.getRecipe();
		String configName = serviceRecipe.getCookbookName() + "::"
				+ serviceRecipe.getName();
		Config config = new Config(configName, service.getSpec()
				.getResourceImpact(), numberOfNewInstances);

		// TODO: throw exception
		List<Node> nodes = new ArrayList<Node>();
		try {
			nodes = npm.applyConfig(config);
		} catch (ConfigNotAppliedException e) {
			logger.error("Service " + service.getSpec().getUUID()
					+ " not deployed: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Service " + service.getSpec().getUUID()
					+ " not deployed: " + e.getMessage());
		}

		for (Node node : nodes) {
			if (!((node.getHostname() == null || node.getHostname().isEmpty()) && (node
					.getIp() == null || node.getIp().isEmpty()))) {
				logger.debug("nodeLocation= " + node.getHostname()
						+ "; node IP=" + node.getIp());
				service.addInstance(new ServiceInstance(node));
			} else {
				logger.debug("request to create a node with no IP or hostname!");
			}
		}

	}

	@Override
	public DeployedService getService(String serviceId)
			throws ServiceNotFoundException {

		DeployedService s = registry.getService(serviceId);
		if (s == null)
			throw new ServiceNotFoundException(serviceId,
					"Error while getting service from service map.");
		return s;
	}

	@Override
	public void deleteService(String serviceName)
			throws ServiceNotDeletedException {

		registry.deleteService(serviceName);
		if (registry.getService(serviceName) != null)
			throw new ServiceNotDeletedException(serviceName);
	}

	@Override
	public DeployedService updateService(DeployedServiceSpec serviceSpec)
			throws UnhandledModificationException {

		logger.info("Requested to update service " + serviceSpec.getUUID()
				+ " with spec " + serviceSpec);
		DeployedService current;
		try {
			logger.info("Trying to get service with id "
					+ serviceSpec.getUUID() + " from \n"
					+ registry.getServices());
			current = getService(serviceSpec.getUUID());
			logger.info("getService got " + current);
		} catch (ServiceNotFoundException e) {
			logger.info("ServiceNotFoundException happened when trying to get service with id "
					+ serviceSpec.getUUID());
			logger.info("Exception message " + e.getMessage());
			throw new UnhandledModificationException();
		}

		DeployedServiceSpec currentSpec = current.getSpec();
		logger.info("Current service spec " + currentSpec);
		List<UpdateAction> actions = getActions(currentSpec, serviceSpec);
		applyUpdate(current, serviceSpec, actions);
		logger.info("Updated service = " + current);
		return current;
	}

	private void applyUpdate(DeployedService currentService,
			DeployedServiceSpec requestedSpec, List<UpdateAction> actions)
			throws UnhandledModificationException {
		logger.info("Going to apply scheduled updates...");
		for (UpdateAction a : actions) {
			logger.info("Selected update " + a);
			switch (a) {
			case INCREASE_NUMBER_OF_REPLICAS:
				logger.info("Requesting to increase amount of replicas");
				requestToIncreaseNumberOfInstances(currentService,
						requestedSpec);
				break;

			case DECREASE_NUMBER_OF_REPLICAS:
				logger.info("Requesting to decrease amount of replicas");
				requestToDecreaseNumberOfInstances(currentService,
						requestedSpec);
				break;

			case MIGRATE:
				logger.info("Requesting to migrate replicas");
				requestToMigrateServiceInstances(currentService, requestedSpec);
				break;

			default:
				logger.info("Cannot apply modification " + a
						+ "; Raising UnhandledModificationException");
				throw new UnhandledModificationException();
			}
		}
		logger.info("Setting the new service spec for service "
				+ currentService);

		String uuid = currentService.getSpec().getUUID();
		currentService.setSpec(requestedSpec);
		registry.addService(currentService.getSpec().getUUID(), currentService);
		registry.deleteService(uuid);
	}

	private List<UpdateAction> getActions(DeployedServiceSpec currentSpec,
			DeployedServiceSpec serviceSpec) {
		boolean foundKnownModification = false;

		List<UpdateAction> actions = new ArrayList<UpdateAction>();

		logger.info("Calculating changes on service spec \nOld = "
				+ currentSpec + "\nNew = " + serviceSpec);
		if (currentSpec.getNumberOfInstances() < serviceSpec
				.getNumberOfInstances()) {
			logger.info("getAction has detected that must increase number of replicas");
			actions.add(UpdateAction.INCREASE_NUMBER_OF_REPLICAS);
			foundKnownModification = true;
		} else if (currentSpec.getNumberOfInstances() > serviceSpec
				.getNumberOfInstances()) {
			logger.info("getAction has detected that must decrease number of replicas");
			actions.add(UpdateAction.DECREASE_NUMBER_OF_REPLICAS);
			foundKnownModification = true;
		}

		if (!(currentSpec.getResourceImpact() == null || currentSpec
				.getResourceImpact().getMemory() == null)) {
			if (!(currentSpec.getResourceImpact().getMemory().ordinal() == serviceSpec
					.getResourceImpact().getMemory().ordinal())) {
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

	private void requestToDecreaseNumberOfInstances(
			DeployedService currentService, ServiceSpec requestedSpec) {
		int decreaseAmount = currentService.getSpec().getNumberOfInstances()
				- requestedSpec.getNumberOfInstances();
		removeServiceInstances(currentService, decreaseAmount);
	}

	private void requestToIncreaseNumberOfInstances(
			DeployedService currentService, ServiceSpec requestedSpec) {
		int increaseAmount = requestedSpec.getNumberOfInstances()
				- currentService.getSpec().getNumberOfInstances();

		logger.info("requestToIncreaseNumberOfInstances: Increase amount = "
				+ increaseAmount);
		addServiceInstances(currentService, increaseAmount);
	}

	private void requestToMigrateServiceInstances(
			DeployedService currentService, ServiceSpec requestedSpec)
			throws UnhandledModificationException {
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

	private void removeServiceInstances(DeployedService currentService,
			int amount) {
		if (amount <= currentService.getInstances().size()) {
			for (int i = 0; i < amount; i++) {
				currentService.getInstances().remove(0);
			}
		}
	}

	private void addServiceInstances(DeployedService current, int amount) {
		logger.info("Requesting to execute deploy of " + amount
				+ " replicas for" + current);
		executeDeployment(current, amount);
		// Until here working very nice
		//logger.info("UPGRADED " + current);
	}
}
