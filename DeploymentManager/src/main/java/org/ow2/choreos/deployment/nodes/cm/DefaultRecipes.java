package org.ow2.choreos.deployment.nodes.cm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ow2.choreos.deployment.Configuration;

/**
 * Recipes to be applied on bootstrap
 * 
 * @author leonardo, thiago
 * 
 */
public class DefaultRecipes {

    public static List<String> getDefaultRecipes() {
	return new ArrayList<String>(Arrays.asList(Configuration.getMultiple("DEFAULT_RECIPES")));
    }

}
