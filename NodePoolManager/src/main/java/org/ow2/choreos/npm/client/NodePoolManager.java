package org.ow2.choreos.npm.client;

import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;

public interface NodePoolManager {

	/**
	 * Create a node on the cloud infrastructure.
	 * 
	 * @return the representation of the created node,
	 * or <code>null<code> if it was not created
	 */
	public NodeRestRepresentation createNode();

	/**
	 * Retrieve information of a node according to a given node id.
	 * 
	 * @param nodeId
	 * @return the representation of the requested node,
	 * or <code>null<code> if the node does not exist
	 */
	public NodeRestRepresentation getNode(String nodeId); 

	/**
	 * Updates the configuration of a node, but not apply the configuration.
	 * 
	 * @param configName corresponds to the chef recipe
	 * @return the representation of the node where the configuration will be applied,
	 * or <code>null</code> if it was not possible to allocate any node
	 */
	public NodeRestRepresentation applyConfig(String configName);
	
	/**
	 * Apply the configuration on selected nodes.
	 * 
	 * Such configurations are the ones requested through the <code>applyConfig</code> operation
	 * 
	 * @return <code>true</code> if everything it seems to be OK,
	 * or <code>false</code> if something went wrong
	 */
	public boolean upgradeNodes();
}
