package org.ow2.choreos.npm;


public class NodeNotDestroyed extends NPMException {

	private static final long serialVersionUID = -5097061342536022130L;
	
	public NodeNotDestroyed(String nodeId) {
		super(nodeId);
	}
	
	public NodeNotDestroyed(String nodeId, String message) {
		super(nodeId, message);
	}
	
	public String toString() {
		String result = "Could not destroy node " + super.getNodeId();
		if (super.getMessage() != null)
			result += ". Reason:" + super.getMessage();
		return result;
	}

}
