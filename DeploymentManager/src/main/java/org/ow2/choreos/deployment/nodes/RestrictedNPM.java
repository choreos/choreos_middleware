package org.ow2.choreos.deployment.nodes;

import java.util.List;

import org.ow2.choreos.nodes.ConfigNotAppliedException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpgradedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

/**
 * Allows only the createNode, getNodes, and getNode operations.
 * 
 * @author leonardo
 *
 */
public class RestrictedNPM implements NodePoolManager {

	public NodePoolManager npm;
	
	public RestrictedNPM(NodePoolManager npm) {
		this.npm = npm;
	}
	
	@Override
	public Node createNode(Node node, ResourceImpact resourceImpact)
			throws NodeNotCreatedException {

		return this.npm.createNode(node, resourceImpact);
	}

	@Override
	public List<Node> getNodes() {

		return this.npm.getNodes();
	}

	@Override
	public Node getNode(String nodeId) throws NodeNotFoundException {

		return this.npm.getNode(nodeId);
	}

	@Override
	public void destroyNode(String nodeId) throws NodeNotDestroyed,
			NodeNotFoundException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void destroyNodes() throws NodeNotDestroyed {

		throw new UnsupportedOperationException();
	}

	@Override
	public void upgradeNode(String nodeId) throws NodeNotUpgradedException,
			NodeNotFoundException {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<Node> applyConfig(Config config)
			throws ConfigNotAppliedException {
		
		throw new UnsupportedOperationException();
	}
	
}
