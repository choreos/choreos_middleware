package org.ow2.choreos.npm;


public class NodeNotFoundException extends NPMException {

	private static final long serialVersionUID = 826431667001507667L;

	public NodeNotFoundException (String nodeId) {
		super(nodeId);
	}
	
	@Override
	public String toString() {
		return "Node " + super.getNodeId() +  " not found.";
	}

}
