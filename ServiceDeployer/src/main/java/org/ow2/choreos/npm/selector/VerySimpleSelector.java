package org.ow2.choreos.npm.selector;

import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;


public class VerySimpleSelector implements NodeSelector {

	private CloudProvider cloudProvider;
	
	public VerySimpleSelector(CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;
	}
	
	public Node selectNode(Config config) {
		
		Node node = new Node();
		try {
			node = this.cloudProvider.createOrUseExistingNode(node);
		} catch (RunNodesException e) {
			e.printStackTrace();
			return null;
		}
		return node;
	}

}
