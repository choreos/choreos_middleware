package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 
 * @author leonardo, felps, tfmend
 *
 */
public class FixedCloudProvider implements CloudProvider {

	private Map<String, Node> nodes = null;

	public FixedCloudProvider() {
		
		// TODO: resolve enactment of two replicas creates two cloud provider object
		
		nodes = new HashMap<String, Node>();
		
		String[] ips = Configuration.getMultiple("FIXED_VM_IPS");
		String[] hosts = Configuration.getMultiple("FIXED_VM_HOSTNAMES");
		String[] users = Configuration.getMultiple("FIXED_VM_USERS");
		String[] keys = Configuration.getMultiple("FIXED_VM_PRIVATE_SSH_KEYS");
		
		if( (ips.length == hosts.length) && (ips.length == users.length) && (ips.length == keys.length ) ) {
			int node_id = 0;
			for(int i = 0; i < ips.length; i++, node_id++) {
				Node node = new Node();
				String id = setNode(ips, hosts, users, keys, i, node, node_id);
				addNode(node, id);
			}
		}
	}

	private void addNode(Node node, String id) {
		nodes.put(id, node);
	}

	private String setNode(String[] ips, 
			String[] hosts, String[] users, String[] keys, int i, Node node, int id) {
		node.setIp(ips[i]);
		node.setHostname(hosts[i]);
		node.setChefName(hosts[i]);
		node.setUser(users[i]);
		node.setPrivateKey(keys[i]);

		//String id = UUID.randomUUID().toString();
		node.setId(Integer.toString(id));
		
		
		node.setCpus(1);
		node.setRam(512);
		node.setSo("Ubuntu server 10.04");
		node.setStorage(10000);
		node.setZone("BR");
		return node.getId();
	}
	
	public Node createNode(Node node) throws RunNodesException {

		throw new UnsupportedOperationException("FixedCloudProvider cannot create new nodes");
	}

	public Node getNode(String nodeId) throws NodeNotFoundException {
		if (nodes.containsKey(nodeId))
			return nodes.get(nodeId);
		throw new NodeNotFoundException(nodeId);
	}

	public List<Node> getNodes() {
		List<Node> theList = new ArrayList<Node>();
		theList.addAll(nodes.values());
		return theList;
	}

	public void destroyNode(String id) {
		
		throw new UnsupportedOperationException("FixedCloudProvider does not destroy nodes");
	}

	public Node createOrUseExistingNode(Node node) throws RunNodesException {
		return (nodes.containsKey(node.getId())) ? nodes.get(node.getId()) : createNode(node);
	}

	public String getProviderName() {
		return "Fixed Provider";
	}

}
