package eu.choreos.storagefactory;

import eu.choreos.storagefactory.datamodel.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class NodePoolManagerHandler {

	public boolean createNode(InfrastructureNodeData node, String recipe) {
		node.setHostname("choreos-node");
		return true;
	}

	public InfrastructureNodeData getNode(String nodeId) {
		return null;
	}

	public List<InfrastructureNodeData> getNodes() {
		return null;
	}

	public void destroyNode(String id) {
	}


}
