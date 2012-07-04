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
 * FixedCloudProvider does not create new nodes
 * TODO: Make FixedCloudProvider deal with several VMs 
 * 
 * @author leonardo, felps
 *
 */
public class FixedCloudProvider implements CloudProvider {

	private String nodeIp = Configuration.get("FIXED_VM_IP");
	private String nodeHostname = Configuration.get("FIXED_VM_HOSTNAME");
	private String nodeUser = Configuration.get("FIXED_VM_USER");
	private String nodePkey = Configuration.get("FIXED_VM_PRIVATE_SSH_KEY");
	private String nodeId = "1";
	private Node node;
	
	public FixedCloudProvider() {
		
		node = new Node();
		node.setIp(nodeIp);
		node.setId(nodeId);
		node.setCpus(1);
		node.setHostname(nodeHostname);
		node.setRam(512);
		node.setSo("Ubuntu server 11.10");
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
			throw new NodeNotFoundException();
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
