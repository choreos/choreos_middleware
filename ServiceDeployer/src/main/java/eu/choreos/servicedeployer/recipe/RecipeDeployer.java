package eu.choreos.servicedeployer.recipe;

import eu.choreos.servicedeployer.NodePoolManagerHandler;
import eu.choreos.servicedeployer.utils.CommandLineInterfaceHelper;

public class RecipeDeployer {

	private NodePoolManagerHandler npm;

	public RecipeDeployer(NodePoolManagerHandler nodePoolManager) {
		npm = nodePoolManager;
	}

	public String deployRecipe(Recipe recipe) {
		String deployedNodeHostname = npm.createNode(recipe.getName());
		return deployedNodeHostname;
	}

	public void uploadRecipe(Recipe recipe) {
		(new CommandLineInterfaceHelper())
				.runLocalCommand(createUploadCommand(recipe));
	}

	private String createUploadCommand(Recipe recipe) {
		return "knife cookbook upload " + recipe.getName() + " -o "
				+ recipe.getFolder();
	}
}
