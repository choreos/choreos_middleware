package eu.choreos.servicedeployer.recipe;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import eu.choreos.servicedeployer.datamodel.Service;

public class WARRecipeBuilder implements RecipeBuilder {
	
	private static final File DEST_DIR = new File("src/main/resources/chef");
	
	public Recipe createRecipe(Service service) {
		Recipe recipe = new Recipe();
		String absolutePath;

		try {
			absolutePath = copyTemplate(service);
			changeMetadataRb(service);
			changeAttributesDefaultRb(service);
			changeServerRecipe(service);

			recipe.setName("service" + service.getId());
			recipe.setFolder(absolutePath);

			return recipe;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	// methods have "package visibility" to test purposes

	void changeMetadataRb(Service service) throws IOException {
		changeFileContents(service, "chef/service" + service.getId()
				+ "/metadata.rb");
	}

	void changeServerRecipe(Service service) throws IOException {
		changeFileContents(service, "chef/service" + service.getId()
				+ "/recipes/default.rb");
	}

	void changeAttributesDefaultRb(Service service) throws IOException {
		changeFileContents(service, "chef/service" + service.getId()
				+ "/attributes/default.rb");
	}

	private void changeFileContents(Service service, String fileLocation)
			throws IOException {

		File file = new File("src/main/resources/" + fileLocation);
		String fileData = FileUtils.readFileToString(file);

		fileData = fileData.replace("$NAME", service.getId());
		fileData = fileData.replace("$URL", service.getCodeLocationURI());
		fileData = fileData.replace("$WARFILE", service.getWarFile());

		FileUtils.deleteQuietly(file);
		FileUtils.writeStringToFile(file, fileData);
	}

	String copyTemplate(Service service) throws IOException {
		
		File srcFolder = new File("src/main/resources/chef/tomcat-service-deploy-recipe-template");
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + service.getId();
		File destFolder = new File(destPath);
		
		FileUtils.copyDirectory(srcFolder, destFolder);

		return destFolder.getAbsolutePath();
	}
}
