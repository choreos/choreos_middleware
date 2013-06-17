/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.cm.NodeNotBootstrappedException;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.Concurrency;

/**
 * Creates a new node and bootstrapped it
 * 
 * @author leonardo
 * 
 */
public class NodeCreator {

    private static final int VM_CREATION_TIMEOUT = 200; // in seconds

    private Logger logger = Logger.getLogger(NodeDestroyer.class);

    private CloudProvider cp;
    private boolean bootstrapNode = true;

    public NodeCreator(CloudProvider cp) {
	this.cp = cp;
    }

    public NodeCreator(CloudProvider cp, boolean bootstrapNode) {
	this(cp);
	this.bootstrapNode = bootstrapNode;
    }

    /**
     * Tries to create a node and bootstrap it.
     */
    public CloudNode create(NodeSpec nodeSpec) throws NodeNotCreatedException {

	ExecutorService executor = Executors.newSingleThreadExecutor();
	CloudNodeCreation cloudNodeCreator = new CloudNodeCreation(cp, nodeSpec);
	Future<CloudNode> future = executor.submit(cloudNodeCreator);
	Concurrency.waitExecutor(executor, VM_CREATION_TIMEOUT, TimeUnit.SECONDS, logger);

	CloudNode node = null;
	try {
	    node = future.get();
	} catch (InterruptedException e1) {
	    throw new NodeNotCreatedException();
	} catch (ExecutionException e1) {
	    throw new NodeNotCreatedException();
	}

	if (node == null || node.getIp() == null || node.getIp().isEmpty()) {
	    throw new NodeNotCreatedException(node.getId());
	}

	if (this.bootstrapNode) {
	    try {
		NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
		bootstrapper.bootstrapNode();
	    } catch (NodeNotBootstrappedException e) {
		throw new NodeNotCreatedException(node.getId());
	    } catch (NodeNotAccessibleException e) {
		throw new NodeNotCreatedException(node.getId());
	    }
	}

	return node;
    }

    private class CloudNodeCreation implements Callable<CloudNode> {

	CloudProvider cp;
	NodeSpec nodeSpec;

	CloudNodeCreation(CloudProvider cp, NodeSpec nodeSpec) {
	    this.cp = cp;
	    this.nodeSpec = nodeSpec;
	}

	@Override
	public CloudNode call() throws Exception {
	    CloudNode node = cp.createNode(nodeSpec);
	    return node;
	}
    }

}
