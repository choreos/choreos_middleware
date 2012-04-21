package eu.choreos.nodepoolmanager.cloudprovider;

import java.util.List;

import org.jclouds.compute.RunNodesException;

import eu.choreos.nodepoolmanager.NodeNotFoundException;
import eu.choreos.nodepoolmanager.datamodel.Node;

/**
 * Provides access to cloud service functions to create nodes on the cloud Each
 * specific provider (e.g. AmazonWS) must have an implementing class of this
 * interface
 * 
 * @author leonardo, felps
 * 
 */
public interface CloudProvider {

	public String getproviderName();
	
	public Node createNode(Node node) throws RunNodesException;

	public Node getNode(String nodeId) throws NodeNotFoundException;

	public List<Node> getNodes();

	public void destroyNode(String id);

	public Node createOrUseExistingNode(Node node) throws RunNodesException;

}
