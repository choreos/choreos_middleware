package org.ow2.choreos.servicedeployer.recipe;

import org.ow2.choreos.servicedeployer.datamodel.ArtifactType;

public class RecipeBuilderFactory {

	public static RecipeBuilder getRecipeBuilderInstance(ArtifactType serviceType) {

		switch (serviceType) {
		case TOMCAT:
			return new RecipeBuilderImpl();
		case COMMAND_LINE:
			return new RecipeBuilderImpl();
		case EASY_ESB:
			return new CDRecipeBuilder();
		default:
			throw new IllegalArgumentException("Service type " + serviceType
					+ " not supported");
		}
	}
}
