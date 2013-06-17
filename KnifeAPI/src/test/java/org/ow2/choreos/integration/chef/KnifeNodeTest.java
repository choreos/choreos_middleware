/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chef;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.tests.IntegrationTest;

/**
 * Requirement: fill the CHEF_CONFIG_FILE in the test/resources/chef.properties file
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class KnifeNodeTest extends BaseKnifeTest {

    @Test
    public void shouldAddAndRemoveNode() throws KnifeException {

	String nodeName = "VirtualTestNode";

	List<String> nodes = knife.node().list();
	if (nodes.contains(nodeName)) {
	    knife.node().delete(nodeName);
	}

	nodes = knife.node().list();
	assertFalse(nodes.contains(nodeName));

	knife.node().create(nodeName);
	nodes = knife.node().list();
	assertTrue(nodes.contains(nodeName));

	knife.node().delete(nodeName);
	nodes = knife.node().list();
	assertFalse(nodes.contains(nodeName));

    }
}
