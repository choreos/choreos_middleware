package eu.choreos.nodepoolmanager.selector;

import org.jclouds.compute.RunNodesException;

import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;

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
