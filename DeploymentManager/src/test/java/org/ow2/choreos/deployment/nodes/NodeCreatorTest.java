package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderMocks;
import org.ow2.choreos.deployment.nodes.cloudprovider.NodeCreator;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public class NodeCreatorTest {

	@Test
	public void shouldCreateNodeAndRegistryIt() throws Exception {
		
		CloudProvider goodCP = CloudProviderMocks.getGoodMock();
		NodeRegistry reg = NodeRegistry.getInstance();
		
		NodeCreator nodeCreator = new NodeCreator(new Node(), new ResourceImpact(), goodCP, reg, false, false);
		Node createdNode = nodeCreator.call();
		assertNotNull(createdNode);
		Node nodeFromReg = reg.getNode(createdNode.getId());
		assertNotNull(nodeFromReg);
		assertEquals(nodeFromReg, createdNode);
	}

	@Test
	public void shouldCreateNodeAndRegistryItEvenWithOneCPFailure() throws Exception {
		
		CloudProvider intermitentCP = CloudProviderMocks.getIntermitentMock();
		NodeRegistry reg = NodeRegistry.getInstance();
		
		NodeCreator nodeCreator = new NodeCreator(new Node(), new ResourceImpact(), intermitentCP, reg, false, true);
		Node createdNode = nodeCreator.call();
		assertNotNull(createdNode);
		Node nodeFromReg = reg.getNode(createdNode.getId());
		assertNotNull(nodeFromReg);
		assertEquals(nodeFromReg, createdNode);
	}

	@Test(expected=NodeNotCreatedException.class)
	public void shouldNotCreateNode() throws Exception {
		
		CloudProvider badCP = CloudProviderMocks.getBadMock();
		NodeRegistry reg = NodeRegistry.getInstance();
		
		NodeCreator nodeCreator = new NodeCreator(new Node(), new ResourceImpact(), badCP, reg, false, true);
		nodeCreator.call();
	}

}
