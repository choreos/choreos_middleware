package org.ow2.choreos.ee.nodes.cm;

public class NodeNotPreparedException extends Exception {

    private static final long serialVersionUID = -8829878596239750125L;

    public NodeNotPreparedException(String nodeId) {
        super("Deployment on node " + nodeId + " nor prepared");
    }
}
