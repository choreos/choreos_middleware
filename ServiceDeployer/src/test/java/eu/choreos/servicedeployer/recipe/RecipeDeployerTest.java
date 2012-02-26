package eu.choreos.servicedeployer.recipe;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.servicedeployer.Configuration;
import eu.choreos.servicedeployer.SimpleNodePoolManagerHandler;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.utils.CommandLineInterfaceHelper;

public class RecipeDeployerTest {

	private static Recipe recipe;
	public static String  KNIFE_CONFIG_FILE_OPTION = 
			Configuration.get("CHEF_CONFIG_FILE")!=null?" -c "+Configuration.get("CHEF_CONFIG_FILE"):"";
	private RecipeDeployer deployer = new RecipeDeployer(
			new SimpleNodePoolManagerHandler());

	@BeforeClass
	public static void setUpBeforeClass() {
		recipe = new Recipe();
		recipe.setName("servlet");
		recipe.setFolder("./src/test/resources/chef/");
		System.out
				.println("deleting previous instances of the recipe. Errors are expected");
		String runCommand="knife cookbook remove myServletWAR -y"+KNIFE_CONFIG_FILE_OPTION;
		
		(new CommandLineInterfaceHelper()).runLocalCommand(runCommand);
	
		runCommand= "knife node run_list remove choreos recipe[myServletWAR]"+KNIFE_CONFIG_FILE_OPTION;
		System.out.println("Executing: "+runCommand);
		(new CommandLineInterfaceHelper()).runLocalCommand(runCommand);
	}

	@Test
	public void testUploadRecipe() {

		deployer.deployRecipe(recipe, (new Service()));

		String commandReturn = "";
		String runCommand="knife cookbook list"+KNIFE_CONFIG_FILE_OPTION;
		
		commandReturn = (new CommandLineInterfaceHelper()).runLocalCommand(runCommand);

		assertTrue("Did not find the uploaded recipe.",
				commandReturn.contains("myServlet"));
	}

	@Test
	public void testDeployRecipe() {

		deployer.deployRecipe(recipe, (new Service()));

		String commandReturn = "";
		String runCommand="knife search node run_list:*myServlet*"+KNIFE_CONFIG_FILE_OPTION;
		
		System.out.println("Executing: "+runCommand);
		commandReturn = (new CommandLineInterfaceHelper()).runLocalCommand(runCommand);

		assertTrue("Did not find the uploaded recipe.",
				commandReturn.contains("Node Name"));
	}
}
