package org.ow2.choreos.servicedeployer.recipe;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;


public class RecipeBuilderImpl implements RecipeBuilder {
	
	private Logger logger = Logger.getLogger(RecipeBuilderImpl.class);
	
	private static final String TEMPLATE_DIR = "src/main/resources/chef/service-deploy-recipe-template";
	private static final File DEST_DIR = new File("src/main/resources/chef/recipes");
	private static final String PETALS_RECIPE = "sa";
	
	private String recipeFile;
	
	public Recipe createRecipe(Service service) {
		
		Recipe recipe = new Recipe();
		String absolutePath;

		try {
			
			String recipeName = getRecipeName(service);
			recipe.setName(recipeName);
			recipe.setCookbookName("service" + service.getId());
			this.recipeFile = recipeName + ".rb";
			File targetFolder = getTargetFolder(service);
			
			if (targetFolder.exists()) {
				
				logger.info("Recipe " + this.recipeFile
						+ " already exists. NOT going to overwriting it");
				
			} else {
				
				absolutePath = copyTemplate(service);
				recipe.setCookbookFolder(absolutePath);
	
				changeMetadataRb(service);
				changeAttributesDefaultRb(service);
				changeServerRecipe(service);
			}

			return recipe;
		} catch (IOException e) {
			logger.error("Could not create recipe", e);
		}

		return null;

	}

	private String getRecipeName(Service service) {
		String extension = service.getExtension();
		String recipeName = "";
		ServiceType type = service.getType();
		if (type == ServiceType.JAR || type == ServiceType.WAR) {
			recipeName = extension;
		} else if (type == ServiceType.PETALS) {
			recipeName = PETALS_RECIPE;
		}
		return recipeName;
	}
	
	// methods have "package visibility" to testing purposes

	void changeMetadataRb(Service service) throws IOException {
		changeFileContents(service, "/service" + service.getId()
				+ "/metadata.rb");
	}

	private void changeServerRecipe(Service service) throws IOException {
		changeFileContents(service, "/service" + service.getId()
				+ "/recipes/"+this.recipeFile);
	}

	void changeAttributesDefaultRb(Service service) throws IOException {
		changeFileContents(service, "/service" + service.getId()
				+ "/attributes/default.rb");
	}

	private void changeFileContents(Service service, String fileLocation)
			throws IOException {

		File file = new File(DEST_DIR + fileLocation);
		String fileData = FileUtils.readFileToString(file);

		fileData = fileData.replace("$NAME", service.getId());
		fileData = fileData.replace("$URL", service.getCodeLocationURI());
		fileData = fileData.replace("$WARFILE", service.getFile());

		FileUtils.deleteQuietly(file);
		FileUtils.writeStringToFile(file, fileData);
	}
	
	private File getTargetFolder(Service service) {
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + service.getId();
		File destFolder = new File(destPath);
		
		return destFolder;
	}

	String copyTemplate(Service service) throws IOException {
		
		File srcFolder = new File(TEMPLATE_DIR);
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + service.getId();
		File destFolder = new File(destPath);
		
		synchronized(RecipeBuilderImpl.class) {
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
