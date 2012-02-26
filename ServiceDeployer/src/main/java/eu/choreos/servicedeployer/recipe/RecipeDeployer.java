package eu.choreos.servicedeployer.recipe;

import eu.choreos.servicedeployer.Configuration;
import eu.choreos.servicedeployer.NodePoolManagerHandler;
import eu.choreos.servicedeployer.SimpleNodePoolManagerHandler;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.utils.CommandLineInterfaceHelper;

public class RecipeDeployer {

	public static String  KNIFE_CONFIG_FILE_OPTION = 
			Configuration.get("CHEF_CONFIG_FILE")!=null?" -c "+Configuration.get("CHEF_CONFIG_FILE"):"";
	
	private NodePoolManagerHandler npm;

	public RecipeDeployer(NodePoolManagerHandler nodePoolManagerHandler) {
		npm = nodePoolManagerHandler;
	}

	public String deployRecipe(Recipe recipe, Service service) {
		String deployedNodeHostname = npm.createNode(recipe.getName(), service)
				.getUri();
		return deployedNodeHostname;
	}

	public void uploadRecipe(Recipe recipe) {
		(new CommandLineInterfaceHelper())
				.runLocalCommand(createUploadCommand(recipe));
	}

	private String createUploadCommand(Recipe recipe) {
		System.out.println("knife cookbook upload " + recipe.getName() + " -o "
				+ recipe.getFolder() +KNIFE_CONFIG_FILE_OPTION);
		return "knife cookbook upload " + recipe.getName() + " -o "
				+ recipe.getFolder()+KNIFE_CONFIG_FILE_OPTION;
	}
}
