package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;


public class RecipeBuilderImpl extends BaseRecipeBuilder {
	
	private static final String TEMPLATE_DIR = "src/main/resources/chef/service-deploy-recipe-template";

	public RecipeBuilderImpl(String recipeName) {
		super(TEMPLATE_DIR, recipeName);
	}
	
	@Override
	public String replace(String content, ServiceSpec serviceSpec) {
		content = content.replace("$NAME", serviceSpec.getName());
		content = content.replace("$URL", serviceSpec.getCodeUri());
		content = content.replace("$WARFILE", serviceSpec.getFileName());
		return content;
	}

}
