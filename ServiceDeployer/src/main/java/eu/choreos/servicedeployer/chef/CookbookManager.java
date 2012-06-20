package eu.choreos.servicedeployer.chef;

import java.io.File;

import eu.choreos.servicedeployer.recipe.Recipe;
import eu.choreos.servicedeployer.utils.CommandLine;

public class CookbookManager {

	public static void uploadCookbook(Recipe recipe) {
		uploadCookbook(recipe, false);
	}
	
	public static String uploadCookbook(Recipe recipe, boolean verbose) {
		
		String command = ChefScripts.getUploadCookbook(recipe.getCookbookName(), ".");
		File folder = new File(recipe.getCookbookFolder());
		String parentFolder = folder.getParentFile().getAbsolutePath();
		String result = CommandLine.runLocalCommand(command, parentFolder, verbose);
		return result;
	}
	
	public static String deleteCookbook(String cookbookName) {
		
		String command = ChefScripts.getDeleteCookbook(cookbookName);
		return CommandLine.runLocalCommand(command);
	}
	
	public static String listCookbooks() {
		
		String command = ChefScripts.getListCookbooks();
		return CommandLine.runLocalCommand(command);
	}
}
