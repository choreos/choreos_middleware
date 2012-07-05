package org.ow2.choreos.npm;

import org.ow2.choreos.npm.datamodel.Node;

public class NodeNotAccessibleException extends Exception {

	private static final long serialVersionUID = 1L;
	private Node node;
	
	public NodeNotAccessibleException(Node node) {
		this.node = node;
	}
	
	public String toString() {
		
		return "Cannot connect to node " + node.toString();
	}
}
