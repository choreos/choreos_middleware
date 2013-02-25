package org.ow2.choreos.deployment.services.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.recipe.Recipe;
import org.ow2.choreos.deployment.services.recipe.RecipeBuilderImpl;

public class RecipeBuilderTest {

	private static String RECIPES_FOLDER = "chef/recipes";
	
	private RecipeBuilderImpl recipeBuilder;
	private static ServiceSpec service;
	private static String id = "myServletWAR";
	private static String codeLocationURI = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war";
	private static ResourceImpact impact = new ResourceImpact();

	@BeforeClass
	public static void setUpBeforeClass() {
		
		deleteDirectory();
		
		impact.setCpu("low");
		impact.setIo("low");
		impact.setMemory("low");
		impact.setRegion("BR");

		service = new ServiceSpec();
		service.setDeployableUri(codeLocationURI);
		service.setArtifactType(ArtifactType.TOMCAT);
		service.setResourceImpact(impact);
		service.setName(id);
	}

	@Before
	public void setUp() {
		recipeBuilder = new RecipeBuilderImpl("war");
	}
	
	private static File getResource(String resource) {
		
		String resourcesPath = "src/main/resources/";
		return new File(resourcesPath + resource);
	}

	@Test
	public void shouldCreateACopyOfTheFilesInTheTemplate() throws IOException {

		recipeBuilder.copyTemplate(service);
		assertTemplateFolderWasCopied();
	}

	private void assertTemplateFolderWasCopied() {
		
		String[] extensions = {"rb"};
		File directory = getResource(RECIPES_FOLDER + "/service" + id);
		assertTrue((FileUtils.listFiles(directory, extensions, false)).size() > 0);
	}

	@Test
	public void shouldReplaceOcurrencesInMetadataRb() throws IOException {

		recipeBuilder.changeMetadataRb(service);
		assertAllOcurrencesInMetadataRbWereReplaced();
	}

	private void assertAllOcurrencesInMetadataRbWereReplaced()
			throws IOException {
		
		File fileLocation = getResource(RECIPES_FOLDER + "/service" + id
				+ "/metadata.rb");
		String fileData = FileUtils.readFileToString(fileLocation);

		assertTrue(fileData.contains("depends \"tomcat\""));
		assertFalse(fileData.contains("$NAME"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("version"));
	}

	@Test
	public void shouldReplaceOcurrencesInAttributesServerRb() throws Exception {
		
		recipeBuilder.changeAttributesDefaultRb(service);
		assertAllOcurrencesWereReplacedInDefaultRb();
	}

	private void assertAllOcurrencesWereReplacedInDefaultRb()
			throws IOException {
		File fileLocation = getResource(RECIPES_FOLDER + "/service" + id
				+ "/attributes/default.rb");
		String fileData = FileUtils.readFileToString(fileLocation);

		// Ensure the ocurrences of $NAME were replaced with the service ID
		assertTrue(fileData.contains(id));
		assertFalse(fileData.contains("$NAME"));

		// Ensure the ocurrences of $WARFILE were replaced with war file name
		String warFile = service.getFileName();
		assertTrue(fileData.contains(warFile));
		assertFalse(fileData.contains("$WARFILE"));

		// Ensure the ocurrences of $URL were replaced with the code location
		assertTrue(fileData.contains(codeLocationURI));
		assertFalse(fileData.contains("$URL"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("IMPORTANT DEVELOPMENT NOTICE:"));
	}

	private void assertAllOcurrencesWereReplacedInDefaultRecipe()
			throws IOException {
		
		File fileLocation = getResource(RECIPES_FOLDER + "/service" + id
				+ "/recipes/war.rb");
		String fileData = FileUtils.readFileToString(fileLocation);

		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(id));
		assertFalse(fileData.contains("$NAME"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains(" IMPORTANT DEVELOPMENT NOTICE:"));
	}

	@Test
	public void shouldCreateFullRecipe() throws Exception {
		deleteDirectory();

		Recipe recipe = recipeBuilder.createRecipe(service);

		assertTemplateFolderWasCopied();
		assertAllOcurrencesInMetadataRbWereReplaced();
		assertAllOcurrencesWereReplacedInDefaultRb();
		assertAllOcurrencesWereReplacedInDefaultRecipe();
		assertFilesAreAvailableInFolder(recipe);

	}

	private void assertFilesAreAvailableInFolder(Recipe recipe)
			throws IOException {
		
		System.out.println(recipe.getCookbookFolder() + "/attributes/default.rb");
		String fileData = FileUtils.readFileToString(new File(recipe
				.getCookbookFolder() + "/attributes/default.rb"));

		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(id));
		assertFalse(fileData.contains("$NAME"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains(" IMPORTANT DEVELOPMENT NOTICE:"));

	}

	private static void deleteDirectory() {
		File fileLocation = getResource(RECIPES_FOLDER + "/service" + id);
		if (fileLocation != null)
			FileUtils.deleteQuietly(fileLocation);
	}


}
