package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Concurrency;

public class NodesDestroyer {

    private Collection<CloudNode> nodesToDestroy;
    private NodeDestroyerFactory nodeDestroyerFactory;
    private NodeRegistry nodeRegistry;
    private List<Thread> trds = new ArrayList<Thread>();
    private List<NodeDestroyer> destroyers = new ArrayList<NodeDestroyer>();

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
        trds = new ArrayList<Thread>();
        destroyers = new ArrayList<NodeDestroyer>();
        for (CloudNode node : nodesToDestroy) {
            NodeDestroyer destroyer = nodeDestroyerFactory.getNewInstance(node);
            Thread trd = new Thread(destroyer);
            destroyers.add(destroyer);
            trds.add(trd);
            trd.start();
        }
    }

    private void waitDestroy() {
        Concurrency.waitThreads(trds); // FIXME
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
