package org.ow2.choreos.nodes;

public class NodeNotUpgradedException extends NPMException {

    private static final long serialVersionUID = 4566523788212445328L;

    public NodeNotUpgradedException(String nodeId) {
	super(nodeId);
    }

    public NodeNotUpgradedException(String nodeId, String message) {
	super(nodeId, message);
    }

    public String toString() {
	String result = "Could not upgrade node " + super.getNodeId();
	if (super.getMessage() != null)
	    result += ". Reason:" + super.getMessage();
	return result;
    }
}
