package org.ow2.choreos.npm.chef;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Maps config names to chef cookbook / recipes
 * @author leonardo
 *
 */
public class ConfigToChef {

	private static String DEFAULT_RECIPE = "default";
	private static Pattern pattern = Pattern.compile("(.*?)::(.*?)");
	
	public static String getCookbookNameFromConfigName(String configName) {
		
		Matcher matcher = pattern.matcher(configName);
		if (matcher.matches())
			return matcher.group(1);
		else // there is no recipe name
			return configName;
	}

	public static String getRecipeNameFromConfigName(String configName) {
		
		Matcher matcher = pattern.matcher(configName);
		if (matcher.matches())
			return matcher.group(2);
		else // there is no recipe name
			return DEFAULT_RECIPE;
	}
	
}
