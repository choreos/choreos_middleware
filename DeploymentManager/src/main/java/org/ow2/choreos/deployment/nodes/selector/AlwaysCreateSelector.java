package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;


public class AlwaysCreateSelector implements NodeSelector {

	private CloudProvider cloudProvider;

	public AlwaysCreateSelector(CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	@Override
	public List<Node> selectNodes(Config config) {

		int numberOfInstances = config.getNumberOfInstances();
		List<Node> nodes = new ArrayList<Node>();

		for(int i = 0; i < numberOfInstances; i++) {
			Node node = new Node();
			try {
				node = this.cloudProvider.createNode(node, config.getResourceImpact());
				nodes.add(node);
			} catch (NodeNotCreatedException e) {
				e.printStackTrace();
				return null;
			}
		}
		return nodes;
	}

}
