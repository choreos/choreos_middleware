package eu.choreos.servicedeployer.recipe;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import eu.choreos.servicedeployer.datamodel.Service;

public class RecipeFactory {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void changeScriptTemplate(Service service) throws IOException {
		changeFileContents(service, "chef/service" + service.getId()
				+ "/templates/default/deploy-service.sh.erb");
	}

	public void changeMetadataRb(Service service) throws IOException {
		changeFileContents(service, "chef/service" + service.getId()
				+ "/metadata.rb");
	}

	public void changeServerRecipe(Service service) throws IOException {
		changeFileContents(service, "chef/service" + service.getId()
				+ "/recipes/default.rb");
	}

	public void changeAttributesDefaultRb(Service service) throws IOException {
		changeFileContents(service, "chef/service" + service.getId()
				+ "/attributes/default.rb");
	}

	private void changeFileContents(Service service, String fileLocation)
			throws IOException {
		URL scriptFile = ClassLoader.getSystemResource(fileLocation);

		File file = new File(scriptFile.getFile());
		String fileData = FileUtils.readFileToString(file);

		fileData = fileData.replace("$NAME", service.getId());
		fileData = fileData.replace("$URL", service.getCodeLocationURI());
		fileData = fileData.replace("$WARFILE", service.getWarFile());

		FileUtils.deleteQuietly(file);
		FileUtils.writeStringToFile(file, fileData);
	}

	public String copyTemplate(Service service) throws IOException {
		URL scriptFile = ClassLoader
				.getSystemResource("chef/tomcat-service-deploy-recipe-template");
		URL destURL = RecipeDeployer.destURL;

		File srcFolder = new File(scriptFile.getFile());
		File destFolder = new File(destURL.getFile().concat(
				"/service" + service.getId()));

		FileUtils.copyDirectory(srcFolder, destFolder);

		return destFolder.getAbsolutePath();
	}
}
