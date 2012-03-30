package eu.choreos.servicedeployer.chef;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.choreos.servicedeployer.recipe.Recipe;

public class CookbookManagerTest {

	private static String COOKBOOK = "service-deploy-recipe-template";
	private static String COOKBOOK_PATH = "src/main/resources/chef/service-deploy-recipe-template";
	
	@Test
	public void shouldUploadAndDeleteCookbbok() {
		
		Recipe recipe = new Recipe();
		recipe.setName("war");
		recipe.setCookbookName(COOKBOOK);
		recipe.setCookbookFolder(COOKBOOK_PATH);
		
		CookbookManager.deleteCookbook(COOKBOOK);
		assertTrue(!CookbookManager.listCookbooks().contains(COOKBOOK));
		
		CookbookManager.uploadCookbook(recipe);
		assertTrue(CookbookManager.listCookbooks().contains(COOKBOOK));
		
		CookbookManager.deleteCookbook(COOKBOOK);
		assertTrue(!CookbookManager.listCookbooks().contains(COOKBOOK));
	}

}
