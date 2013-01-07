package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;


/**
 * Select nodes according the round robin rule
 * among the existent nodes
 * 
 * The round robin order is ensured only if the client
 * first creates the nodes and just after uses them
 * 
 * @author leonardo
 *
 */
public class RoundRobinSelector implements NodeSelector {

	private Logger logger = Logger.getLogger(RoundRobinSelector.class);
	
	private AtomicInteger counter = new AtomicInteger();
	private CloudProvider cloudProvider;
	
	public RoundRobinSelector(CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;
	}
	
	public Node selectNode(Config config) {
		
		List<Node> nodes = cloudProvider.getNodes();
		int idx = counter.getAndIncrement();
		idx = idx % nodes.size();
		Node selected = nodes.get(idx);
		logger.debug("Selector has chosen " + selected);
		return selected;
	}

}
