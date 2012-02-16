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

import eu.choreos.storagefactory.datamodel.StorageNode;

public class RecipeFactoryTest {

	private RecipeFactory recipeFactory;
	private static StorageNode storage = new StorageNode();
	private static String uuid = "RecipeFactoryTest";
	private static String user = "UZER";
	private static String password = "CODE";
	private static String schema = "SKEMA";
	private static String dbType = "MYSQL";

	@BeforeClass
	public static void setUpBeforeClass() {
		deleteDirectory();
		storage.setPassword(password);
		storage.setSchema(schema);
		storage.setType(dbType);
		storage.setUser(user);
		storage.setUuid(uuid);
	}

	@Before
	public void setUp() {
		recipeFactory = new RecipeFactory();
	}

	@Test
	public void shouldCreateACopyOfTheFilesInTheTemplate() throws IOException {

		recipeFactory.copyTemplate(storage);

		assertTemplateFolderWasCopied();
	}

	private void assertTemplateFolderWasCopied() {
		String[] extensions = new String[1];
		extensions[0] = "rb";

		URL fileLocation = ClassLoader.getSystemResource("chef/storage" + uuid);
		File directory = new File(fileLocation.getFile());

		assertTrue((FileUtils.listFiles(directory, extensions, false)).size() > 0);
	}

	@Test
	public void shouldReplaceOcurrencesInMetadataRb() throws IOException {
		recipeFactory.changeMetadataRb(storage);

		assertAllOcurrencesInMetadataRbWereReplaced();

	}

	private void assertAllOcurrencesInMetadataRbWereReplaced()
			throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/storage" + uuid
				+ "/metadata.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(uuid));
		assertFalse(fileData.contains("$UUID"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("version"));
	}

	@Test
	public void shouldReplaceOcurrencesInAttributesServerRb() throws Exception {
		recipeFactory.changeAttributesDefaultRb(storage);

		assertAllOcurrencesWereReplacedInDefaultRb();
	}

	private void assertAllOcurrencesWereReplacedInDefaultRb()
			throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/storage" + uuid
				+ "/attributes/default.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $UUID were replaced with 123456789
		assertTrue(fileData.contains(uuid));
		assertFalse(fileData.contains("$UUID"));

		// Ensure the ocurrences of $USER were replaced with UZER
		assertTrue(fileData.contains(user));
		assertFalse(fileData.contains("$USER"));

		// Ensure the ocurrences of $PASSWORD were replaced with CODE
		assertTrue(fileData.contains(password));
		assertFalse(fileData.contains("PASSWORD"));

		// Ensure the ocurrences of $SCHEMA were replaced with SKEMA
		assertTrue(fileData.contains(schema));
		assertFalse(fileData.contains("$SCHEMA"));

		// Ensure the ocurrences of $TYPE were replaced with MYSQL
		assertTrue(fileData.contains(dbType));
		assertFalse(fileData.contains("$TYPE"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("IMPORTANT DEVELOPMENT NOTICE:"));
	}

	@Test
	public void shouldReplaceOcurrencesInRecipesServerRb() throws Exception {
		recipeFactory.changeServerRecipe(storage);

		assertAllOcurrencesWereReplacedInDefaultRecipe();
	}

	private void assertAllOcurrencesWereReplacedInDefaultRecipe()
			throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/storage" + uuid
				+ "/recipes/default.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(uuid));
		assertFalse(fileData.contains("$UUID"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains(" IMPORTANT DEVELOPMENT NOTICE:"));
	}

	@Test
	public void shouldCreateFullRecipe() throws Exception {
		deleteDirectory();

		Recipe recipe = recipeFactory.createRecipe(storage);

		assertTemplateFolderWasCopied();
		assertAllOcurrencesInMetadataRbWereReplaced();
		assertAllOcurrencesWereReplacedInDefaultRb();
		assertAllOcurrencesWereReplacedInDefaultRecipe();
		assertFilesAreAvailableInFolder(recipe);

	}

	private void assertFilesAreAvailableInFolder(Recipe recipe)
			throws IOException {
		String fileData = FileUtils.readFileToString(new File(recipe
				.getFolder() + "/attributes/default.rb"));

		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(uuid));
		assertFalse(fileData.contains("$UUID"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains(" IMPORTANT DEVELOPMENT NOTICE:"));

	}

	private static void deleteDirectory() {
		URL fileLocation = ClassLoader.getSystemResource("chef/storage" + uuid);
		if (fileLocation != null)
			FileUtils.deleteQuietly(new File(fileLocation.getFile()));
	}

}
