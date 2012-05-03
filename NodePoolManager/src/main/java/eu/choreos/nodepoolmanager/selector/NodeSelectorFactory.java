package eu.choreos.nodepoolmanager.selector;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;

public class NodeSelectorFactory {

	public enum NodeSelectorType {
		ALWAYS_CREATE, ROUND_ROBIN, DEMO
	};

	// singleton
	private static NodeSelector roundRobinSelector;
	
	public static NodeSelector getInstance(
			CloudProvider cloudProvider) {

		String selector = Configuration.get("NODE_SELECTOR");
		NodeSelectorType type = null;
		try {
			type = NodeSelectorType.valueOf(selector);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(
					"Invalid NODE_SELECTOR in properties file");
		}
		return getNodeSelectorInstance(cloudProvider, type);
	}

	private static NodeSelector getNodeSelectorInstance(
			CloudProvider cloudProvider, NodeSelectorType nodeSelectorType) {

		switch (nodeSelectorType) {

		case ALWAYS_CREATE:
			return new VerySimpleSelector(cloudProvider);

		case ROUND_ROBIN:
			if (roundRobinSelector == null)
				roundRobinSelector = new RoundRobinSelector(cloudProvider);
			return roundRobinSelector;
			
		case DEMO:
			return new DemoSelector(cloudProvider);
			
		default:
			throw new IllegalStateException("Could not choose NodeSelector");
		}
	}

}
