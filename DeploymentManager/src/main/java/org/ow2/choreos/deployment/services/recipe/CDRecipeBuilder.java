package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;

public class CDRecipeBuilder extends BaseRecipeBuilder {

	@Override
	public String replace(String content, ServiceSpec serviceSpec) {
		content = content.replace("$NAME", serviceSpec.getName());
		content = content.replace("$CD_URL", serviceSpec.getCodeUri());
		return content;
	}

}
