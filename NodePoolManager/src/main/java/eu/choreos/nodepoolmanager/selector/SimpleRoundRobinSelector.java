package eu.choreos.nodepoolmanager.selector;

import java.util.List;

import org.jclouds.compute.RunNodesException;

import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;

public class SimpleRoundRobinSelector implements NodeSelector {

	private List<Node> availableNodes;
	private CloudProvider cloudProvider;
	private int lastUsedNode = -1;

	public List<Node> getAvailableNodes() {
		return availableNodes;
	}

	public SimpleRoundRobinSelector(CloudProvider provider,
			int minimalInstantiatedNodeAmmount) {

		availableNodes = provider.getNodes();
		cloudProvider = provider;

		createInstantiatedNodePool(minimalInstantiatedNodeAmmount, provider);

	}

	public void createInstantiatedNodePool(int minimalInstantiatedNodeAmmount,
			CloudProvider provider) {

		for (int i = availableNodes.size(); i < minimalInstantiatedNodeAmmount; i++) {
			Node createdNode = createNewNode(provider);
			availableNodes.add(createdNode);
		}
	}

	private Node createNewNode(CloudProvider provider) {
		Node node = new Node();
		Node responseNode = null;
		try {
			responseNode = provider.createNode(node);
		} catch (RunNodesException e) {
			System.out.println("Could not create new node at "
					+ provider.getproviderName());
			e.printStackTrace();
		}

		return responseNode;
	}

	public void changeNodePoolSize(int newSize) {
		createInstantiatedNodePool(newSize, cloudProvider);

	}

	public Node selectNode(Config config) {

		Node node = availableNodes.get(getNextIndex());

		return node;
	}

	public int getNextIndex() {
		lastUsedNode++;

		if (lastUsedNode == availableNodes.size()){
			lastUsedNode = 0;
		}
		
		return lastUsedNode;
	}

}
