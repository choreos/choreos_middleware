package org.ow2.choreos.deployment.nodes;

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
		
		NodeCreator nodeCreator = new NodeCreator(new Node(), new ResourceImpact(), goodCP, false, false);
		Node createdNode = nodeCreator.call();
		this.isNodeOK(createdNode);
	}
	
	private boolean isNodeOK(Node node) {
		
		return (node != null) && (node.getId() != null) && (!node.getId().isEmpty());
	}

	@Test
	public void shouldCreateNodeAndRegistryItEvenWithOneCPFailure() throws Exception {
		
		CloudProvider intermitentCP = CloudProviderMocks.getIntermitentMock();
		
		NodeCreator nodeCreator = new NodeCreator(new Node(), new ResourceImpact(), intermitentCP, false, true);
		Node createdNode = nodeCreator.call();
		this.isNodeOK(createdNode);
	}

	@Test(expected=NodeNotCreatedException.class)
	public void shouldNotCreateNode() throws Exception {
		
		CloudProvider badCP = CloudProviderMocks.getBadMock();
		
		NodeCreator nodeCreator = new NodeCreator(new Node(), new ResourceImpact(), badCP, false, true);
		nodeCreator.call();
	}

}
