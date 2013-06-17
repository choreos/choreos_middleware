/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.tests.IntegrationTest;

@Category(IntegrationTest.class)
public class NodesResourceTest extends BaseTest {

    @Test
    public void getNode() throws Exception {

	CloudProvider cp = new FixedCloudProvider();
	CloudNode node = cp.createOrUseExistingNode(new NodeSpec());

	NodePoolManager npm = new NodesClient(nodePoolManagerHost);
	CloudNode returnedNode = npm.getNode(node.getId());

	assertEquals(node.getId(), returnedNode.getId());
	assertEquals(node.getHostname(), returnedNode.getHostname());
	assertEquals(node.getIp(), returnedNode.getIp());
    }

    @Test
    public void getInvalidNode() throws NodeNotFoundException {

	String NO_EXISTING_NODE = "nodes/696969696969";
	NodePoolManager npm = new NodesClient(nodePoolManagerHost);
	CloudNode node = npm.getNode(NO_EXISTING_NODE);
	assertTrue(node == null);
    }

}