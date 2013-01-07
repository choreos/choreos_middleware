package org.ow2.choreos.deployment.nodes.selector;

import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;


public class AlwaysCreateSelector implements NodeSelector {

	private CloudProvider cloudProvider;
	
	public AlwaysCreateSelector(CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;
	}
	
	public Node selectNode(Config config) {
		
		Node node = new Node();
		try {
			node = this.cloudProvider.createNode(node);
		} catch (RunNodesException e) {
			e.printStackTrace();
			return null;
		}
		return node;
	}

}
