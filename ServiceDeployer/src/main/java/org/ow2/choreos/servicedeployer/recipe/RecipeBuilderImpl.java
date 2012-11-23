package org.ow2.choreos.servicedeployer.recipe;

import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;


public class RecipeBuilderImpl extends BaseRecipeBuilder {

	@Override
	public String replace(String content, ServiceSpec serviceSpec) {
		content = content.replace("$NAME", serviceSpec.getName());
		content = content.replace("$URL", serviceSpec.getCodeUri());
		content = content.replace("$WARFILE", serviceSpec.getFileName());
		return content;
	}

}
