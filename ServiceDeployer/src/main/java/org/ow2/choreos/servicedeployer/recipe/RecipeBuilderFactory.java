package org.ow2.choreos.servicedeployer.recipe;

import org.ow2.choreos.servicedeployer.datamodel.ServiceType;

public class RecipeBuilderFactory {

	public static RecipeBuilder getRecipeBuilderInstance(ServiceType serviceType) {

		switch (serviceType) {
		case TOMCAT:
			return new RecipeBuilderImpl();
		case COMMAND_LINE:
			return new RecipeBuilderImpl();
		case EASY_ESB:
			return new RecipeBuilderImpl();
		default:
			throw new IllegalArgumentException("Service type " + serviceType
					+ " not supported");
		}
	}
}
