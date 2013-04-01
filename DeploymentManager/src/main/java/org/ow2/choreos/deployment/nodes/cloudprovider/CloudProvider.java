package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.List;

import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.deployment.nodes.NodeNotDestroyed;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;


/**
 * Provides access to cloud service functions to create nodes on the cloud 
 * 
 * Each specific provider (e.g. AmazonWS) must have an implementing class 
 * of this interface.
 * 
 * @author leonardo, felps, furtado
 * 
 */
public interface CloudProvider {

	public String getProviderName();
	
	public Node createNode(Node node, ResourceImpact resourceImpact) throws RunNodesException;

	public Node getNode(String nodeId) throws NodeNotFoundException;

	public List<Node> getNodes();

	public void destroyNode(String id) throws NodeNotDestroyed, NodeNotFoundException;

	public Node createOrUseExistingNode(Node node, ResourceImpact resourceImpact) throws RunNodesException;

}
