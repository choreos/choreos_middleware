package eu.choreos.servicedeployer.recipe;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.servicedeployer.NodePoolManagerHandler;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class RecipeDeployerTest {

	private static Recipe recipe;
	private RecipeDeployer deployer = new RecipeDeployer(
			new NodePoolManagerHandler());

	@BeforeClass
	public static void setUpBeforeClass() {
		recipe = new Recipe();
		recipe.setName("myServletWAR");
		recipe.setFolder("./src/test/resources/chef/");
		System.out
				.println("deleting previous instances of the recipe. Errors are expected");
		(new CommandLineInterfaceHelper())
				.runLocalCommand("knife cookbook delete myServletWAR -y");
		(new CommandLineInterfaceHelper())
				.runLocalCommand("knife node run_list remove choreos recipe[myServletWAR]");
	}

	@Test
	public void testUploadRecipe() {

		deployer.deployRecipe(recipe, (new Service()));

		String commandReturn = "";

		commandReturn = (new CommandLineInterfaceHelper())
				.runLocalCommand("knife cookbook list");

		assertTrue("Did not find the uploaded recipe.",
				commandReturn.contains("myServlet"));
	}

	@Test
	public void testDeployRecipe() {

		deployer.deployRecipe(recipe, (new Service()));

		String commandReturn = "";

		commandReturn = (new CommandLineInterfaceHelper())
				.runLocalCommand("knife search node run_list:*myServlet*");

		assertTrue("Did not find the uploaded recipe.",
				commandReturn.contains("Node Name"));
	}
}
