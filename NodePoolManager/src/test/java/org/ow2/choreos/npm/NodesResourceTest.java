package org.ow2.choreos.npm;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Test;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;


public class NodesResourceTest extends BaseTest {

	@After
	public void resetPath() {
		client.back(true);
	}

	@Test
	public void getNode() throws Exception {

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null);
		
		System.out.println(node);
		client.path("nodes/" + node.getId());
		NodeRestRepresentation nodeRest = client.get(NodeRestRepresentation.class);

		System.out.println(node);
		System.out.println(nodeRest);
		
		assertEquals(node.getId(), nodeRest.getId());
		assertEquals(node.getHostname(), nodeRest.getHostname());
		assertEquals(node.getIp(), nodeRest.getIp());
	}

	@Test
	public void getInvalidNode() {
		
		String NO_EXISTING_NODE = "nodes/696969696969";
		client.path(NO_EXISTING_NODE);
		Response response = client.get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}