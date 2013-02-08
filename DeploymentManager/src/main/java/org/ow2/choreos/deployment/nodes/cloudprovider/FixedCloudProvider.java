package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.datamodel.Node;



/**
 * Handles a fixed pool of machines
 * 
 * FixedCloudProvider does not create new nodes
 * It uses the VMs listed in the FIXED_VM_IPS property 
 * 
 * @author leonardo, felps
 *
 */
public class FixedCloudProvider implements CloudProvider {

	private final String nodeHostname = Configuration.get("FIXED_VM_HOSTNAME");
	private final String nodeUser = Configuration.get("FIXED_VM_USER");
	private final String nodePkey = Configuration.get("FIXED_VM_PRIVATE_SSH_KEY");
	private List<Node> nodes = new ArrayList<Node>();
	
	public FixedCloudProvider() {

		this.fillIpsList();
	}
	
	private void fillIpsList() {
		
		this.nodes = new ArrayList<Node>();
		FixedIPsRetriever retriever = new FixedIPsRetriever();
		int nodeId = 1;
		for (String ip: retriever.retrieveIPs()) {
			Node node = new Node();
			node.setIp(ip);
			node.setId(Integer.toString(nodeId++));
			node.setHostname(nodeHostname);
			node.setChefName(nodeHostname);
			node.setCpus(1);
			node.setRam(512);
			node.setSo("Ubuntu server 10.04");
			node.setStorage(10000);
			node.setZone("BR");
			node.setUser(nodeUser);
			node.setPrivateKey(nodePkey);
			nodes.add(node);
		}
	}
	
	public Node createNode(Node node) throws RunNodesException {

		throw new UnsupportedOperationException("FixedCloudProvider cannot create new nodes");
	}

	public Node getNode(String nodeId) throws NodeNotFoundException {
		
		try {
			int id = Integer.parseInt(nodeId);
			return this.nodes.get(id);
		}
		catch (NumberFormatException e) {
			throw new NodeNotFoundException(nodeId);
		}
		catch (IndexOutOfBoundsException e) {
			throw new NodeNotFoundException(nodeId);
		}
	}

	public List<Node> getNodes() {

		return Collections.unmodifiableList(this.nodes);
	}

	public void destroyNode(String id) {
		
		throw new UnsupportedOperationException("FixedCloudProvider does not destroy nodes");
	}

	public Node createOrUseExistingNode(Node node) throws RunNodesException {

		if (this.nodes != null && !this.nodes.isEmpty())
			return this.nodes.get(0);
		else
			throw new IllegalStateException("Sorry, I do not have any VM available in the moment");
	}

	public String getProviderName() {
		return "Fixed Provider";
	}

}
