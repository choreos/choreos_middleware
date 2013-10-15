/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.utils.TimeoutsAndTrials;

/**
 * Invokes the cloud provider to destroy the node and, if successful, removes
 * the node from the nodes registry.
 * 
 * @author leonardo
 */
public class NodeDestroyer {

    private static Logger logger = Logger.getLogger(NodeDestroyer.class);

    private final String nodeId;
    private final CloudProvider cp;
    private final NodeRegistry nodeRegistry;
    private final int timeout;
    private final int trials;

    public NodeDestroyer(String nodeId, CloudProvider cp) {
        this.nodeId = nodeId;
        this.cp = cp;
        this.timeout = TimeoutsAndTrials.get("NODE_DELETION_TIMEOUT");
        this.trials = TimeoutsAndTrials.get("NODE_DELETION_TRIALS");
        this.nodeRegistry = NodeRegistry.getInstance();
    }

    public void destroyNode() throws NodeNotDestroyed, NodeNotFoundException {
        if (nodeRegistry.getNode(nodeId) == null)
            throw new NodeNotFoundException(nodeId);
        NodeDeletionTask task = new NodeDeletionTask();
        Invoker<Void> invoker = new InvokerBuilder<Void>(task, timeout).trials(trials).build();
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            logger.error("Node " + nodeId + " not destroyed");
            throw new NodeNotDestroyed(nodeId);
        }
    }

    private class NodeDeletionTask implements Callable<Void> {
        @Override
        public Void call() throws NodeNotDestroyed, NodeNotFoundException {
            cp.destroyNode(nodeId);
            nodeRegistry.deleteNode(nodeId);
            logger.info("Node " + nodeId + " destroyed");
            return null;
        }
    }
}
