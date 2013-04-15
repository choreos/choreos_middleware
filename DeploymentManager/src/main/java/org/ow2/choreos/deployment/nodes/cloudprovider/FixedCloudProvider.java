package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;



/**
 * Handles a fixed pool of machines
 * 
 * FixedCloudProvider does not create new nodes It uses the VMs listed in the
 * FIXED_VM_IPS property
 * 
 * 
 * @author leonardo, felps, tfmend
 * 
 */
public class FixedCloudProvider implements CloudProvider {

	private Map<String, Node> nodes = null;

	public FixedCloudProvider() {

		nodes = new HashMap<String, Node>();

		String[] ips = Configuration.getMultiple("FIXED_VM_IPS");
		String[] hosts = Configuration.getMultiple("FIXED_VM_HOSTNAMES");
		String[] users = Configuration.getMultiple("FIXED_VM_USERS");
		String[] keys = Configuration.getMultiple("FIXED_VM_PRIVATE_SSH_KEYS");
		String[] types = Configuration.getMultiple("FIXED_VM_TYPES");

		if ((ips.length == hosts.length) && (ips.length == users.length)
				&& (ips.length == keys.length) && (types.length == ips.length)) {
			int node_id = 0;
			for (int i = 0; i < ips.length; i++, node_id++) {
				Node node = new Node();
				String id = setNode(ips[i], hosts[i], users[i], keys[i], types[i], node, node_id);
				addNode(node, id);
			}
		}
	}

	private void addNode(Node node, String id) {
		nodes.put(id, node);
	}

	private String setNode(String ip, String host, String user, String key, String type, Node node, int id) {
		node.setIp(ip);
		node.setHostname(host);
		node.setChefName(host);
		node.setUser(user);
		node.setPrivateKey(key);
		node.setId(Integer.toString(id));
		node.setCpus(1);
		node.setRam(memFromType(type));
		node.setSo("Ubuntu server 10.04");
		node.setStorage(10000);
		node.setZone("BR");
		return node.getId();
	}

	private int memFromType(String type) {
		if(type.compareTo("SMALL") == 0) {
			return 256;
		} else if(type.compareTo("MEDIUM") == 0) {
			return 512;
		} else if(type.compareTo("LARGE") == 0) {
			return 768;
		} else return 256;
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

	@Override
	public Node createOrUseExistingNode(Node node, ResourceImpact resourceImpact)
			throws RunNodesException {
		if (node != null) {
			return (nodes.containsKey(node.getId())) ? nodes.get(node.getId())
					: createNode(node, resourceImpact);
		} else {
			if (!nodes.keySet().isEmpty()) {
				Iterator<String> it = nodes.keySet().iterator();
				return nodes.get(it.next());
			} else {
				// TODO should throws RunNodesException
				// actually we should't use RunNodesException, but a Exception of our own
				throw new IllegalStateException(
						"FixedCloudProvider does not creates nodes and there is no node available in the moment.");
			}
		}
	}

	public String getProviderName() {
		return "Fixed Provider";
	}





	/*============================= UNSUPPORTED OPERATIONS ============================*/

	public void destroyNode(String id) {

		throw new UnsupportedOperationException(
				"FixedCloudProvider does not destroy nodes");
	}

	@Override
	public Node createNode(Node node, ResourceImpact resourceImpact)
			throws RunNodesException {

		throw new UnsupportedOperationException(
				"FixedCloudProvider cannot create new nodes");
	}
}
