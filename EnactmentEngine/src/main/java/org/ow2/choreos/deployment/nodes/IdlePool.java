/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.CloudConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.Concurrency;

/**
 * Maintains a pool of VMs.
 * 
 * The VMs in the pool are not knew by the NPM. The idea is to provide a fast VM
 * creation: when NPM requests a VM to the pool, the pool returns an already
 * created VM that was in the pool, but NPM will see it just like a new VM
 * created quickly.
 * 
 * @author leonardo
 * 
 */
public class IdlePool {

    private static final int FILLING_POOL_TIMEOUT_MINUTES = 10;

    private static IdlePool INSTANCE;

    private static Logger logger = Logger.getLogger(IdlePool.class);

    private int poolSize;
    private int threshold;
    private Set<CloudNode> idleNodes = new HashSet<CloudNode>();
    private ExecutorService fillerExecutor = Executors.newSingleThreadExecutor();
    private CloudConfiguration cloudConfiguration;

    IdlePool(CloudConfiguration cloudConfiguration, int poolSize, int threshold) {
	this.poolSize = poolSize;
	this.threshold = threshold;
	this.cloudConfiguration = cloudConfiguration;
    }

    public int getSize() {
	return poolSize;
    }

    public int getThreshold() {
	return threshold;
    }

    /**
     * 
     * @return an unmodifiable list with the ids of the nodes in the idle pool
     */
    Set<CloudNode> getIdleNodes() {
	return Collections.unmodifiableSet(idleNodes);
    }

    /**
     * Retrieves a node from the idle pool.
     * 
     * The node is removed from the pool. The method is synchronized, so
     * multiple invocations will not get the same node. If the pool is empty,
     * the client waits for the creation of a VM
     * 
     * @throws NodeNotCreatedException
     * @return
     */
    public CloudNode retriveNode() throws NodeNotCreatedException {

	if (idleNodes.isEmpty()) {
	    VMCreator vmCreator = new VMCreator();
	    vmCreator.run();
	    if (!vmCreator.ok) {
		throw new NodeNotCreatedException("");
	    }
	}

	synchronized (this) {
	    CloudNode node = idleNodes.iterator().next();
	    idleNodes.remove(node);
	    adaptPoolSize();
	    return node;
	}
    }

    private void adaptPoolSize() {
	if (idleNodes.size() <= threshold) {
	    poolSize++;
	    logger.info("Idle pool size has increased to " + poolSize);
	}
    }

    /**
     * Creates extra VMs asynchronously
     * 
     * @param howManyVMs
     * @param nodeCreator
     */
    public void createExtraVMs(int howManyVMs) {
	for (int i = 0; i < howManyVMs; i++) {
	    VMCreator vmCreator = new VMCreator();
	    Thread thrd = new Thread(vmCreator);
	    thrd.start();
	}
    }

    public boolean isFull() {
	return idleNodes.size() >= poolSize;
    }

    /**
     * Give a order to fill the pool. The execution is asynchronous, i.e., the
     * client will not wait for the pool be filled.
     */
    public void fillPool() {
	PoolFiller filler = new PoolFiller();
	this.fillerExecutor.execute(filler);
    }

    public void emptyPool() throws NodeNotDestroyed {
	NodesDestroyer destroyer = new NodesDestroyer(cloudConfiguration, idleNodes);
	destroyer.destroyNodes();
    }

    private class VMCreator implements Runnable {

	boolean ok;

	@Override
	public void run() {
	    try {
		NodeCreatorFactory factory = new NodeCreatorFactory();
		NodeCreator nodeCreator = factory.getNewNodeCreator(cloudConfiguration);
		CloudNode node = nodeCreator.createBootstrappedNode(new NodeSpec());
		ok = true;
		synchronized (IdlePool.this) {
		    idleNodes.add(node);
		}
	    } catch (NodeNotCreatedException e) {
		logger.error("Could not create a VM by the pool");
		ok = false;
	    }
	}
    }

    private class PoolFiller implements Runnable {

	@Override
	public void run() {
	    int extra = poolSize - idleNodes.size();
	    if (extra > 0) {
		ExecutorService executor = Executors.newFixedThreadPool(extra);
		for (int i = 0; i < extra; i++) {
		    VMCreator vmCreator = new VMCreator();
		    executor.execute(vmCreator);
		}
		Concurrency.waitExecutor(executor, FILLING_POOL_TIMEOUT_MINUTES, "Could not properly fill the pool.");
	    }
	}
    }
}
