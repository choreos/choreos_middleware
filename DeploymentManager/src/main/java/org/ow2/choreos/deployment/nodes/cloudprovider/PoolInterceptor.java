package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.List;

import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

/**
 * Intercepts create node requests and manages the node pool size.
 * 
 * All the intercepted requests are delegated to the 
 * cloud provider provided through the constructor.
 * 
 * @author leonardo
 *
 */
public class PoolInterceptor implements CloudProvider {

	// this class follows the decorator design pattern =)
	
	private CloudProvider cp;
	
	public PoolInterceptor(CloudProvider cp) {
		this.cp = cp;
	}

	@Override
	public String getProviderName() {
		return this.cp.getProviderName();
	}

	@Override
	public Node createNode(Node node, ResourceImpact resourceImpact)
			throws RunNodesException {
		return this.cp.createNode(node, resourceImpact);
	}

	@Override
	public Node getNode(String nodeId) throws NodeNotFoundException {
		return this.cp.getNode(nodeId);
	}

	@Override
	public List<Node> getNodes() {
		return this.cp.getNodes();
	}

	@Override
	public void destroyNode(String id) throws NodeNotDestroyed,
			NodeNotFoundException {
		this.cp.destroyNode(id);
	}

	@Override
	public Node createOrUseExistingNode(Node node, ResourceImpact resourceImpact)
			throws RunNodesException {
		return this.cp.createOrUseExistingNode(node, resourceImpact);
	}

}
