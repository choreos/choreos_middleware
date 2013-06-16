package org.ow2.choreos.deployment.nodes.selector;

import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.selectors.ObjectFactory;

class NodeFactory implements ObjectFactory<Node, DeploymentRequest> {

    private static final int TIMEOUT_SECONDS = 5 * 60;
    private NodePoolManager npm;

    public NodeFactory(NodePoolManager npm) {
	this.npm = npm;
    }

    @Override
    public Node createNewInstance(DeploymentRequest requirements) throws ObjectCreationException {
	try {
	    NodeSpec nodeSpec = new NodeSpec();
	    nodeSpec.setResourceImpact(requirements.getResourceImpact());
	    return this.npm.createNode(nodeSpec);
	} catch (NodeNotCreatedException e) {
	    throw new ObjectCreationException();
	}
    }

    @Override
    public int getTimeouInSeconds() {
	return TIMEOUT_SECONDS;
    }

}
