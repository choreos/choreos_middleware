package org.ow2.choreos.deployment.nodes.cm;

public class NodeNotBootstrappedException extends Exception {

	private static final long serialVersionUID = -5145339552519586119L;
	
	public NodeNotBootstrappedException() {
		
	}
	
	public NodeNotBootstrappedException(String msg) {
		super(msg);
	}
}
