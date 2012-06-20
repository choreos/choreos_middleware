package eu.choreos.servicedeployer;

import java.net.MalformedURLException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;

import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;
import eu.choreos.servicedeployer.chef.CookbookManager;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceType;
import eu.choreos.servicedeployer.npm.NodePoolManager;
import eu.choreos.servicedeployer.recipe.Recipe;
import eu.choreos.servicedeployer.recipe.RecipeBuilder;
import eu.choreos.servicedeployer.recipe.RecipeBuilderFactory;
import eu.choreos.servicedeployer.registry.DeployedServicesRegistry;

public class ServiceDeployer {

	private Logger logger = Logger.getLogger(ServiceDeployer.class);
	
	private DeployedServicesRegistry registry = new DeployedServicesRegistry();
	private NodePoolManager npm;
	
	public ServiceDeployer(NodePoolManager npm) {
		this.npm = npm;
	}
	
	/**
	 * 
	 * @param service
	 * @return service URI
	 * @throws MalformedURLException
	 */
	public String deploy(Service service) throws MalformedURLException {
		
		Recipe serviceRecipe = createRecipe(service);

		CookbookManager.uploadCookbook(serviceRecipe);

		String hostname = deployService(serviceRecipe);
		
		service.setHost(hostname);

		registry.addService(service.getId(), service);
		
		return service.getUri();
	}

	private Recipe createRecipe(Service service) {
		ServiceType serviceType = service.getServiceType();
		RecipeBuilder builder = RecipeBuilderFactory.getRecipeBuilderInstance(serviceType);
		Recipe serviceRecipe = builder.createRecipe(service);
		return serviceRecipe;
	}

	private String deployService(Recipe serviceRecipe) {
		
		String config = serviceRecipe.getCookbookName() + "::" + serviceRecipe.getName();
		String nodeLocation = npm.applyConfig(config);
		logger.debug("nodeLocation= " + nodeLocation);
		WebClient client = WebClient.create(nodeLocation);
		
		String hostname = "";
		synchronized (ServiceDeployer.class) {
			NodeRestRepresentation node = client.get(NodeRestRepresentation.class);
			hostname = node.getIp();
		}
		return hostname;
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
