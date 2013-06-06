package org.ow2.choreos.deployment.nodes.selector;

import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.selectors.ObjectFactory;

class NodeFactory implements ObjectFactory<Node> {

    private static final int TIMEOUT_SECONDS = 5*60; 
    private NodePoolManager npm;

    public NodeFactory(NodePoolManager npm) {
	this.npm = npm;
    }

    @Override
    public Node createNewInstance() throws ObjectCreationException {
	try {
	    return this.npm.createNode(new NodeSpec());
	} catch (NodeNotCreatedException e) {
	    throw new ObjectCreationException();
	}
    }

    @Override
    public int getTimeouInSeconds() {
	return TIMEOUT_SECONDS;
    }

}
