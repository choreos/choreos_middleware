package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.ee.api.DeployableServiceSpec;


public class RecipeBuilderImpl extends BaseRecipeBuilder {
	
	private static final String TEMPLATE_DIR = "src/main/resources/chef/service-deploy-recipe-template";

	public RecipeBuilderImpl(String recipeName) {
		super(TEMPLATE_DIR, recipeName);
	}
	
	@Override
	public String replace(String content, DeployableServiceSpec serviceSpec) {
		content = content.replace("$NAME", serviceSpec.getUUID());
		content = content.replace("$PackageURL", serviceSpec.getPackageUri());
		content = content.replace("$WARFILE", serviceSpec.getFileName());
		return content;
	}

}
