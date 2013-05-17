package org.ow2.choreos.deployment.nodes;

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

/**
 * Creates a new and bootstrapped node
 * 
 * @author leonardo
 *
 */
public class NodeCreator {

	private Logger logger = Logger.getLogger(NodeDestroyer.class);

	private CloudProvider cp;
	private boolean bootstrapNode = true;
	private boolean retry = false;

	public NodeCreator(CloudProvider cp, boolean retry) {
		this.cp = cp;
		this.retry = retry;
	}

	public NodeCreator(CloudProvider cp, boolean bootstrapNode, boolean retry) {
		
		this(cp, retry);
		this.bootstrapNode = bootstrapNode;
	}

	/**
	 * Tries to create a node and bootstrap it.
	 */
	public Node create(Node node, ResourceImpact resourceImpact) throws NPMException {
		
		try {
			node = cp.createNode(node, resourceImpact);
		} catch (NodeNotCreatedException e) {
			if (retry) {
				logger.warn("Could not create VM. Going to try again!");
				NodeCreator creator = new NodeCreator(cp, 
						bootstrapNode, false);
				return creator.create(node, resourceImpact);
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
					NodeCreator creator = new NodeCreator(cp, bootstrapNode, false);
					return creator.create(node, resourceImpact);
				} else {
					throw new NodeNotCreatedException(node.getId(),
							"Could not connect to the node " + node);
				}
			}
		}

		return node;
	}
		
}
