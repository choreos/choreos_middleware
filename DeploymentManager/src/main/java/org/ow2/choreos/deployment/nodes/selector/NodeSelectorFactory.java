package org.ow2.choreos.deployment.nodes.selector;

import org.ow2.choreos.deployment.Configuration;

public class NodeSelectorFactory {

	public enum NodeSelectorType {
		ALWAYS_CREATE, ROUND_ROBIN, DEMO, NODE_POOL
	};

	// singleton
	private static NodeSelector roundRobinSelector, demoSelector;
	
	public static NodeSelector getInstance() {

		String selector = Configuration.get("NODE_SELECTOR");
		NodeSelectorType type = null;
		try {
			type = NodeSelectorType.valueOf(selector);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(
					"Invalid NODE_SELECTOR in properties file: " + selector);
		}
		return getNodeSelectorInstance(type);
	}

	private static NodeSelector getNodeSelectorInstance(NodeSelectorType nodeSelectorType) {

		switch (nodeSelectorType) {

		case ALWAYS_CREATE:
			return new AlwaysCreateSelector();

		case ROUND_ROBIN:
			if (roundRobinSelector == null)
				roundRobinSelector = new RoundRobinSelector();
			return roundRobinSelector;
			
		case DEMO:
			if (demoSelector == null)
				demoSelector = new DemoSelector();
			return demoSelector;
			
		case NODE_POOL:
			return new NodePoolSelector();			
			
		default:
			throw new IllegalStateException("Could not choose NodeSelector");
		}
	}

}
