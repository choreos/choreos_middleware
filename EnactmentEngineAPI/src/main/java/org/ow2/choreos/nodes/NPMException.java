package org.ow2.choreos.nodes;

public class NPMException extends Exception {

    private static final long serialVersionUID = -4401668770096601392L;
    private final String nodeId;

    public NPMException(String nodeId) {
	this.nodeId = nodeId;
    }

    public NPMException(String nodeId, String message) {
	super(message);
	this.nodeId = nodeId;
    }

    public String getNodeId() {
	return this.nodeId;
    }
}
