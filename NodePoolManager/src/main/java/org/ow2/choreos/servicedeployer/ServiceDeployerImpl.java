package org.ow2.choreos.servicedeployer;


import org.apache.log4j.Logger;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.npm.Configuration;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.recipe.Recipe;
import org.ow2.choreos.servicedeployer.recipe.RecipeBuilder;
import org.ow2.choreos.servicedeployer.recipe.RecipeBuilderFactory;
import org.ow2.choreos.servicedeployer.registry.DeployedServicesRegistry;

public class ServiceDeployerImpl implements ServiceDeployer {

	private Logger logger = Logger.getLogger(ServiceDeployerImpl.class);
	
    private static String CHEF_REPO = Configuration.get("CHEF_REPO");
    private static String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
    
	private DeployedServicesRegistry registry = new DeployedServicesRegistry();
	private NodePoolManager npm;
	
	public ServiceDeployerImpl(NodePoolManager npm) {
		this.npm = npm;
	}

	@Override
	public Service deploy(ServiceSpec serviceSpec) {
		
		Service service = new Service(serviceSpec);
		Recipe serviceRecipe = this.createRecipe(service);

		try {
			this.uploadRecipe(serviceRecipe);
		} catch (KnifeException e) {
			logger.error("Could not upload recipe", e);
			return null;
		} 

		String hostname = deployService(serviceRecipe); 
		if (hostname == null || hostname.isEmpty())
			return null;
		
		service.setHost(hostname);
		registry.addService(service.getId(), service);
		
		return service;
	}

	private Recipe createRecipe(Service service) {
		
		RecipeBuilder builder = RecipeBuilderFactory.getRecipeBuilderInstance(service.getType());
		Recipe serviceRecipe = builder.createRecipe(service);
		return serviceRecipe;
	}
	
	private void uploadRecipe(Recipe serviceRecipe) throws KnifeException {
		
		Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO); 
		knife.cookbook().upload(serviceRecipe.getCookbookName(),
				serviceRecipe.getCookbookFolder());
	}

	private String deployService(Recipe serviceRecipe) {
		
		String configName = serviceRecipe.getCookbookName() + "::" + serviceRecipe.getName();
		Config config = new Config(configName);
		Node node = npm.applyConfig(config);
		if (node == null)
			return null;
		String hostname = node.getHostname();
		logger.debug("nodeLocation= " + hostname);
		return hostname;
	}

	@Override
	public Service getService(String serviceId) {
		
		return registry.getService(serviceId);
	}

	@Override
	public boolean deleteService(String serviceId) {
		
		registry.deleteService(serviceId);
		if (registry.getService(serviceId) == null)
			return true;
		else
			return false;
	}

}
