package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public class NodesDestroyer {

    private Collection<CloudNode> nodesToDestroy;
    private NodeDestroyerFactory nodeDestroyerFactory;

    private List<DestroyTask> tasks = new ArrayList<DestroyTask>();
    private ExecutorService executor;
    
    private final int timeout;
    private final int trials;

    public NodesDestroyer(Collection<CloudNode> nodesToDestroy, NodeDestroyerFactory nodeDestroyerFactory) {
        this.nodesToDestroy = nodesToDestroy;
        this.nodeDestroyerFactory= nodeDestroyerFactory;
        this.timeout = TimeoutsAndTrials.get("NODE_DELETION_TIMEOUT");
        this.trials = TimeoutsAndTrials.get("NODE_DELETION_TRIALS");        
    }

    public void destroyNodes() throws NodeNotDestroyed {
        destroy();
        waitDestroy();
        checkDestroyed();
    }

    private void destroy() {
        if (nodesToDestroy == null || nodesToDestroy.isEmpty())
            return;
        executor = Executors.newFixedThreadPool(nodesToDestroy.size());
        tasks = new ArrayList<DestroyTask>();
        for (CloudNode node : nodesToDestroy) {
            DestroyTask task = new DestroyTask(node);
            tasks.add(task);
            executor.submit(task);
        }
    }

    private void waitDestroy() {
        String erMsg = "Could not wait for nodes destroyment";
        int totalTimeout = timeout*trials;
        totalTimeout += totalTimeout*0.2;
        Concurrency.waitExecutor(executor, totalTimeout, erMsg);
    }

    private void checkDestroyed() throws NodeNotDestroyed {
        for (DestroyTask task : tasks) {
            if (!task.ok) {
                throw new NodeNotDestroyed(task.node.getId());
            }
        }
    }
    
    private class DestroyTask implements Runnable {
        
        private CloudNode node;
        private boolean ok;

        public DestroyTask(CloudNode node) {
            this.node = node;
        }

        @Override
        public void run() {
            NodeDestroyer destroyer = nodeDestroyerFactory.getNewInstance(node);
            try {
                destroyer.destroyNode();
                ok = true;
            } catch (NodeNotDestroyed e) {
                ok = false;
            } catch (NodeNotFoundException e) {
                ok = false;
            }
        }
    }

}
