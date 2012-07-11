package org.ow2.choreos.npm;

import java.util.List;

import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;

public interface NodePoolManager {

	/**
	 * Create a node on the cloud infrastructure.
	 * 
	 * @param node used as node specification
	 * @return the representation of the created node,
	 * or <code>null<code> if it was not created
	 */
	public Node createNode(Node node);
	
	/**
	 * Retrieve information about all the nodes managed by this Node Pool Manager
	 * @return
	 */
	public List<Node> getNodes();

	/**
	 * Retrieve information of a node according to a given node id.
	 * 
	 * @param nodeId
	 * @return the representation of the requested node,
	 * or <code>null<code> if the node does not exist
	 */
	public Node getNode(String nodeId); 

	/**
	 * Updates the configuration of a node, but not apply the configuration.
	 * 
	 * @param config what must be deployed
	 * @return the representation of the node where the configuration will be applied,
	 * or <code>null</code> if it was not possible to allocate any node
	 */
	public Node applyConfig(Config config);
	
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
