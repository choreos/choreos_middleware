package org.ow2.choreos.npm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.npm.client.NodePoolManager;
import org.ow2.choreos.npm.client.NodePoolManagerClient;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;


public class NodesResourceTest extends BaseTest {

	@Test
	public void getNode() throws Exception {

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null);
		
		NodePoolManager npm = new NodePoolManagerClient(nodePoolManagerHost);
		NodeRestRepresentation nodeRest = npm.getNode(node.getId());

		assertEquals(node.getId(), nodeRest.getId());
		assertEquals(node.getHostname(), nodeRest.getHostname());
		assertEquals(node.getIp(), nodeRest.getIp());
	}

	@Test
	public void getInvalidNode() {
		
		String NO_EXISTING_NODE = "nodes/696969696969";
		NodePoolManager npm = new NodePoolManagerClient(nodePoolManagerHost);
		NodeRestRepresentation nodeRest = npm.getNode(NO_EXISTING_NODE);
		assertTrue(nodeRest == null);
	}

}