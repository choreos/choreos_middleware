package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;

public class AlwaysCreateSelector implements NodeSelector {

    @Override
    public List<Node> selectNodes(Config config, NodePoolManager npm) throws NodeNotSelectedException {

	int numberOfInstances = config.getNumberOfInstances();
	List<Node> nodes = new ArrayList<Node>();

	for (int i = 0; i < numberOfInstances; i++) {
	    Node node = new Node();
	    try {
		node = npm.createNode(node, config.getResourceImpact());
		nodes.add(node);
	    } catch (NodeNotCreatedException e) {
		e.printStackTrace();
		throw new NodeNotSelectedException("Nodes not selected to config " + config.getName());
	    }
	}
	return nodes;
    }

}
