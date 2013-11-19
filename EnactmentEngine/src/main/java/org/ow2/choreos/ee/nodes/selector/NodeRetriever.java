package org.ow2.choreos.ee.nodes.selector;

import java.util.List;

import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.ObjectRetriever;

class NodeRetriever implements ObjectRetriever<CloudNode> {

    private NodePoolManager npm;

    public NodeRetriever(NodePoolManager npm) {
        this.npm = npm;
    }

    @Override
    public List<CloudNode> retrieveObjects() {
        return this.npm.getNodes();
    }

}
