package org.ow2.choreos.npm.cloudprovider;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.npm.NodeNotFoundException;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.servicedeployer.Configuration;



/**
 * Returns always the same node
 * 
 * FixedCloudProvider does not create new nodes
 * TODO: Make FixedCloudProvider deal with several VMs 
 * 
 * @author leonardo, felps
 *
 */
public class FixedCloudProvider implements CloudProvider {

	private final String nodeIp = Configuration.get("FIXED_VM_IP");
	private final String nodeHostname = Configuration.get("FIXED_VM_HOSTNAME");
	private final String nodeUser = Configuration.get("FIXED_VM_USER");
	private final String nodePkey = Configuration.get("FIXED_VM_PRIVATE_SSH_KEY");
	private final String nodeId = "1";
	private Node node;
	
	public FixedCloudProvider() {
		
		node = new Node();
		node.setIp(nodeIp);
		node.setId(nodeId);
		node.setHostname(nodeHostname);
		node.setChefName(nodeHostname);
		node.setCpus(1);
		node.setRam(512);
		node.setSo("Ubuntu server 10.04");
		node.setStorage(10000);
		node.setZone("BR");
		node.setUser(nodeUser);
		node.setPrivateKey(nodePkey);
	}
	
	public Node createNode(Node node) throws RunNodesException {

		throw new UnsupportedOperationException("FixedCloudProvider cannot create new nodes");
	}

	public Node getNode(String nodeId) throws NodeNotFoundException {
		
		if (nodeId.equals(nodeId))
			return node;
		else
			throw new NodeNotFoundException(nodeId);
	}

	public List<Node> getNodes() {

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		return nodes;
	}

	public void destroyNode(String id) {
		
		throw new UnsupportedOperationException("FixedCloudProvider does not destroy nodes");
	}

	public Node createOrUseExistingNode(Node node) throws RunNodesException {

		return this.node;
	}

	public String getproviderName() {
		return "Fixed Provider";
	}

}
