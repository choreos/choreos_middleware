package org.ow2.choreos.deployment.services.recipe;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.Configuration;

import org.ow2.choreos.ee.api.DeployableServiceSpec;


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
	
	public Recipe createRecipe(DeployableServiceSpec serviceSpec) {
		
		Recipe recipe = new Recipe();

		try {
			
			recipe.setName(this.recipeName);
			recipe.setCookbookName("service" + serviceSpec.getUUID());
			this.recipeFile = this.recipeName + ".rb";
			File targetFolder = getTargetFolder(serviceSpec);
			recipe.setCookbookFolder(targetFolder.getAbsolutePath());
			
			boolean cache = Boolean.parseBoolean(Configuration.get("RECIPES_CACHE"));
			
			if (targetFolder.exists() && cache) {
				
				logger.warn("Recipe "
						+ this.recipeFile
						+ " already exists and recipe cache is True. NOT going to overwriting it");
				
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
	public abstract String replace(String content, DeployableServiceSpec serviceSpec);

	// methods have "package visibility" to testing purposes

	void changeMetadataRb(DeployableServiceSpec serviceSpec) throws IOException {
		changeFileContents(serviceSpec, "/service" + serviceSpec.getUUID()
				+ "/metadata.rb");
	}

	private void changeServerRecipe(DeployableServiceSpec serviceSpec) throws IOException {
		changeFileContents(serviceSpec, "/service" + serviceSpec.getUUID()
				+ "/recipes/"+this.recipeFile);
	}

	void changeAttributesDefaultRb(DeployableServiceSpec serviceSpec) throws IOException {
		changeFileContents(serviceSpec, "/service" + serviceSpec.getUUID()
				+ "/attributes/default.rb");
	}

	private void changeFileContents(DeployableServiceSpec serviceSpec, String fileLocation)
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
	
	private File getTargetFolder(DeployableServiceSpec serviceSpec) {
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + serviceSpec.getUUID();
		File destFolder = new File(destPath);
		
		return destFolder;
	}

	String copyTemplate(DeployableServiceSpec serviceSpec) throws IOException {
		
		File srcFolder = new File(this.templateDir);
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + serviceSpec.getUUID();
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
