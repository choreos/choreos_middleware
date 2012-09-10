package org.ow2.choreos.npm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.npm.client.NPMClient;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.npm.datamodel.Node;


public class NodesResourceTest extends BaseTest {

	@Test
	public void getNode() throws Exception {

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null);
		
		NodePoolManager npm = new NPMClient(nodePoolManagerHost);
		Node returnedNode = npm.getNode(node.getId());

		assertEquals(node.getId(), returnedNode.getId());
		assertEquals(node.getHostname(), returnedNode.getHostname());
		assertEquals(node.getIp(), returnedNode.getIp());
	}

	@Test
	public void getInvalidNode() throws NodeNotFoundException {
		
		String NO_EXISTING_NODE = "nodes/696969696969";
		NodePoolManager npm = new NPMClient(nodePoolManagerHost);
		Node node = npm.getNode(NO_EXISTING_NODE);
		assertTrue(node == null);
	}

}