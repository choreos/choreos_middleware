package org.ow2.choreos.deployment.nodes;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;

public class NodeDestroyer implements Runnable {
	
	private Logger logger = Logger.getLogger(NodeDestroyer.class);

	private Node node;
	private CloudProvider cp;
	private boolean ok;

	public NodeDestroyer(Node node, CloudProvider cp) {
		this.node = node;
		this.cp = cp;
	}
	
	public boolean isOK() {
		return this.ok;
	}
	
	public Node getNode() {
		return this.node;
	}

	/**
	 * Invokes the cloud provider to destroy the node and, 
	 * if successful, removes the node from the nodes registry.
	 */
	@Override
	public void run() {
		try {
			cp.destroyNode(node.getId());
		} catch (NodeNotDestroyed e) {
			ok = false;
			logger.error("Node not destroyed", e);
		} catch (NodeNotFoundException e) {
			ok = false;
			logger.error("Impossible!", e);
		}
		logger.info("Node " + node.getId() + " destroyed");
		ok = true;
	}

}

