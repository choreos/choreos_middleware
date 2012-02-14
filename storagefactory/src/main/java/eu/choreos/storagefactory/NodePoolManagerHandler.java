package eu.choreos.storagefactory;

import java.util.List;

public class NodePoolManagerHandler {

	public String createNode(String recipe) {
		return "choreos-node";
	}

	public String getNode(String nodeId) {
		return "choreos-node";
	}

	public List<String> getNodes() {
		return null;
	}

	public void destroyNode(String id) {
	}

}
