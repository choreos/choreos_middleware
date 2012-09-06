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

		try {
			
			String recipeName = getRecipeName(service);
			recipe.setName(recipeName);
			recipe.setCookbookName("service" + service.getName());
			this.recipeFile = recipeName + ".rb";
			File targetFolder = getTargetFolder(service);
			recipe.setCookbookFolder(targetFolder.getAbsolutePath());
			
			if (targetFolder.exists()) {
				
				logger.info("Recipe " + this.recipeFile
						+ " already exists. NOT going to overwriting it");
				
			} else {
				
				copyTemplate(service);
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
		ServiceType type = service.getSpec().getType();
		String extension = type.getExtension();
		String recipeName = "";
		if (type == ServiceType.COMMAND_LINE || type == ServiceType.TOMCAT) {
			recipeName = extension;
		} else if (type == ServiceType.EASY_ESB) {
			recipeName = PETALS_RECIPE;
		}
		return recipeName;
	}
	
	// methods have "package visibility" to testing purposes

	void changeMetadataRb(Service service) throws IOException {
		changeFileContents(service, "/service" + service.getName()
				+ "/metadata.rb");
	}

	private void changeServerRecipe(Service service) throws IOException {
		changeFileContents(service, "/service" + service.getName()
				+ "/recipes/"+this.recipeFile);
	}

	void changeAttributesDefaultRb(Service service) throws IOException {
		changeFileContents(service, "/service" + service.getName()
				+ "/attributes/default.rb");
	}

	private void changeFileContents(Service service, String fileLocation)
			throws IOException {

		File file = new File(DEST_DIR + fileLocation);
		String fileData = FileUtils.readFileToString(file);

		fileData = fileData.replace("$NAME", service.getName());
		fileData = fileData.replace("$URL", service.getSpec().getCodeUri());
		fileData = fileData.replace("$WARFILE", service.getFile());

		FileUtils.deleteQuietly(file);
		FileUtils.writeStringToFile(file, fileData);
	}
	
	private File getTargetFolder(Service service) {
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + service.getName();
		File destFolder = new File(destPath);
		
		return destFolder;
	}

	String copyTemplate(Service service) throws IOException {
		
		File srcFolder = new File(TEMPLATE_DIR);
		
		String destPath = DEST_DIR.getAbsolutePath() + "/service" + service.getName();
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
