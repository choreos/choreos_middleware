package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;

/**
 * Local node registry.
 * 
 * The nodes managed by the NodePoolManager
 * 
 * @author leonardo
 * 
 */
public class NodeRegistry {

    private static NodeRegistry instance = new NodeRegistry();

    private Map<String, Node> nodes = new ConcurrentHashMap<String, Node>();

    private NodeRegistry() {

    }

    public static NodeRegistry getInstance() {
	return instance;
    }

    public synchronized void putNode(Node node) {
	this.nodes.put(node.getId(), node);
    }

    public Node getNode(String nodeId) throws NodeNotFoundException {
	Node node = this.nodes.get(nodeId);
	if (node == null) {
	    throw new NodeNotFoundException(nodeId);
	}
	return node;
    }

    public void deleteNode(String nodeId) {
	this.nodes.remove(nodeId);
    }

    public List<Node> getNodes() {
	return new ArrayList<Node>(this.nodes.values());
    }

    public void clear() {
	this.nodes.clear();
    }
}
