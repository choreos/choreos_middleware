package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.nodes.datamodel.CloudNode;

public class NodeDestroyerFactory {

    public NodeDestroyer getNewInstance(CloudNode node) {
        return new NodeDestroyer(node.getId());
    }
    
    public NodeDestroyer getNewInstance(String nodeId) {
        return new NodeDestroyer(nodeId);
    }
    
}
