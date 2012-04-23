package eu.choreos.nodepoolmanager.cloudprovider;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.compute.RunNodesException;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.NodeNotFoundException;
import eu.choreos.nodepoolmanager.datamodel.Node;


/**
 * Returns always the same node 
 * 
 * @author leonardo, felps
 *
 */
public class FixedCloudProvider implements CloudProvider {

	private String NODE_IP = Configuration.get("FIXED_VM_IP");
	private String NODE_ID = "1";
	private Node node;
	
	public FixedCloudProvider() {
		
		node = new Node();
		node.setIp(NODE_IP);
		node.setId(NODE_ID);
		node.setCpus(1);
		node.setHostname("choreos-node");
		node.setRam(512);
		node.setSo("Ubuntu server 11.10");
		node.setStorage(10000);
		node.setZone("BR");
		node.setUser("choreos");
		node.setPrivateKey(Configuration.get("FIXED_VM_PRIVATE_SSH_KEY"));
	}
	
	public Node createNode(Node node) throws RunNodesException {

		return this.node;
	}

	public Node getNode(String nodeId) throws NodeNotFoundException {
		
		if (nodeId.equals(NODE_ID))
			return node;
		else
			return null;
	}

	public List<Node> getNodes() {

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		return nodes;
	}

	public void destroyNode(String id) {
		
		return;
	}

	public Node createOrUseExistingNode(Node node) throws RunNodesException {

		return node;
	}

	public String getproviderName() {
		return "Fixed Provider";
	}

}
