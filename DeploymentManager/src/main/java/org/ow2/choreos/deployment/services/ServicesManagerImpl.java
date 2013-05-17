package org.ow2.choreos.deployment.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.cm.RecipeApplier;
import org.ow2.choreos.deployment.services.diff.UpdateAction;
import org.ow2.choreos.deployment.services.recipe.RecipeBuilder;
import org.ow2.choreos.deployment.services.recipe.RecipeBuilderFactory;
import org.ow2.choreos.deployment.services.registry.DeployedServicesRegistry;
import org.ow2.choreos.nodes.ConfigNotAppliedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.ServiceNotDeletedException;
import org.ow2.choreos.services.ServiceNotDeployedException;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.Recipe;
import org.ow2.choreos.services.datamodel.RecipeBundle;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceSpec;

public class ServicesManagerImpl implements ServicesManager {

	private Logger logger = Logger.getLogger(ServicesManagerImpl.class);

	private DeployedServicesRegistry registry = DeployedServicesRegistry
			.getInstance();
	private NodePoolManager npm;
	private Knife knife;

	public ServicesManagerImpl(NodePoolManager npm) {

		final String CHEF_REPO = Configuration.get("CHEF_REPO");
		final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
		Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
		fakeConstructor(npm, knife);
	}

	// constructor created to mock knife in tests
	public ServicesManagerImpl(NodePoolManager npm, Knife knife) {
		fakeConstructor(npm, knife);
	}

	private void fakeConstructor(NodePoolManager npm, Knife knife) {
		this.npm = npm;
		this.knife = knife;
	}

	@Override
	public DeployableService createService(DeployableServiceSpec serviceSpec)
			throws ServiceNotDeployedException {

		DeployableService service = null;
		try {
			service = new DeployableService(serviceSpec);
		} catch (IllegalArgumentException e) {
			String message = "Invalid service spec";
			logger.error(message, e);
			throw new ServiceNotDeployedException(serviceSpec.getUUID(),
					message);
		}

		if (serviceSpec.getPackageType() != PackageType.LEGACY) {
			service = createDeployableService(service);
		}

		registry.addService(serviceSpec.getUUID(), service);
		return service;

	}

	private DeployableService createDeployableService(DeployableService service)
			throws ServiceNotDeployedException {

		createAndUploadRecipes(service);
		logger.debug("recipes uploaded");
		applyRecipe(service, service.getSpec().getNumberOfInstances());
		logger.debug("creation of service " + service.getSpec().getUUID() + " completed");

		return service;
	}

