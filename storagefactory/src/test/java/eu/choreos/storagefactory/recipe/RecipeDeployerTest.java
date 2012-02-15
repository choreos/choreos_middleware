package eu.choreos.storagefactory.recipe;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.storagefactory.NodePoolManagerHandler;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class RecipeDeployerTest {

	private static Recipe recipe;
	private RecipeDeployer deployer = new RecipeDeployer(
			new NodePoolManagerHandler());

	@BeforeClass
	public static void setUpBeforeClass() {
		recipe = new Recipe();
		recipe.setName("storage123456789");
		recipe.setFolder("./src/test/resources/chef/");
		System.out.println("deleting previous instances of the recipe. Errors are expected");
		CommandLineInterfaceHelper.runLocalCommand("knife cookbook remove storage123456789 -y");
	}

	@Test
	public void testDeployRecipe() {
		
		deployer.deployRecipe(recipe);

		String commandReturn = "";
		
		commandReturn = CommandLineInterfaceHelper.runLocalCommand("knife cookbook list");
		
		assertTrue("Did not find the uploaded recipe.", commandReturn.contains("storage123456789"));
	}
}
