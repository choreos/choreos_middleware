package org.ow2.choreos.deployment.nodes;

import java.util.List;

import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public interface NodePoolManager {

	/**
	 * Create a node on the cloud infrastructure.
	 * 
	 * @param node used as node specification
	 * @return the representation of the created node
	 * @throws NodeNotCreatedException if node was not created
	 */
	public Node createNode(Node node, ResourceImpact resourceImpact) throws NodeNotCreatedException;
	
	/**
	 * Retrieve information about all the nodes managed by this Node Pool Manager
	 * @return
	 */
	public List<Node> getNodes();

	/**
	 * Retrieve information of a node according to a given node id.
	 * 
	 * @param nodeId
	 * @return the representation of the requested node
	 * @throws NodeNotFoundException if the node does not exist
	 */
	public Node getNode(String nodeId) throws NodeNotFoundException; 

	/**
	 * Destroys the Virtual Machine node
	 * @param nodeId the id of the node to be destroyed
	 * @throws NodeNotDestroyed if could not destry node
	 * @throws NodeNotFoundException if the node does not exist
	 */
	public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException;

	/**
	 * Destroys all the Virtual Machine nodes
	 * @throws NodeNotDestroyed if could not destry some node
	 */
	public void destroyNodes() throws NodeNotDestroyed;

	/**
	 * Apply configurations on selected node.
	 * 
	 * Such configurations are the ones requested through the <code>applyConfig</code> operation
	 * 
	 * @param nodeId the id of the node to be upgraded
	 * @throws NodeNotUpgradedException if could not upgrade the node
	 * @throws NodeNotFoundException if the node does not exist
	 */
	public void upgradeNode(String nodeId) throws NodeNotUpgradedException, NodeNotFoundException;
	
	/**
	 * Updates the configuration of a node, but not apply the configuration.
	 * 
	 * @param config what must be deployed
	 * @return the representation of the node where the configuration will be applied,
	 * @throws if it was not possible to allocate any node
	 */
	public List<Node> applyConfig(Config config) throws ConfigNotAppliedException;
}