	private void createAndUploadRecipes(DeployableService service)
			throws ServiceNotDeployedException {
		
		for (int i = 0; i < 5;) {
			try {
				this.uploadRecipes(this.createRecipes(service));
			} catch (KnifeException e) {
				i++;
				if (i >= 4) {
					logger.error("Could not upload recipe: " + e.getMessage());
					throw new ServiceNotDeployedException(service.getSpec()
							.getUUID());
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
			}
			break;
		}
	}

	private RecipeBundle createRecipes(DeployableService service) {
		PackageType type = service.getSpec().getPackageType();
		RecipeBuilder builder = RecipeBuilderFactory
				.getRecipeBuilderInstance(type);
		RecipeBundle bundle = builder.createServiceRecipeBundle(service
				.getSpec());
		service.setRecipeBundle(bundle);
		return bundle;
	}

	private void uploadRecipes(RecipeBundle serviceRecipeBundle)
			throws KnifeException {

		File folder = new File(serviceRecipeBundle.getCookbookFolder());
		String dir = folder.getAbsolutePath();
		uploadServiceRecipe(serviceRecipeBundle, dir);
		uploadDeactivateRecipe(serviceRecipeBundle, dir);
	}

	private void uploadDeactivateRecipe(RecipeBundle serviceRecipeBundle,
			String dir) throws KnifeException {
		String result;
		logger.debug("Uploading deactivate recipe "
				+ serviceRecipeBundle.getServiceRecipe().getCookbookName());
		result = this.knife.cookbook().upload(
				serviceRecipeBundle.getDeactivateRecipe().getCookbookName(),
				dir);
		logger.debug(result);
	}

	private void uploadServiceRecipe(RecipeBundle serviceRecipeBundle,
			String dir) throws KnifeException {
		logger.debug("Uploading service recipe "
				+ serviceRecipeBundle.getServiceRecipe().getCookbookName());
		String result = this.knife.cookbook().upload(
				serviceRecipeBundle.getServiceRecipe().getCookbookName(), dir);
		logger.debug(result);
	}

	private void applyRecipe(DeployableService service,
			int numberOfNewInstances) {

		RecipeBundle serviceRecipe = service.getRecipeBundle();

		String configName = serviceRecipe.getServiceRecipe().getCookbookName()
				+ "::" + serviceRecipe.getServiceRecipe().getName();

		Config config = new Config(configName, service.getSpec()
				.getResourceImpact(), numberOfNewInstances);

		// TODO: throw exception
		List<Node> nodes = new ArrayList<Node>();
		try {
			nodes = npm.applyConfig(config);
		} catch (ConfigNotAppliedException e) {
			logger.error("Service " + service.getSpec().getUUID()
					+ " not created: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Service " + service.getSpec().getUUID()
					+ " not created: " + e.getMessage());
		}

		for (Node node : nodes) {
			if (!((node.getHostname() == null || node.getHostname().isEmpty()) && (node
					.getIp() == null || node.getIp().isEmpty()))) {
				logger.debug("nodeLocation= " + node.getHostname()
						+ "; node IP=" + node.getIp());
				service.addInstance(new ServiceInstance(node));
			} else {
				logger.debug("I cannot process a request to create a node with no IP or hostname!");
			}
		}

	}

	@Override
	public DeployableService getService(String serviceId)
			throws ServiceNotFoundException {

		DeployableService s = registry.getService(serviceId);
		if (s == null)
			throw new ServiceNotFoundException(serviceId,
					"Error while getting service from service map.");
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
		RecipeBundle recipeBundle = service.getRecipeBundle();
		Recipe deactivateRecipe = recipeBundle.getDeactivateRecipe();

		// deactivate service instances and
		for (ServiceInstance instance : service.getInstances()) {
			executeServiceInstanceUndeployment(deactivateRecipe, instance);
		}
		
		// remove cookbooks from chef-server
		try {
			this.knife.cookbook().delete(deactivateRecipe.getCookbookName());
			this.knife.cookbook().delete(recipeBundle.getServiceRecipe().getCookbookName());
		} catch (KnifeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void executeServiceInstanceUndeployment(Recipe deactivateRecipe,
			ServiceInstance instance) {
		RecipeApplier recipeApplyer = new RecipeApplier();
		try {
			recipeApplyer.applyRecipe(instance.getNode(),
					deactivateRecipe.getCookbookName(), "");
		} catch (ConfigNotAppliedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DeployableService updateService(DeployableServiceSpec serviceSpec)
			throws UnhandledModificationException {

		logger.info("Requested to update service " + serviceSpec.getUUID()
				+ " with spec " + serviceSpec);
		DeployableService current;
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

		DeployableServiceSpec currentSpec = current.getSpec();
		logger.info("Current service spec " + currentSpec);
		List<UpdateAction> actions = getActions(currentSpec, serviceSpec);
		applyUpdate(current, serviceSpec, actions);
		logger.info("Updated service = " + current);
		return current;
	}

	private void applyUpdate(DeployableService currentService,
			DeployableServiceSpec requestedSpec, List<UpdateAction> actions)
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

	private List<UpdateAction> getActions(DeployableServiceSpec currentSpec,
			DeployableServiceSpec serviceSpec) {
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
			DeployableService currentService, ServiceSpec requestedSpec) {
		int decreaseAmount = currentService.getSpec().getNumberOfInstances()
				- requestedSpec.getNumberOfInstances();
		removeServiceInstances(currentService, decreaseAmount);
	}

	private void requestToIncreaseNumberOfInstances(
			DeployableService currentService, ServiceSpec requestedSpec) {
		int increaseAmount = requestedSpec.getNumberOfInstances()
				- currentService.getSpec().getNumberOfInstances();

		logger.info("requestToIncreaseNumberOfInstances: Increase amount = "
				+ increaseAmount);
		addServiceInstances(currentService, increaseAmount);
	}

	private void requestToMigrateServiceInstances(
			DeployableService currentService, ServiceSpec requestedSpec)
			throws UnhandledModificationException {
		currentService.setSpec(requestedSpec);
		currentService.getInstances().clear();
		migrateServiceInstances(currentService);
	}

	private void migrateServiceInstances(DeployableService currentService)
			throws UnhandledModificationException {
		try {
			createDeployableService(currentService);
		} catch (ServiceNotDeployedException e) {
			throw new UnhandledModificationException();
		}
	}

	private void removeServiceInstances(DeployableService currentService,
			int amount) {
		if (amount < currentService.getInstances().size()) {
			for (int i = 0; i < amount; i++) {
				executeServiceInstanceUndeployment(currentService
						.getRecipeBundle().getDeactivateRecipe(),
						currentService.getInstances().get(0));
				currentService.getInstances().remove(0);
			}
		} else if (amount < currentService.getInstances().size()) {
			try {
				this.deleteService(currentService.getSpec().getUUID());
			} catch (ServiceNotDeletedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addServiceInstances(DeployableService current, int amount) {
		logger.info("Requesting to execute creation of " + amount
				+ " replicas for" + current);
		applyRecipe(current, amount);
	}
}
