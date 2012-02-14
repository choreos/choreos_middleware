package eu.choreos.storagefactory.recipe;

import eu.choreos.storagefactory.NodePoolManagerHandler;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class RecipeDeployer {

	NodePoolManagerHandler npm;
	

	public RecipeDeployer(NodePoolManagerHandler nodePoolManager) {
		npm = nodePoolManager;
	}

	public String deployRecipe(String recipeName) {
		uploadRecipe(recipeName);
		String deployedNodeHostname = npm.createNode(recipeName);
		return deployedNodeHostname;
	}
	
	public static void uploadRecipe(String recipeName) {
		CommandLineInterfaceHelper.runLocalCommand("knife cookbook upload " + recipeName);
	}
}
