package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class NodeDestroyerFactory {

    private CloudProvider cp;
    
    public NodeDestroyerFactory(CloudProvider cp) {
        this.cp = cp;
    }

    public NodeDestroyer getNewInstance(CloudNode node) {
        return new NodeDestroyer(node.getId(), cp);
    }
    
    public NodeDestroyer getNewInstance(String nodeId) {
        return new NodeDestroyer(nodeId, cp);
    }
    
}
