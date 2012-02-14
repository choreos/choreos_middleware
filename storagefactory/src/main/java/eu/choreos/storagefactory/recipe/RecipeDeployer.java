package eu.choreos.storagefactory.recipe;

import eu.choreos.storagefactory.NodePoolManagerHandler;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class RecipeDeployer {

	NodePoolManagerHandler npm;
	

	public RecipeDeployer(NodePoolManagerHandler nodePoolManager) {
		npm = nodePoolManager;
	}

	public String deployRecipe(Recipe recipe) {
		uploadRecipe(recipe);
		String deployedNodeHostname = npm.createNode(recipe.getName());
		return deployedNodeHostname;
	}
	
	private void uploadRecipe(Recipe recipe) {
		CommandLineInterfaceHelper.runLocalCommand("knife cookbook upload " + recipe.getName() + " -o "+recipe.getFolder());
	}
}
