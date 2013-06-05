package org.ow2.choreos.nodes;

public class NodeNotCreatedException extends NPMException {

    private static final long serialVersionUID = -3018510520455978938L;

    public NodeNotCreatedException(String nodeId) {
	super(nodeId);
    }

    public NodeNotCreatedException(String nodeId, String message) {
	super(nodeId, message);
    }

    public String toString() {
	String result = "Could not create a node like " + super.getNodeId();
	if (super.getMessage() != null)
	    result += ". Reason:" + super.getMessage();
	return result;
    }
}
