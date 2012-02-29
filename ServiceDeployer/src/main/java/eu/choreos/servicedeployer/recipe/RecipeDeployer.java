package eu.choreos.servicedeployer.recipe;

import java.io.File;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.servicedeployer.NodePoolManagerHandler;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.utils.CommandLineInterfaceHelper;

public class RecipeDeployer {

	// este getSystemResource estava usando a pasta do NodePoolManager!!!
	// public static final URL destURL = ClassLoader.getSystemResource("chef");

	public static final File DEST_DIR = new File("src/main/resources/chef");
	
	private NodePoolManagerHandler npm;

	public RecipeDeployer(NodePoolManagerHandler nodePoolManager) {
		npm = nodePoolManager;
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
				+ recipe.getFolder() + " -c " + Configuration.get("CHEF_CONFIG_FILE"));
		return "knife cookbook upload " + recipe.getName() + " -o "
				+ recipe.getFolder() + " -c " + Configuration.get("CHEF_CONFIG_FILE");
	}
}
