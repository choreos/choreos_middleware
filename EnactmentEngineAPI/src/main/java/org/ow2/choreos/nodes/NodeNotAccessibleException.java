package org.ow2.choreos.nodes;

/**
 * Used if it is not possible to connect in a node using SSH
 * 
 * @author leonardo
 *
 */
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
