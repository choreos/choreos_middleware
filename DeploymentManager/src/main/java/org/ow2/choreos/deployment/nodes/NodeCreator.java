package org.ow2.choreos.deployment.nodes;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.cm.NodeNotBootstrappedException;
import org.ow2.choreos.nodes.NPMException;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public class NodeCreator implements Callable<Node> {

	private Logger logger = Logger.getLogger(NodeDestroyer.class);

	private Node node;
	private ResourceImpact resourceImpact;
	private CloudProvider cloudProvider;
	private boolean bootstrapNode = true;
	private boolean retry = false;

	/**
	 * 
	 * @param node work as node specification
	 * @param resourceImpact
	 * @param cp
	 * @param retry
	 */
	public NodeCreator(Node node, ResourceImpact resourceImpact, CloudProvider cp, boolean retry) {
		this.node = node;
		this.resourceImpact = resourceImpact;
		this.cloudProvider = cp;
		this.retry = retry;
	}

	/**
	 * 
	 * @param node work as node specification
	 * @param resourceImpact
	 * @param cp
	 * @param bootstrapNode
	 * @param retry
	 */
	public NodeCreator(Node node, ResourceImpact resourceImpact, CloudProvider cp, 
			boolean bootstrapNode, boolean retry) {
		
		this(node, resourceImpact, cp, retry);
		this.bootstrapNode = bootstrapNode;
	}

	/**
	 * Tries to create a node and bootstrap it.
	 */
	@Override
	public Node call() throws NPMException {
		
		try {
			node = cloudProvider.createNode(node, resourceImpact);
		} catch (NodeNotCreatedException e) {
			if (retry) {
				logger.warn("Could not create VM. Going to try again!");
				NodeCreator creator = new NodeCreator(node, resourceImpact, cloudProvider, 
						bootstrapNode, false);
				return creator.call();
			} else {
				throw new NodeNotCreatedException(node.getId(),
						"Could not create VM");
			}
		}

		if (this.bootstrapNode) {
			try {
				NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
				bootstrapper.bootstrapNode();
			} catch (KnifeException e) {
				throw new NodeNotCreatedException(node.getId(),
						"Could not initialize node " + node);
			} catch (NodeNotBootstrappedException e) {
				throw new NodeNotCreatedException(node.getId(),
						"Could not initialize node " + node);
			} catch (NodeNotAccessibleException e) {
				if (retry) {
					logger.warn("Could not connect to the node " + node
							+ ". We will forget this node and try a new one.");
					NodeCreator creator = new NodeCreator(node, resourceImpact, cloudProvider, 
							bootstrapNode, false);
					return creator.call();
				} else {
					throw new NodeNotCreatedException(node.getId(),
							"Could not connect to the node " + node);
				}
			}
		}

		return node;
	}
		
}
