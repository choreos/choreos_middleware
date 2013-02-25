package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.deployment.services.datamodel.PackageType;

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
			throw new IllegalArgumentException("Service type " + serviceType
					+ " not supported");
		}
	}
}
