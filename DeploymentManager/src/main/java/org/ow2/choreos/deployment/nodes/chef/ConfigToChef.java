/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.chef;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Maps config names to chef cookbook / recipes
 * 
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
	else
	    // there is no recipe name
	    return configName;
    }

    public static String getRecipeNameFromConfigName(String configName) {

	Matcher matcher = pattern.matcher(configName);
	if (matcher.matches())
	    return matcher.group(2);
	else
	    // there is no recipe name
	    return DEFAULT_RECIPE;
    }

}
