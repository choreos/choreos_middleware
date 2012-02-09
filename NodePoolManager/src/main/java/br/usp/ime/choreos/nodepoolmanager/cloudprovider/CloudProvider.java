package br.usp.ime.choreos.nodepoolmanager.cloudprovider;

import java.util.List;

import org.jclouds.compute.RunNodesException;

import br.usp.ime.choreos.nodepoolmanager.Node;
import br.usp.ime.choreos.nodepoolmanager.NodeNotFoundException;

/**
 * Provides access to cloud service functions to create nodes on the cloud
 * Each specific provider (e.g. AmazonWS) must have an implementing class of this interface 
 * 
 * @author leonardo, felps
 *
 */
public interface CloudProvider {
	
	 public Node createNode(Node node) throws RunNodesException;
	 
	 public Node getNode(String nodeId) throws NodeNotFoundException;
	 
	 public List<Node> getNodes();
	 
	 public void destroyNode(String id);
	 
	 public Node createOrUseExistingNode(Node node) throws RunNodesException;

}
