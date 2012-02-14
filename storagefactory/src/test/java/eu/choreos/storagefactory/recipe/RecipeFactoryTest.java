package eu.choreos.storagefactory.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RecipeFactoryTest {

	private RecipeFactory recipeFactory;
	private static String uuid = "ThisIsATest";

	@BeforeClass
	public static void setUpBeforeClass() {
		deleteDirectory();
	}

	@Before
	public void setUp() {
		recipeFactory = new RecipeFactory();
	}

	@Test
	public void testCreateNewRecipeFromTemplate() throws IOException {

		recipeFactory.createNewRecipeFromTemplate(uuid);

		makeAssertionsCopyDirectory();
	}

	private void makeAssertionsCopyDirectory() {
		String[] extensions = new String[1];
		extensions[0] = "rb";

		URL fileLocation = ClassLoader.getSystemResource("chef/mysql_" + uuid);
		File directory = new File(fileLocation.getFile());

		assertTrue((FileUtils.listFiles(directory, extensions, false)).size() > 0);
	}

	@Test
	public void testChangeMetadataRb() throws IOException {
		recipeFactory.changeMetadataRb(uuid);

		makeAssertionsMetadataRb();

	}

	private void makeAssertionsMetadataRb() throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/mysql_" + uuid
				+ "/metadata.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $uuid were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(uuid));
		assertFalse(fileData.contains("$uuid"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("version"));
	}

	@Test
	public void testChangeAttributesServerRb() throws Exception {
		recipeFactory.changeAttributesServerRb(uuid);

		makeAssertionsServerRb();
	}

	private void makeAssertionsServerRb() throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/mysql_" + uuid
				+ "/attributes/server.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $uuid were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(uuid));
		assertFalse(fileData.contains("$uuid"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("centos"));
	}
	

	@Test
	public void testChangeRecipesServerRb() throws Exception {
		recipeFactory.changeServerRecipe(uuid);

		makeAssertionsServerRecipe();
	}

	private void makeAssertionsServerRecipe() throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/mysql_" + uuid
				+ "/recipes/server.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $uuid were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(uuid));
		assertFalse(fileData.contains("$uuid"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("centos"));
	}

	@Test
	public void testCreateRecipe() throws Exception {
		deleteDirectory();

		recipeFactory.createRecipe(uuid);
		
		makeAssertionsCopyDirectory();
		makeAssertionsMetadataRb();
		makeAssertionsServerRb();
		makeAssertionsServerRecipe();

	}

	private static void deleteDirectory() {
		URL fileLocation = ClassLoader.getSystemResource("chef/mysql_" + uuid);
		if (fileLocation != null)
			FileUtils.deleteQuietly(new File(fileLocation.getFile()));
	}

}
