package eu.choreos.servicedeployer;

import java.net.MalformedURLException;
import java.net.URL;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.recipe.Recipe;
import eu.choreos.servicedeployer.recipe.RecipeDeployer;
import eu.choreos.servicedeployer.recipe.RecipeFactory;
import eu.choreos.servicedeployer.registry.DeployedServicesRegistry;

public class ServiceDeployer {

	private DeployedServicesRegistry registry;

	public String deploy(Service service) throws MalformedURLException {
		// Create recipe from template
		RecipeFactory factory = new RecipeFactory();
		Recipe serviceDeployRecipe = factory.createRecipe(service);

		// Upload template to Chef
		RecipeDeployer deployer = new RecipeDeployer(
				new NodePoolManagerHandler());
		deployer.uploadRecipe(serviceDeployRecipe);

		// and Request NodePoolManager to deploy the recipe in a node
		String deployedHost = deployer.deployRecipe(serviceDeployRecipe,
				service);

		String serviceURL;
		if (deployedHost.contains("http://"))
			serviceURL = service.getUri() + ":" + service.getPort() + "/"
					+ service.getId() + "/";
		else
			serviceURL = "http://" + service.getUri() + ":" + service.getPort()
					+ "/" + service.getId() + "/";

		return serviceURL;
	}

	public Service getService(String serviceID) {
		return registry.getNode(serviceID);
	}

	// What is this??
	public Service updateService(Service service) {
		return null;
	}

	public void deleteService(String serviceID) {
		registry.deleteService(serviceID);
	}

}
