package eu.choreos.storagefactory.recipe;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import br.usp.ime.choreos.nodepoolmanager.Configuration;

public class RecipeFactory {

	public Recipe createRecipe(String uuid) {
		Recipe recipe = new Recipe();
		String absolutePath;
		
		try {
			absolutePath = createNewRecipeFromTemplate(uuid);
			changeMetadataRb(uuid);
			changeAttributesServerRb(uuid);
			changeServerRecipe(uuid);

			recipe.setName("mysql_"+uuid);
			recipe.setFolder(absolutePath);
			return recipe;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public void changeMetadataRb(String uuid) throws IOException {
		changeFileContents(uuid, "chef/mysql_" + uuid + "/metadata.rb");
	}

	public void changeServerRecipe(String uuid) throws IOException {
		changeFileContents(uuid, "chef/mysql_" + uuid + "/recipes/server.rb");
	}

	public void changeAttributesServerRb(String uuid) throws IOException {
		changeFileContents(uuid, "chef/mysql_" + uuid + "/attributes/server.rb");
	}

	private void changeFileContents(String uuid, String fileLocation)
			throws IOException {
		URL scriptFile = ClassLoader.getSystemResource(fileLocation);
		
		String fileData = FileUtils.readFileToString(new File(scriptFile
				.getFile()));

		fileData = fileData.replace("$uuid", uuid);

		FileUtils.deleteQuietly(new File(scriptFile.getFile()));
		FileUtils.writeStringToFile((new File(scriptFile.getFile())), fileData);
	}

	public String createNewRecipeFromTemplate(String uuid) throws IOException {
		URL scriptFile = ClassLoader
				.getSystemResource("chef/mysql-recipe-template");
		URL  destURL = ClassLoader.getSystemResource("chef");

		File srcFolder = new File(scriptFile.getFile());
		File destFolder = new File(destURL.getFile().concat("/mysql_"+uuid));

		
		FileUtils.copyDirectory(srcFolder, destFolder);
		
		return destFolder.getAbsolutePath();
	}
}
