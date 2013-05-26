package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;

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
	private Logger logger = Logger.getLogger(LimitedRoundRobin.class);

	private int vmLimit = 1;
	private AtomicInteger counter = null;
	private NodeSelectorMapper mapper = null;

	public LimitedRoundRobin() {
		vmLimit = Integer.parseInt(Configuration.get("VM_LIMIT"));
		mapper = new NodeSelectorMapper(Configuration.get("MAPPER_POLICY"));
		counter = new AtomicInteger();
	}

	@Override
	public List<Node> selectNodes(Config config, NodePoolManager npm) throws NodeNotSelectedException {
		
		int numberOfInstances = config.getNumberOfInstances();
		List<Node> allNodes = npm.getNodes();
		
		if (allNodes.size() < vmLimit) { // creation state

			int maximumNewAllowed = vmLimit - allNodes.size();
			int newQty = numberOfInstances;
			if (newQty > maximumNewAllowed) {
				newQty = maximumNewAllowed;
			}
			List<Node> result = createNodes(newQty, config, npm);
			
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
		List<Node> newNodes = new ArrayList<Node>();
		for (int i=0; i<nodesNeeded; i++) {
			try {
				Node createdNode = npm.createNode(nodeSpec,
						config.getResourceImpact());
				newNodes.add(createdNode);
			} catch (NodeNotCreatedException e) {
				logger.error("Failed to create new node");
			}
		}
		return newNodes;
	}
	
	private List<Node> makeRoundRobin(int numberOfInstances, Config config, NodePoolManager npm) {
	
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