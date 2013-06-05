package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.ObjectRetriever;

public class NodeRetriever implements ObjectRetriever<Node> {

    private NodePoolManager npm;

    public NodeRetriever(NodePoolManager npm) {
	this.npm = npm;
    }

    @Override
    public List<Node> retrieveObjects() {
	return this.npm.getNodes();
    }

}
