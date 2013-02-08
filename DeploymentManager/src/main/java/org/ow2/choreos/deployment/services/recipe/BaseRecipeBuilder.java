package org.ow2.choreos.deployment.services.recipe;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;


public abstract class BaseRecipeBuilder implements RecipeBuilder {
	
	private Logger logger = Logger.getLogger(BaseRecipeBuilder.class);
	
	private static final File DEST_DIR = new File("src/main/resources/chef/recipes");
	
	private final String templateDir;
	private final String recipeName;
	private String recipeFile;
	
	public BaseRecipeBuilder(String templateDir, String recipeName) {
		this.templateDir = templateDir;
		this.recipeName = recipeName;
	}
	
	public Recipe createRecipe(ServiceSpec serviceSpec) {
		
		Recipe recipe = new Recipe();

		try {
			
			recipe.setName(this.recipeName);
			recipe.setCookbookName("service" + serviceSpec.getName());
			this.recipeFile = this.recipeName + ".rb";
			File targetFolder = getTargetFolder(serviceSpec);
			recipe.setCookbookFolder(targetFolder.getAbsolutePath());
			
			if (targetFolder.exists()) {
				
				logger.warn("Recipe " + this.recipeFile
						+ " already exists. NOT going to overwriting it");
				
			} else {
				
				copyTemplate(serviceSpec);
				changeMetadataRb(serviceSpec);
				changeAttributesDefaultRb(serviceSpec);
				changeServerRecipe(serviceSpec);
			}

			return recipe;
			
		} catch (IOException e) {
			logger.error("Could not create recipe", e);
		}

		return null;

	}
	
	/**
	 * Replace content from cookbook files with information retrieved from service specification
	 * @param content
	 * @param serviceSpec
	 * @return
	 */
	public abstract String replace(String content, ServiceSpec serviceSpec);

	// methods have "package visibility" to testing purposes

	void changeMetadataRb(ServiceSpec serviceSpec) throws IOException {
		changeFileContents(serviceSpec, "/service" + serviceSpec.getName()
				+ "/metadata.rb");
	}

	private void changeServerRecipe(ServiceSpec serviceSpec) throws IOException {
		changeFileContents(serviceSpec, "/service" + serviceSpec.getName()
				+ "/recipes/"+this.recipeFile);
	}

	void changeAttributesDefaultRb(ServiceSpec serviceSpec) throws IOException {
		changeFileContents(serviceSpec, "/service" + serviceSpec.getName()
				+ "/attributes/default.rb");
	}

	private void changeFileContents(ServiceSpec serviceSpec, String fileLocation)
			throws IOException {

		File file = new File(DEST_DIR + fileLocation);
		
		String fileData = null;
		synchronized (BaseRecipeBuilder.class) {
			fileData = FileUtils.readFileToString(file);
		}

		fileData = this.replace(fileData, serviceSpec);

		synchronized (BaseRecipeBuilder.class) {
			FileUtils.deleteQuietly(file);
			FileUtils.writeStringToFile(file, fileData);
		}
	}
	
	private File getTargetFolder(ServiceSpec serviceSpec) {
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + serviceSpec.getName();
		File destFolder = new File(destPath);
		
		return destFolder;
	}

	String copyTemplate(ServiceSpec serviceSpec) throws IOException {
		
		File srcFolder = new File(this.templateDir);
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + serviceSpec.getName();
		File destFolder = new File(destPath);
		
		synchronized(BaseRecipeBuilder.class) {
			try {
				FileUtils.copyDirectory(srcFolder, destFolder);
			} catch (IOException e) {
				logger.warn("IOException when copying recipe template; it should not happen");
				File dest = new File(destPath);
				if (!dest.exists())
					throw e;
			}
		}

		return destFolder.getAbsolutePath();
	}
}
