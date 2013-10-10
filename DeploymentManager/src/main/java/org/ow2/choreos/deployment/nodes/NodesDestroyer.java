package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Concurrency;

public class NodesDestroyer {

    private static final int TIMEOUT_MINUTES = 3;
    
    private Collection<CloudNode> nodesToDestroy;
    private NodeDestroyerFactory nodeDestroyerFactory;
    private NodeRegistry nodeRegistry;

    private List<NodeDestroyer> destroyers = new ArrayList<NodeDestroyer>();
    private ExecutorService executor;
    
    public NodesDestroyer(Collection<CloudNode> nodesToDestroy, NodeDestroyerFactory nodeDestroyerFactory) {
        this.nodesToDestroy = nodesToDestroy;
        this.nodeDestroyerFactory= nodeDestroyerFactory; 
        nodeRegistry = NodeRegistry.getInstance();
    }

    public void destroyNodes() throws NodeNotDestroyed {
        destroy();
        waitDestroy();
        checkDestroyed();
    }

    private void destroy() {
        executor = Executors.newFixedThreadPool(nodesToDestroy.size());
        destroyers = new ArrayList<NodeDestroyer>();
        for (CloudNode node : nodesToDestroy) {
            NodeDestroyer destroyer = nodeDestroyerFactory.getNewInstance(node);
            destroyers.add(destroyer);
            executor.submit(destroyer);
        }
    }

    private void waitDestroy() {
        String erMsg = "Could not wait for nodes destroyment";
        Concurrency.waitExecutor(executor, TIMEOUT_MINUTES, erMsg);
    }

    private void checkDestroyed() throws NodeNotDestroyed {
        for (NodeDestroyer destroyer : destroyers) {
            if (destroyer.isOK()) {
                this.nodeRegistry.deleteNode(destroyer.getNode().getId());
            } else {
                throw new NodeNotDestroyed(destroyer.getNode().getId());
            }
        }
    }

}
