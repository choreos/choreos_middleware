/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class CDRecipeBuilder extends BaseRecipeBuilder {

    private static final String TEMPLATE_DIR = "src/main/resources/chef/cd_cookbook_template";
    private static final String RECIPE_NAME = "default";

    public CDRecipeBuilder() {
	super(TEMPLATE_DIR, RECIPE_NAME);
    }

    @Override
    public String replace(String content, DeployableServiceSpec serviceSpec) {
	content = content.replace("$NAME", serviceSpec.getUuid());
	content = content.replace("$CD_URL", serviceSpec.getPackageUri());
	return content;
    }

}
