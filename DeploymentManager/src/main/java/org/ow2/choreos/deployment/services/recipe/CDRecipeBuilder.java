package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;

public class CDRecipeBuilder extends BaseRecipeBuilder {

	private static final String TEMPLATE_DIR = "src/main/resources/chef/cd_cookbook_template";
	private static final String RECIPE_NAME = "default";

	public CDRecipeBuilder() {
		super(TEMPLATE_DIR, RECIPE_NAME);
	}

	@Override
	public String replace(String content, ServiceSpec serviceSpec) {
		content = content.replace("$NAME", serviceSpec.getName());
		content = content.replace("$CD_URL", serviceSpec.getDeployableUri());
		return content;
	}

}
