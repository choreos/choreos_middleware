package eu.choreos.servicedeployer;

import java.net.MalformedURLException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.cxf.jaxrs.client.WebClient;

import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;
import eu.choreos.servicedeployer.chef.CookbookManager;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.npm.NodePoolManager;
import eu.choreos.servicedeployer.recipe.Recipe;
import eu.choreos.servicedeployer.recipe.RecipeBuilder;
import eu.choreos.servicedeployer.recipe.RecipeBuilderFactory;
import eu.choreos.servicedeployer.registry.DeployedServicesRegistry;

public class ServiceDeployer {

	private DeployedServicesRegistry registry = new DeployedServicesRegistry();
	private NodePoolManager npm;
	
	public ServiceDeployer(NodePoolManager npm) {
		this.npm = npm;
	}
	
	/**
	 * 
	 * @param service
	 * @return
	 * @throws MalformedURLException
	 */
	public String deploy(Service service) throws MalformedURLException {
		
		Recipe serviceRecipe = createRecipe(service);

		CookbookManager.uploadCookbook(serviceRecipe);

		String hostname = deployService(serviceRecipe);
		
		service.setUri(getServiceURL(service, hostname));

		registry.addService(service.getId(), service);
		
		return service.getUri();
	}

	private Recipe createRecipe(Service service) {
		ServiceType serviceType = ServiceType.valueOf(service.getServiceType().toUpperCase());
		RecipeBuilder builder = RecipeBuilderFactory.getRecipeBuilderInstance(serviceType);
		Recipe serviceRecipe = builder.createRecipe(service);
		return serviceRecipe;
	}

	private String deployService(Recipe serviceRecipe) {
		String nodeLocation = npm.applyConfig(serviceRecipe.getName());
		WebClient client = WebClient.create(nodeLocation);
		NodeRestRepresentation node = client.get(NodeRestRepresentation.class);
		String hostname = node.getIp();
		return hostname;
	}

	private String getServiceURL(Service service, String hostname) {
		service.setPort(8080); // TODO: Define where the port shouldbe set
		return "http://" + hostname + ":" + service.getPort()
				+ "/service" + service.getId() + "Deploy/";
	}
	

	public Service getService(String serviceID) {
		return registry.getService(serviceID);
	}

	public Service updateService(Service service) {
		throw new NotImplementedException();
	}

	public void deleteService(String serviceID) {
		registry.deleteService(serviceID);
	}

}
