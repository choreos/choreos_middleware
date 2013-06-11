package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpgradedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.Concurrency;

public class NodesUpdater {

    private static final int TIMEOUT = 30; // chef-client may take a long time

    private List<DeployableService> services;
    private String chorId;

    private Logger logger = Logger.getLogger(NodesUpdater.class);

    public NodesUpdater(List<DeployableService> services, String chorId) {
	this.services = services;
	this.chorId = chorId;
    }

    public List<DeployableService> updateNodes() throws EnactmentException {
	logger.info("Going to run chef-client on nodes of choreography " + chorId);
	final int N = services.size();
	ExecutorService executor = Executors.newFixedThreadPool(N);
	List<DeployableService> deployedServices = new ArrayList<DeployableService>();
	for (DeployableService deployable : services) {
	    deployedServices.add(deployable); // TODO do it only if update was OK
	    String owner = deployable.getSpec().getOwner();
	    List<ServiceInstance> instances = deployable.getServiceInstances();
	    if (instances != null) {
		for (ServiceInstance instance : instances) {
		    String nodeId = instance.getNode().getId();
		    NodeUpdater upgrader = new NodeUpdater(nodeId, owner);
		    executor.submit(upgrader);
		}
	    } else {
		logger.warn("No services intances to choreography " + chorId + "!");
	    }
	}
	Concurrency.waitExecutor(executor, TIMEOUT);
	return deployedServices;
    }

    private class NodeUpdater implements Runnable {

	String nodeId, owner;

	public NodeUpdater(String nodeId, String owner) {
	    this.nodeId = nodeId;
	    this.owner = owner;
	}

	@Override
	public void run() {
	    RESTClientsRetriever retriever = new RESTClientsRetriever();
	    NodePoolManager npm = retriever.getNodesClient(owner);
	    try {
		npm.updateNode(nodeId);
	    } catch (NodeNotUpgradedException e) {
		logger.error("Bad response from /nodes/" + nodeId + "/upgrade; maybe some service is not deployed");
	    } catch (NodeNotFoundException e) {
		logger.error("Bad response from /nodes/" + nodeId + "/upgrade; maybe some service is not deployed");
	    } catch (org.apache.cxf.interceptor.Fault e) {
		throw e;
	    }
	}
    }

}
