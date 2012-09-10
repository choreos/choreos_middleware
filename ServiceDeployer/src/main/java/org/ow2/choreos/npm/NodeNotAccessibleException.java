package org.ow2.choreos.npm;


public class NodeNotAccessibleException extends NPMException {

	private static final long serialVersionUID = -6491291627017563771L;
	
	public NodeNotAccessibleException(String nodeId) {
		super(nodeId);
	}
	
	@Override
	public String toString() {
		return "Cannot connect to node " + super.getNodeId();
	}
}
