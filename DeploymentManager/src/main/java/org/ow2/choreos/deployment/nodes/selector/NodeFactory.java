package org.ow2.choreos.deployment.nodes.selector;

import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.selectors.ObjectFactory;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public class NodeFactory implements ObjectFactory<Node> {

    private NodePoolManager npm;
    private ResourceImpact resourceImpact;

    public NodeFactory(NodePoolManager npm) {
	this.npm = npm;
    }

    @Override
    public Node createNewInstance() throws ObjectCreationException {
	try {
	    return this.npm.createNode(new Node(), resourceImpact);
	} catch (NodeNotCreatedException e) {
	    throw new ObjectCreationException();
	}
    }

}
