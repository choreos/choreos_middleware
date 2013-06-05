/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.services.datamodel.PackageType;

public class RecipeBuilderFactory {

    public static RecipeBuilder getRecipeBuilderInstance(PackageType serviceType) {

	switch (serviceType) {
	case TOMCAT:
	    return new RecipeBuilderImpl("war");
	case COMMAND_LINE:
	    return new RecipeBuilderImpl("jar");
	case EASY_ESB:
	    return new CDRecipeBuilder();
	default:
	    throw new IllegalArgumentException("Service type " + serviceType + " not supported");
	}
    }
}
