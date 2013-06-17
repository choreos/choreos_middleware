/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chef;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ow2.choreos.chef.impl.ChefScripts;

public class ChefScriptsTest {

    @Test
    public void chefScripts() throws Exception {

	String config = "knife.rb";
	String user = "myUser";
	String ip = "127.0.0.1";
	String hostname = "myHost";
	String recipeFullName = "cook::recipe";
	String key = "myKey";

	ChefScripts cs = new ChefScripts(config);

	String command = cs.getKnifeBootstrap(key, ip, user, null);
	String expected = "knife bootstrap 127.0.0.1 -x myUser -i " + key + " --no-host-key-verify --sudo -c " + config;
	assertEquals(expected.trim(), command.trim());

	command = cs.getKnifeBootstrap(key, ip, user, new ArrayList<String>());
	expected = "knife bootstrap 127.0.0.1 -x myUser -i " + key + " --no-host-key-verify --sudo -c " + config;
	assertEquals(expected.trim(), command.trim());

	List<String> recipes = new ArrayList<String>();
	recipes.add("easyesb");
	command = cs.getKnifeBootstrap(key, ip, user, recipes);
	expected = "knife bootstrap 127.0.0.1 -x myUser -i " + key + " --no-host-key-verify --sudo -c " + config
		+ " --run-list easyesb";
	assertEquals(expected.trim(), command.trim());

	recipes = new ArrayList<String>();
	recipes.add("easyesb");
	recipes.add("ganglia");
	command = cs.getKnifeBootstrap(key, ip, user, recipes);
	expected = "knife bootstrap 127.0.0.1 -x myUser -i " + key + " --no-host-key-verify --sudo -c " + config
		+ " --run-list easyesb, ganglia";
	assertEquals(expected.trim(), command.trim());

	command = cs.getKnifeRunListAdd(hostname, recipeFullName);
	expected = "knife node run_list add myHost cook::recipe -c " + config;
	assertEquals(expected.trim(), command.trim());
    }

}
