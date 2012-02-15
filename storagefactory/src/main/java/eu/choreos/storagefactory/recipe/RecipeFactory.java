package eu.choreos.storagefactory.recipe;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import eu.choreos.storagefactory.datamodel.StorageNode;

import br.usp.ime.choreos.nodepoolmanager.Configuration;

public class RecipeFactory {

	public Recipe createRecipe(StorageNode storage) {
		Recipe recipe = new Recipe();
		String absolutePath;

		try {
			absolutePath = copyTemplate(storage);
			changeMetadataRb(storage);
			changeAttributesDefaultRb(storage);
			changeServerRecipe(storage);

			recipe.setName("storage" + storage.getUuid());
			recipe.setFolder(absolutePath);
			return recipe;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void changeMetadataRb(StorageNode storage) throws IOException {
		changeFileContents(storage, "chef/storage" + storage.getUuid()
				+ "/metadata.rb");
	}

	public void changeServerRecipe(StorageNode storage) throws IOException {
		changeFileContents(storage, "chef/storage" + storage.getUuid()
				+ "/recipes/default.rb");
	}

	public void changeAttributesDefaultRb(StorageNode storage)
			throws IOException {
		changeFileContents(storage, "chef/storage" + storage.getUuid()
				+ "/attributes/default.rb");
	}

	private void changeFileContents(StorageNode storage, String fileLocation)
			throws IOException {
		URL scriptFile = ClassLoader.getSystemResource(fileLocation);

		String fileData = FileUtils.readFileToString(new File(scriptFile
				.getFile()));

		fileData = fileData.replace("$UUID", storage.getUuid());
		fileData = fileData.replace("$USER", storage.getUser());
		fileData = fileData.replace("$PASSWORD", storage.getPassword());
		fileData = fileData.replace("$SCHEMA", storage.getSchema());
		fileData = fileData.replace("$TYPE", storage.getType());

		FileUtils.deleteQuietly(new File(scriptFile.getFile()));
		FileUtils.writeStringToFile((new File(scriptFile.getFile())), fileData);
	}

	public String copyTemplate(StorageNode storage) throws IOException {
		URL scriptFile = ClassLoader
				.getSystemResource("chef/storage-recipe-template");
		URL destURL = ClassLoader.getSystemResource("chef");

		File srcFolder = new File(scriptFile.getFile());
		File destFolder = new File(destURL.getFile().concat("/storage" + storage.getUuid()));

		FileUtils.copyDirectory(srcFolder, destFolder);

		return destFolder.getAbsolutePath();
	}
}
