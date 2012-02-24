package eu.choreos.storagefactory.recipe;

import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;
import eu.choreos.storagefactory.utils.NodePoolManagerHandler;
import eu.choreos.storagefactory.utils.SimpleNodePoolManagerHandler;

public class RecipeDeployer {

	private NodePoolManagerHandler npm;

	public RecipeDeployer(NodePoolManagerHandler nodePoolManager) {
		npm = nodePoolManager;
	}
	
	public RecipeDeployer() {
		npm = new SimpleNodePoolManagerHandler();
	}

	public String deployRecipe(Recipe recipe) {
		uploadRecipe(recipe);
		String deployedNodeHostname = npm.createNode(recipe.getName());
		return deployedNodeHostname;
	}

	private void uploadRecipe(Recipe recipe) {
		(new CommandLineInterfaceHelper())
				.runLocalCommand(createUploadCommand(recipe));
	}

	private String createUploadCommand(Recipe recipe) {
		return "knife cookbook upload " + recipe.getName() + " -o "
				+ recipe.getFolder();
		// knife cookbook upload storage12345789 -o ./src/test/resources/chef/
	}
}
