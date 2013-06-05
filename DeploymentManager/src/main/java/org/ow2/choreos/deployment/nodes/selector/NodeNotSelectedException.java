package org.ow2.choreos.deployment.nodes.selector;

public class NodeNotSelectedException extends Exception {

    private static final long serialVersionUID = 6172403976309518959L;

    public NodeNotSelectedException() {

    }

    public NodeNotSelectedException(String msg) {

	super(msg);
    }
}
