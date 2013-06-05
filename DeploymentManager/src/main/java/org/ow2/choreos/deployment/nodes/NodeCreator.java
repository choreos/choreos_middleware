package org.ow2.choreos.deployment.nodes;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.cm.NodeNotBootstrappedException;
import org.ow2.choreos.nodes.NPMException;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
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
    public Node create(Node nodeSpec, ResourceImpact resourceImpact) throws NPMException {

	ExecutorService executor = Executors.newSingleThreadExecutor();
	CPNodeCreation cpNodeCreator = new CPNodeCreation(cp, nodeSpec, resourceImpact);
	Future<Node> future = executor.submit(cpNodeCreator);
	Concurrency.waitExecutor(executor, VM_CREATION_TIMEOUT, TimeUnit.SECONDS, logger);

	Node node = null;
	try {
	    node = future.get();
	} catch (InterruptedException e1) {
	    throw new NodeNotCreatedException(nodeSpec.getId(), "Could not create VM");
	} catch (ExecutionException e1) {
	    throw new NodeNotCreatedException(nodeSpec.getId(), "Could not create VM");
	}

	if (node == null || node.getIp() == null || node.getIp().isEmpty()) {
	    throw new NodeNotCreatedException(node.getId(), "Could not create VM");
	}

	if (this.bootstrapNode) {
	    try {
		NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
		bootstrapper.bootstrapNode();
	    } catch (KnifeException e) {
		throw new NodeNotCreatedException(node.getId(), "Could not initialize node " + node);
	    } catch (NodeNotBootstrappedException e) {
		throw new NodeNotCreatedException(node.getId(), "Could not initialize node " + node);
	    } catch (NodeNotAccessibleException e) {
		throw new NodeNotCreatedException(node.getId(), "Could not connect to the node " + node);
	    }
	}

	return node;
    }

    private class CPNodeCreation implements Callable<Node> {

	CloudProvider cp;
	Node nodeSpec;
	ResourceImpact impact;

	CPNodeCreation(CloudProvider cp, Node nodeSpec, ResourceImpact impact) {
	    this.cp = cp;
	    this.nodeSpec = nodeSpec;
	    this.impact = impact;
	}

	@Override
	public Node call() throws Exception {
	    Node node = cp.createNode(nodeSpec, impact);
	    return node;
	}
    }

}
