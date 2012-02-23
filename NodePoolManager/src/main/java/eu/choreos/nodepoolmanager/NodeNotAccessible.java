package eu.choreos.nodepoolmanager;

import eu.choreos.nodepoolmanager.datamodel.Node;

public class NodeNotAccessible extends Exception {

	private static final long serialVersionUID = 1L;
	private Node node;
	
	public NodeNotAccessible(Node node) {
		this.node = node;
	}
	
	public String toString() {
		
		return "Cannot connect to node " + node.toString();
	}
}
