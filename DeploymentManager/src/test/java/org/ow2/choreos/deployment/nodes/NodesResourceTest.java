package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.tests.IntegrationTest;

@Category(IntegrationTest.class)
public class NodesResourceTest extends BaseTest {

	@Test
	public void getNode() throws Exception {

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null);
		
		NodePoolManager npm = new NodesClient(nodePoolManagerHost);
		Node returnedNode = npm.getNode(node.getId());

		assertEquals(node.getId(), returnedNode.getId());
		assertEquals(node.getHostname(), returnedNode.getHostname());
		assertEquals(node.getIp(), returnedNode.getIp());
	}

	@Test
	public void getInvalidNode() throws NodeNotFoundException {
		
		String NO_EXISTING_NODE = "nodes/696969696969";
		NodePoolManager npm = new NodesClient(nodePoolManagerHost);
		Node node = npm.getNode(NO_EXISTING_NODE);
		assertTrue(node == null);
	}

}