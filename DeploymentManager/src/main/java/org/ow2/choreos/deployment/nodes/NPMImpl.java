/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdater;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdaterFactory;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.Concurrency;

/**
 * 
 * @author leonardo, furtado
 * 
 */
public class NPMImpl implements NodePoolManager {

    private Logger logger = Logger.getLogger(NPMImpl.class);

    private CloudProvider cloudProvider;
    private NodeRegistry nodeRegistry;
    private NodeCreator nodeCreator;
    private IdlePool idlePool;

    public NPMImpl(CloudProvider provider) {

        int poolSize = 0;
        try {
            poolSize = Integer.parseInt(DeploymentManagerConfiguration.get("IDLE_POOL_SIZE"));
        } catch (NumberFormatException e) {
            ; // no problem, poolSize is zero
        }

        cloudProvider = provider;
        nodeRegistry = NodeRegistry.getInstance();
        nodeCreator = new NodeCreator(cloudProvider);
        idlePool = IdlePool.getInstance(poolSize, nodeCreator);
    }

    public NPMImpl(CloudProvider provider, NodeCreator creator, IdlePool pool) {
        cloudProvider = provider;
        nodeRegistry = NodeRegistry.getInstance();
        nodeCreator = creator;
        idlePool = pool;
    }

    @Override
    public CloudNode createNode(NodeSpec nodeSpec) throws NodeNotCreatedException {
        CloudNode node = new CloudNode();
        try {
            node = nodeCreator.createBootstrappedNode(nodeSpec);
        } catch (NodeNotCreatedException e1) {
            // if node creation has failed, let's retrieve a node from the pool
            // since waiting for a new node would take too much time!
            // TODO: maybe the failed node only took too much time to be ready;
            // in such situation, this node could go to the pool!
            try {
                logger.warn("*** Node creation failed, let's retrieve a node from the pool ***");
                node = idlePool.retriveNode();
            } catch (NodeNotCreatedException e2) {
                // OK, now we give up =/
                throw new NodeNotCreatedException(node.getId());
            }
        }
        nodeRegistry.putNode(node);
        idlePool.fillPool(); // we want the pool to be always filled
        // whenever requests are coming
        return node;
    }

    @Override
    public List<CloudNode> getNodes() {
        if (this.cloudProvider.getCloudProviderName() == FixedCloudProvider.FIXED_CLOUD_PROVIDER) {
            return this.cloudProvider.getNodes();
        } else {
            return nodeRegistry.getNodes();
        }
    }

    @Override
    public CloudNode getNode(String nodeId) throws NodeNotFoundException {
        if (this.cloudProvider.getCloudProviderName() == FixedCloudProvider.FIXED_CLOUD_PROVIDER) {
            return this.cloudProvider.getNode(nodeId);
        } else {
            return nodeRegistry.getNode(nodeId);
        }
    }

    @Override
    public void updateNode(String nodeId) throws NodeNotUpdatedException, NodeNotFoundException {
        CloudNode node = this.getNode(nodeId);
        NodeUpdater updater = NodeUpdaterFactory.getInstance(nodeId);
        updater.update(node);
    }

    @Override
    public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException {
        try {
            this.cloudProvider.destroyNode(nodeId);
            this.nodeRegistry.deleteNode(nodeId);
        } catch (NodeNotDestroyed e) {
            throw e;
        } catch (NodeNotFoundException e) {
            throw e;
        }
    }

    @Override
    public void destroyNodes() throws NodeNotDestroyed {

        List<Thread> trds = new ArrayList<Thread>();
        List<NodeDestroyer> destroyers = new ArrayList<NodeDestroyer>();

        for (CloudNode node : this.getNodes()) {
            NodeDestroyer destroyer = new NodeDestroyer(node, this.cloudProvider);
            Thread trd = new Thread(destroyer);
            destroyers.add(destroyer);
            trds.add(trd);
            trd.start();
        }

        Concurrency.waitThreads(trds);

        for (NodeDestroyer destroyer : destroyers) {
            if (destroyer.isOK()) {
                this.nodeRegistry.deleteNode(destroyer.getNode().getId());
            } else {
                throw new NodeNotDestroyed(destroyer.getNode().getId());
            }
        }
    }
}
