/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ow2.choreos.deployment.nodes.chef.ConfigToChef;
import org.ow2.choreos.nodes.datamodel.Config;

public class ConfigToChefTest {

    @Test
    public void shouldGetCookBookAndRecipeName() {
	Config config = new Config();
	config.setName("getting-started::default");

	assertEquals(ConfigToChef.getCookbookNameFromConfigName(config.getName()), "getting-started");
	assertEquals(ConfigToChef.getRecipeNameFromConfigName(config.getName()), "default");
    }

    @Test
    public void shouldGetCookBookAndRecipeNameWithoutRecipeName() {
	Config config = new Config();
	config.setName("getting-started");

	assertEquals(ConfigToChef.getCookbookNameFromConfigName(config.getName()), "getting-started");
	assertEquals(ConfigToChef.getRecipeNameFromConfigName(config.getName()), "default");
    }
}
