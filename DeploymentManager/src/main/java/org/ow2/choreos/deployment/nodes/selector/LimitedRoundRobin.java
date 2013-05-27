package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.utils.Concurrency;

/**
 * An hybrid between Always Create and Round Robin, with a limit on the number of VMs.
 * The initial behavior is Always Create.
 * When the limit of instances is reached, it acts as Round Robin.
 * The VM limit is defined by the property VM_LIMIT in the config file. 
 * 
 * @author alessio, leonardo
 *
 */
public class LimitedRoundRobin implements NodeSelector {
	
	private static final int TIMEOUT_TO_VM_CREATION = 5;

	private Logger logger = Logger.getLogger(LimitedRoundRobin.class);

	private int vmLimit = 1;
	private AtomicInteger counter = new AtomicInteger();
	private AtomicInteger nodesBeenCreated = new AtomicInteger();
	private NodeSelectorMapper mapper = null;

	public LimitedRoundRobin() {
		vmLimit = Integer.parseInt(Configuration.get("VM_LIMIT"));
		mapper = new NodeSelectorMapper(Configuration.get("MAPPER_POLICY"));
	}

	@Override
	public List<Node> selectNodes(Config config, NodePoolManager npm) throws NodeNotSelectedException {
		
		int numberOfInstances = config.getNumberOfInstances();
		List<Node> allNodes = npm.getNodes();
		
		boolean creationState = false;
		int newQty = 0;
		synchronized (this) {
			if (allNodes.size() + nodesBeenCreated.get() < vmLimit) {
				creationState = true;
				int maximumNewAllowed = vmLimit - allNodes.size();
				newQty = numberOfInstances;
				if (newQty > maximumNewAllowed) {
					newQty = maximumNewAllowed;
				}
				nodesBeenCreated.addAndGet(newQty);				
			}
		}
		
		if (creationState) { 
			
			List<Node> result = createNodes(newQty, config, npm);
			nodesBeenCreated.set(nodesBeenCreated.get() - newQty);
			
			if (result.size() < numberOfInstances) {
				int diff = numberOfInstances = result.size();
				List<Node> moreNodes = makeRoundRobin(diff, config, npm);
				result.addAll(moreNodes);
			}
			return result;
		}
		else { // round robin state
			
			return makeRoundRobin(numberOfInstances, config, npm);
		}
	}
	
	private List<Node> createNodes(int nodesNeeded, Config config, NodePoolManager npm) {
	
		Node nodeSpec = new Node();
		List<Future<Node>> futures = new ArrayList<Future<Node>>();
		ExecutorService executor = Executors.newFixedThreadPool(nodesNeeded);
		for (int i=0; i<nodesNeeded; i++) {
			RunnableCreator runnable = new RunnableCreator(nodeSpec, config, npm);
			Future<Node> f = executor.submit(runnable);
			futures.add(f);
		}
		
		Concurrency.waitExecutor(executor, TIMEOUT_TO_VM_CREATION);
		
		List<Node> newNodes = new ArrayList<Node>();
		for (Future<Node> f: futures) {
			try {
				Node newNode = Concurrency.checkFuture(f);
				newNodes.add(newNode);
			} catch (ExecutionException e) {
				logger.error("Failed to create new node");
			}
		}
		return newNodes;
	}
	
	private class RunnableCreator implements Callable<Node> {

		Node nodeSpec;
		Config config;
		NodePoolManager npm;
		
		RunnableCreator(Node nodeSpec, Config config, NodePoolManager npm) {
			this.nodeSpec = nodeSpec;
			this.config = config;
			this.npm = npm;
		}
		
		@Override
		public Node call() throws Exception {
			logger.debug("Creating node");
			Node createdNode = npm.createNode(nodeSpec,
					config.getResourceImpact());
			return createdNode;
		}
		
	}
	
	private List<Node> makeRoundRobin(int numberOfInstances, Config config, NodePoolManager npm) {
	
		// if nodes are still been created, we have to wait
		logger.debug("Waiting nodes to be ready");
		int c = 0;
		while (nodesBeenCreated.get() > 0) {
			try {
				if (c<10)
					Thread.sleep(100);
				else
					Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Exception while waiting nodes be ready");
			}
			c++;
		}
		logger.debug("Waiting no more nodes to be ready");
		
		List<Node> allNodes = npm.getNodes();
		List<Node> resultList = new ArrayList<Node>();
		int i = 0, j = 0;
		while (i < numberOfInstances && j < allNodes.size()) {
			int idx = counter.getAndIncrement();
			idx = idx % allNodes.size();
			Node selected = allNodes.get(idx);
			if (mapper.isAcceptable(config.getResourceImpact(), selected)) {
				logger.debug("Selector has chosen " + selected);
				resultList.add(selected);
				i++;
			}
			j++;
		}
		return resultList;
	}
}