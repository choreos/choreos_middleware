package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderMocks;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Node;

public class NPMImplTest {

	@Test
	public void shouldCreateNode() throws Exception {
		
		NodeRegistry reg = NodeRegistry.getInstance();
		reg.clear();

		CloudProvider cp = CloudProviderMocks.getGoodMock();
		NodeCreator creator = new NodeCreator(cp, false);
		int N = cp.getNodes().size();
		IdlePool pool = IdlePool.getCleanInstance(N, creator);
		
		NodePoolManager npm = new NPMImpl(cp, creator, pool);
		
		Node createdNode = npm.createNode(new Node(), null);
		assertTrue(isNodeOK(createdNode));
		Node fromRegNode = reg.getNode(createdNode.getId());
		assertTrue(isNodeOK(fromRegNode));
		assertEquals(fromRegNode, createdNode);
	}
	
	@Test(expected=NodeNotFoundException.class)
	public void shouldCreateAndDeleteNode() throws Exception {
		
		NodeRegistry reg = NodeRegistry.getInstance();
		reg.clear();

		CloudProvider cp = CloudProviderMocks.getGoodMock();
		NodeCreator creator = new NodeCreator(cp, false);
		int N = cp.getNodes().size();
		IdlePool pool = IdlePool.getCleanInstance(N, creator);

		NodePoolManager npm = new NPMImpl(cp, creator, pool);
		
		Node createdNode = npm.createNode(new Node(), null);
		assertTrue(isNodeOK(createdNode));
		Node fromRegNode = reg.getNode(createdNode.getId());
		assertTrue(isNodeOK(fromRegNode));
		assertEquals(fromRegNode, createdNode);
		
		npm.destroyNode(createdNode.getId());
		fromRegNode = reg.getNode(createdNode.getId());
	}
	
	@Test
	public void shouldAccessNodeFromDifferentNPMInstances () throws Exception {
		
		NodeRegistry reg = NodeRegistry.getInstance();
		reg.clear();

		CloudProvider cp1 = CloudProviderMocks.getGoodMock();
		NodeCreator creator1 = new NodeCreator(cp1, false);
		int N = cp1.getNodes().size();
		IdlePool pool1 = IdlePool.getCleanInstance(N, creator1);
		NodePoolManager npm1 = new NPMImpl(cp1, creator1, pool1);
		
		Node createdNode = npm1.createNode(new Node(), null);
		assertTrue(isNodeOK(createdNode));

		CloudProvider cp2 = CloudProviderMocks.getGoodMock();
		NodeCreator creator2 = new NodeCreator(cp2, false);
		N = cp2.getNodes().size();
		IdlePool pool2 = IdlePool.getCleanInstance(N, creator2);
		NodePoolManager npm2 = new NPMImpl(cp2, creator2, pool2);
		
		Node fromOtherNPM = npm2.getNode(createdNode.getId());
		assertTrue(isNodeOK(fromOtherNPM));
		assertEquals(fromOtherNPM, createdNode);
	}
	
	private boolean isNodeOK(Node node) {
		
		return (node != null) && (node.getId() != null) && (!node.getId().isEmpty());
	}
}
