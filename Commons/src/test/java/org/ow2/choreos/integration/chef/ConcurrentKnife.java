/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chef.ChefNode;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.tests.IntegrationTest;

/**
 * Requirement: fill the CHEF_CONFIG_FILE in the test/resources/chef.properties file
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class ConcurrentKnife extends BaseKnifeTest {

    private String nodeName = "VirtualTestNode";

    @Test
    public void shouldAddRecipesConcurrently() throws KnifeException, InterruptedException {

	clearNode();

	String[] recipes = new String[] { "apt", "java", "tomcat" };
	List<Thread> trds = new ArrayList<Thread>();
	for (String recipe : recipes) {
	    Thread trd = new Thread(new Adder(recipe));
	    trds.add(trd);
	    trd.start();
	}

	for (Thread trd : trds) {
	    trd.join();
	}

	ChefNode node = knife.node().show(nodeName);
	assertEquals(3, node.getRunList().size());
	for (String recipe : recipes) {
	    assertTrue(node.hasRecipeOnRunlist(recipe + "::default"));
	}
    }

    private class Adder implements Runnable {

	String recipe;

	public Adder(String recipe) {
	    this.recipe = recipe;
	}

	@Override
	public void run() {
	    try {
		knife.node().runListAdd(nodeName, recipe);
	    } catch (KnifeException e) {
		e.printStackTrace();
	    }
	}
    }

    private void clearNode() throws KnifeException {

	List<String> nodes = knife.node().list();
	if (nodes.contains(nodeName)) {
	    knife.node().delete(nodeName);
	}
	knife.node().create(nodeName);
    }

}
