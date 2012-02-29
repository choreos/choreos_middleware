package eu.choreos.storagefactory.recipe;

import eu.choreos.storagefactory.Configuration;
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
		
		String deployedNodeHostname=null;
		try{
			deployedNodeHostname= npm.createNode(recipe.getName());
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
		return deployedNodeHostname;
	}

	private void uploadRecipe(Recipe recipe) {
		System.out.println("Uploading recipe: "+recipe.getName()+" ...");
		(new CommandLineInterfaceHelper())
				.runLocalCommand(createUploadCommand(recipe));
	}

	private String createUploadCommand(Recipe recipe) {
		String runCommand="knife cookbook upload " + recipe.getName() + " -o "+ recipe.getFolder();
		runCommand+= Configuration.get("CHEF_CONFIG_FILE")!=null?" -c "+Configuration.get("CHEF_CONFIG_FILE"):"";
		return runCommand;
		// knife cookbook upload storage12345789 -o ./src/test/resources/chef/
	}
}
