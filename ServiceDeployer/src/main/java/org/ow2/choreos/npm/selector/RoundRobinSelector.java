package org.ow2.choreos.npm.selector;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;


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
		System.out.println("Selector has chosen " + selected);
		return selected;
	}

}
