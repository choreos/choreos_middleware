package org.ow2.choreos.servicedeployer;


import java.io.File;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.npm.ConfigNotAppliedException;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
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

		Node node = deployService(serviceRecipe);
		String hostname = node.getHostname();
		String ip = node.getIp();
		if ((hostname == null || hostname.isEmpty())
				&& (ip == null || ip.isEmpty()))
			return null;
		service.setHost(hostname);
		service.setIp(ip);
		service.setNodeId(node.getId());
		registry.addService(service.getName(), service);
		
		return service;
	}

	private Recipe createRecipe(Service service) {
		
		ServiceType type = service.getSpec().getType();
		RecipeBuilder builder = RecipeBuilderFactory.getRecipeBuilderInstance(type);
		Recipe serviceRecipe = builder.createRecipe(service);
		return serviceRecipe;
	}
	
	private void uploadRecipe(Recipe serviceRecipe) throws KnifeException {
		
		Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO); 
		File folder = new File(serviceRecipe.getCookbookFolder());
		String parent = folder.getParent();
		logger.debug("Uploading recipe " + serviceRecipe.getName());
		String result = knife.cookbook().upload(serviceRecipe.getCookbookName(),
				parent);
		logger.debug(result);
	}

	private Node deployService(Recipe serviceRecipe) {
		
		String configName = serviceRecipe.getCookbookName() + "::" + serviceRecipe.getName();
		Config config = new Config(configName);
		Node node;
		try {
			node = npm.applyConfig(config);
		} catch (ConfigNotAppliedException e) {
			return null;
		}
		logger.debug("nodeLocation= " + node.getHostname() + "; node IP="
				+ node.getIp());
		return node;
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
