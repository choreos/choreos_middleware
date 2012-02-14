package eu.choreos.storagefactory;

import eu.choreos.storagefactory.datamodel.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
