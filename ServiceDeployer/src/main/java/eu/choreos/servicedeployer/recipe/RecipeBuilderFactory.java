package eu.choreos.servicedeployer.recipe;

import eu.choreos.servicedeployer.ServiceType;

public class RecipeBuilderFactory {

	public static RecipeBuilder getRecipeBuilderInstance(ServiceType serviceType) {

		switch (serviceType) {
		case WAR:
			return new RecipeBuilderImpl();
		case JAR:
			return new RecipeBuilderImpl();
		default:
			throw new IllegalArgumentException("Service type " + serviceType
					+ " not supported");
		}
	}
}
