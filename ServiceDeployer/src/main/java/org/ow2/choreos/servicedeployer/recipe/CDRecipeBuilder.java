package org.ow2.choreos.servicedeployer.recipe;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public class CDRecipeBuilder implements RecipeBuilder {

	private Logger logger = Logger.getLogger(CDRecipeBuilder.class);
	
	private static final String RECIPE_NAME = "default";
	private static final String TEMPLATE_DIR = "src/main/resources/chef/cd_cookbook_template";
	private static final File DEST_DIR = new File("src/main/resources/chef/recipes/");

	private String recipeFile;
	
	@Override
	public Recipe createRecipe(Service service) {

		Recipe recipe = new Recipe();

		try {
			
			recipe.setName(RECIPE_NAME);
			recipe.setCookbookName(service.getName());
			this.recipeFile = RECIPE_NAME + ".rb";
			File targetFolder = getTargetFolder(service);
			recipe.setCookbookFolder(targetFolder.getAbsolutePath());
			
			if (targetFolder.exists()) {
				
				logger.info("Recipe " + this.recipeFile
						+ " already exists. NOT going to overwriting it");
				
			} else {
				
				copyTemplate(service, targetFolder);
				changeAttributesDefaultRb(service);
				changeServerRecipe(service);
			}

			return recipe;
			
		} catch (IOException e) {
			logger.error("Could not create recipe", e);
		}

		return null;
	}
	
	private void changeServerRecipe(Service service) throws IOException {
		changeFileContents(service, service.getName()
				+ "/recipes/"+this.recipeFile);
	}

	void changeAttributesDefaultRb(Service service) throws IOException {
		changeFileContents(service, service.getName()
				+ "/attributes/default.rb");
	}
	
	private void changeFileContents(Service service, String fileLocation)
			throws IOException {

		File file = new File(DEST_DIR + "/" + fileLocation);
		String fileData = FileUtils.readFileToString(file);

		fileData = fileData.replace("$NAME", service.getName());
		fileData = fileData.replace("$CD_URL", service.getSpec().getCodeUri());

		FileUtils.deleteQuietly(file);
		FileUtils.writeStringToFile(file, fileData);
	}
	
	private File getTargetFolder(Service service) {
		
		String destPath = DEST_DIR.getAbsolutePath() + "/" + service.getName();
		File destFolder = new File(destPath);
		return destFolder;
	}
	
	private String copyTemplate(Service service, File targetFolder) throws IOException {
		
		File srcFolder = new File(TEMPLATE_DIR);
		
		synchronized(CDRecipeBuilder.class) {
			try {
				FileUtils.copyDirectory(srcFolder, targetFolder);
			} catch (IOException e) {
				logger.warn("IOException when copying CD cookbook template; it should not happen");
				if (!targetFolder.exists())
					throw e;
			}
		}

		return targetFolder.getAbsolutePath();
	}

}
