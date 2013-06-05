/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;

/**
 * Recipes to be applied on bootstrap
 * 
 * @author leonardo, thiago
 * 
 */
public class DefaultRecipes {

    public static List<String> getDefaultRecipes() {
	return new ArrayList<String>(Arrays.asList(DeploymentManagerConfiguration.getMultiple("DEFAULT_RECIPES")));
    }

}
