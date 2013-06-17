/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chef.ChefNode;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.tests.IntegrationTest;

@Category(IntegrationTest.class)
public class RunListTest extends BaseKnifeTest {

    String nodeName = "VirtualTestNode";

    @Test
    public void shouldAddRecipesToRunlist() throws KnifeException {

	clearNode();

	String[] recipes = new String[] { "apt", "java", "tomcat" };
	for (String recipe : recipes) {
	    knife.node().runListAdd(nodeName, recipe);
	}

	ChefNode node = knife.node().show(nodeName);
	assertEquals(nodeName, node.getName());

	assertEquals(3, node.getRunList().size());
	for (String recipe : recipes) {
	    assertTrue(node.hasRecipeOnRunlist(recipe + "::default]"));
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
