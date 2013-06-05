/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.RecipeBundle;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.ResourceImpactDefs.MemoryTypes;

public class RecipeBuilderTest {

    private static String RECIPES_FOLDER = "chef/recipes";

    private RecipeBuilderImpl recipeBuilder;
    private static DeployableServiceSpec serviceSpec;
    private static String id;
    private static String codeLocationURI = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war";
    private static ResourceImpact impact = new ResourceImpact();

    private static final File DEST_DIR = new File("src/main/resources/chef/recipes");

    @BeforeClass
    public static void setUpBeforeClass() {

	deleteDirectory();

	impact.setCpu("low");
	impact.setIo("low");
	impact.setMemory(MemoryTypes.SMALL);
	impact.setRegion("BR");

	serviceSpec = new DeployableServiceSpec();
	id = serviceSpec.getUUID();
	serviceSpec.setPackageUri(codeLocationURI);
	serviceSpec.setPackageType(PackageType.TOMCAT);
	serviceSpec.setResourceImpact(impact);
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

	File srcFolder = new File("src/main/resources/chef/" + "service-deploy-recipe-template");

	String destPath = DEST_DIR.getAbsolutePath() + "/service" + serviceSpec.getUUID();

	File destFolder = new File(destPath);

	recipeBuilder.copyRecipeTemplate(srcFolder, destFolder);
	assertTemplateFolderWasCopied();
    }

    private void assertTemplateFolderWasCopied() {

	String[] extensions = { "rb" };
	File directory = getResource(RECIPES_FOLDER + "/service" + id);
	assertTrue((FileUtils.listFiles(directory, extensions, false)).size() > 0);
    }

    @Test
    public void shouldReplaceOcurrencesInMetadataRb() throws IOException {

	recipeBuilder.changeMetadataRb(serviceSpec, "/service" + serviceSpec.getUUID() + "/metadata.rb");
	assertAllOcurrencesInMetadataRbWereReplaced();
    }

    private void assertAllOcurrencesInMetadataRbWereReplaced() throws IOException {

	File fileLocation = getResource(RECIPES_FOLDER + "/service" + id + "/metadata.rb");
	String fileData = FileUtils.readFileToString(fileLocation);

	assertTrue(fileData.contains("depends \"tomcat\""));
	assertFalse(fileData.contains("$NAME"));

	// Ensures the remainder of the file is left untouched
	assertTrue(fileData.contains("version"));
    }

    @Test
    public void shouldReplaceOcurrencesInAttributesServerRb() throws Exception {

	recipeBuilder.changeAttributesDefaultRb(serviceSpec, "/service" + serviceSpec.getUUID()
		+ "/attributes/default.rb");
	assertAllOcurrencesWereReplacedInDefaultRb();
    }

    private void assertAllOcurrencesWereReplacedInDefaultRb() throws IOException {
	File fileLocation = getResource(RECIPES_FOLDER + "/service" + id + "/attributes/default.rb");
	String fileData = FileUtils.readFileToString(fileLocation);

	// Ensure the ocurrences of $NAME were replaced with the service ID
	assertTrue(fileData.contains(id));
	assertFalse(fileData.contains("$NAME"));

	// Ensure the ocurrences of $WARFILE were replaced with war file name
	String warFile = serviceSpec.getFileName();
	assertTrue(fileData.contains(warFile));
	assertFalse(fileData.contains("$WARFILE"));

	// Ensure the ocurrences of $URL were replaced with the code location
	assertTrue(fileData.contains(codeLocationURI));
	assertFalse(fileData.contains("$URL"));

	// Ensures the remainder of the file is left untouched
	assertTrue(fileData.contains("IMPORTANT DEVELOPMENT NOTICE:"));
    }

    private void assertAllOcurrencesWereReplacedInDefaultRecipe() throws IOException {

	File fileLocation = getResource(RECIPES_FOLDER + "/service" + id + "/recipes/war.rb");
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

	RecipeBundle recipe = recipeBuilder.createServiceRecipeBundle(serviceSpec);

	assertTemplateFolderWasCopied();
	assertAllOcurrencesInMetadataRbWereReplaced();
	assertAllOcurrencesWereReplacedInDefaultRb();
	assertAllOcurrencesWereReplacedInDefaultRecipe();
	assertFilesAreAvailableInFolder(recipe);
    }

    private void assertFilesAreAvailableInFolder(RecipeBundle recipe) throws IOException {

	String fileData = FileUtils.readFileToString(new File(recipe.getCookbookFolder() + "/"
		+ recipe.getServiceRecipe().getCookbookName() + "/attributes/default.rb"));

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
