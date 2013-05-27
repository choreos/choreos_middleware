package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderMocks;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public class NodeCreatorTest {

	@Test
	public void shouldCreateNodeAndRegistryIt() throws Exception {
		
		CloudProvider goodCP = CloudProviderMocks.getGoodMock();
		
		NodeCreator nodeCreator = new NodeCreator(goodCP, false);
		Node createdNode = nodeCreator.create(new Node(), new ResourceImpact());
		assertTrue(this.isNodeOK(createdNode));
	}
	
	private boolean isNodeOK(Node node) {
		
		return (node != null) && (node.getId() != null) && (!node.getId().isEmpty());
	}

	@Test(expected=NodeNotCreatedException.class)
	public void shouldNotCreateNode() throws Exception {
		
		CloudProvider badCP = CloudProviderMocks.getBadMock();

		NodeCreator nodeCreator = new NodeCreator(badCP, false);
		nodeCreator.create(new Node(), new ResourceImpact());
	}

}
