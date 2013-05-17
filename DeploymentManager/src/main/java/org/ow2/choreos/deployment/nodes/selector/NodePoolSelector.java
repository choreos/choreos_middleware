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

public class NodePoolSelector implements NodeSelector {
	private Logger logger = Logger.getLogger(NodePoolSelector.class);

	private int vm_limit = 1;
	private AtomicInteger counter = null;
	private NodeSelectorMapper mapper = null;

	public NodePoolSelector() {
		vm_limit = Integer.parseInt(Configuration.get("VM_LIMIT"));
		mapper = new NodeSelectorMapper(Configuration.get("MAPPER_POLICY"));
		counter = new AtomicInteger();
	}

	public List<Node> selectNodes(Config config, NodePoolManager npm) {
		
		int node_counter = 0;
		int numberOfInstances = config.getNumberOfInstances();
		List<Node> allNodes = npm.getNodes();
		List<Node> compatibleNodes = mapper.filterByResourceImpact(
				config.getResourceImpact(), allNodes);
		node_counter = compatibleNodes.size();
		if (compatibleNodes.size() < numberOfInstances) {
			while (node_counter < vm_limit) {
				Node node = new Node();
				try {
					node = npm.createNode(node,
							config.getResourceImpact());
				} catch (NodeNotCreatedException e) {
					logger.error("Failed to create new node");
					e.printStackTrace();
				}
				allNodes.add(node);
				node_counter++;
			}
		}

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