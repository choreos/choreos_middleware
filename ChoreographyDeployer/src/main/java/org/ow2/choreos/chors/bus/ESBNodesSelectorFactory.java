package org.ow2.choreos.chors.bus;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;

public class ESBNodesSelectorFactory {

	private static Logger logger = Logger.getLogger(ESBNodesSelectorFactory.class);
	
	private static enum SelectorType {SINGLE_NODE, ALWAYS_CREATE};
	
	public static ESBNodesSelector getInstance(String type) {
		
		try {
			SelectorType selectorType = SelectorType.valueOf(type);
			return getInstance(selectorType);
		} catch (IllegalArgumentException e) {
			logger.error("Invalid ESBNodesSelector type: " + type);
			throw e;
		}
	}

	private static ESBNodesSelector getInstance(SelectorType type) {
		
		String host = Configuration.get(Option.DEPLOYMENT_MANAGER_URI);
		NodePoolManager npm = new NodesClient(host);

		switch (type) {

			case SINGLE_NODE:
				return new SingleESBNodeSelector(npm);
	
			case ALWAYS_CREATE:
				return new AlwaysCreateESBNodeSelector(npm);
	
			default:
				throw new IllegalArgumentException(
						"Invalid ESBNodesSelector type: " + type);
		}
	}

}
