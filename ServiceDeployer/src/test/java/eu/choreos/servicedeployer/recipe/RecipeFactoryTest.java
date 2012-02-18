package eu.choreos.servicedeployer.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.servicedeployer.datamodel.ResourceImpact;
import eu.choreos.servicedeployer.datamodel.Service;

public class RecipeFactoryTest {

	private RecipeFactory recipeFactory;
	private static Service service = new Service();
	private static String id = "myServletWAR";
	private static String codeLocationURI = "http://content.hccfl.edu/pollock/AJava/WAR/myServletWAR.war";
	private static String warFile = "myServletWAR.war";
	private static eu.choreos.servicedeployer.datamodel.ResourceImpact impact = new ResourceImpact();

	@BeforeClass
	public static void setUpBeforeClass() {
		deleteDirectory();
		impact.setCpu("low");
		impact.setIo("low");
		impact.setMemory("low");
		impact.setRegion("BR");

		service.setId(id);
		service.setCodeLocationURI(codeLocationURI);
		service.setServiceType("WAR");
		service.setWarFile(warFile);
		service.setResourceImpact(impact);
	}

	@Before
	public void setUp() {
		recipeFactory = new RecipeFactory();
	}

	@Test
	public void shouldCreateACopyOfTheFilesInTheTemplate() throws IOException {

		recipeFactory.copyTemplate(service);

		assertTemplateFolderWasCopied();
	}

	private void assertTemplateFolderWasCopied() {
		String[] extensions = new String[1];
		extensions[0] = "rb";

		URL fileLocation = ClassLoader.getSystemResource("chef/service" + id);
		File directory = new File(fileLocation.getFile());

		assertTrue((FileUtils.listFiles(directory, extensions, false)).size() > 0);
	}

	@Test
	public void shouldReplaceOcurrencesInMetadataRb() throws IOException {
		recipeFactory.changeMetadataRb(service);

		assertAllOcurrencesInMetadataRbWereReplaced();

	}

	private void assertAllOcurrencesInMetadataRbWereReplaced()
			throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/service" + id
				+ "/metadata.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		assertTrue(fileData.contains("depends \"tomcat\""));
		assertFalse(fileData.contains("$NAME"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("version"));
	}

	@Test
	public void shouldReplaceOcurrencesInAttributesServerRb() throws Exception {
		recipeFactory.changeAttributesDefaultRb(service);

		assertAllOcurrencesWereReplacedInDefaultRb();
	}

	private void assertAllOcurrencesWereReplacedInDefaultRb()
			throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/service" + id
				+ "/attributes/default.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $NAME were replaced with the service ID
		assertTrue(fileData.contains(id));
		assertFalse(fileData.contains("$NAME"));

		// Ensure the ocurrences of $WARFILE were replaced with war file name
		assertTrue(fileData.contains(warFile));
		assertFalse(fileData.contains("$WARFILE"));

		// Ensure the ocurrences of $URL were replaced with the code location
		assertTrue(fileData.contains(codeLocationURI));
		assertFalse(fileData.contains("$URL"));

		// Ensure the ocurrences of $SCHEMA were replaced with SKEMA
		assertFalse(fileData.contains("$"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains("IMPORTANT DEVELOPMENT NOTICE:"));
	}

	@Test
	public void shouldReplaceOcurrencesInRecipesServerRb() throws Exception {
		recipeFactory.changeServerRecipe(service);

		assertAllOcurrencesWereReplacedInDefaultRecipe();
	}

	private void assertAllOcurrencesWereReplacedInDefaultRecipe()
			throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/service" + id
				+ "/recipes/default.rb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(id));
		assertFalse(fileData.contains("$NAME"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains(" IMPORTANT DEVELOPMENT NOTICE:"));
	}

	@Test
	public void shouldReplaceOcurrencesInTemplatesShellScript()
			throws Exception {
		recipeFactory.changeScriptTemplate(service);

		assertAllOcurrencesWereReplacedInTemplatesShellScript();
	}

	private void assertAllOcurrencesWereReplacedInTemplatesShellScript()
			throws IOException {
		URL fileLocation = ClassLoader.getSystemResource("chef/service" + id
				+ "/templates/default/deploy-service.sh.erb");
		String fileData = FileUtils.readFileToString(new File(fileLocation
				.getFile()));

		System.out.println(fileLocation);
		System.out.println(fileData);
		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(id));
		assertFalse(fileData.contains("$NAME"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains(" IMPORTANT DEVELOPMENT NOTICE:"));
	}

	@Test
	public void shouldCreateFullRecipe() throws Exception {
		deleteDirectory();

		Recipe recipe = recipeFactory.createRecipe(service);

		assertTemplateFolderWasCopied();
		assertAllOcurrencesInMetadataRbWereReplaced();
		assertAllOcurrencesWereReplacedInDefaultRb();
		assertAllOcurrencesWereReplacedInDefaultRecipe();
		assertFilesAreAvailableInFolder(recipe);

	}

	private void assertFilesAreAvailableInFolder(Recipe recipe)
			throws IOException {
		System.out.println(recipe.getFolder() + "/attributes/default.rb");
		String fileData = FileUtils.readFileToString(new File(recipe
				.getFolder() + "/attributes/default.rb"));

		// Ensure the ocurrences of $UUID were replaced with THIS_IS_A_TEST
		assertTrue(fileData.contains(id));
		assertFalse(fileData.contains("$NAME"));

		// Ensures the remainder of the file is left untouched
		assertTrue(fileData.contains(" IMPORTANT DEVELOPMENT NOTICE:"));

	}

	private static void deleteDirectory() {
		URL fileLocation = ClassLoader.getSystemResource("chef/service" + id);
		if (fileLocation != null)
			FileUtils.deleteQuietly(new File(fileLocation.getFile()));
	}

}
