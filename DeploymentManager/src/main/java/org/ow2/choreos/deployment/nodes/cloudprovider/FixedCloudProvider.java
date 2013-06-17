/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

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

    public static final String FIXED_CLOUD_PROVIDER = "Fixed Provider";
    private Map<String, CloudNode> nodes = null;

    public FixedCloudProvider() {

	nodes = new HashMap<String, CloudNode>();

	String[] ips = DeploymentManagerConfiguration.getMultiple("FIXED_VM_IPS");
	String[] hosts = DeploymentManagerConfiguration.getMultiple("FIXED_VM_HOSTNAMES");
	String[] users = DeploymentManagerConfiguration.getMultiple("FIXED_VM_USERS");
	String[] keys = DeploymentManagerConfiguration.getMultiple("FIXED_VM_PRIVATE_SSH_KEYS");
	String[] types = DeploymentManagerConfiguration.getMultiple("FIXED_VM_TYPES");

	if ((ips.length == hosts.length) && (ips.length == users.length) && (ips.length == keys.length)
		&& (types.length == ips.length)) {
	    int node_id = 0;
	    for (int i = 0; i < ips.length; i++, node_id++) {
		CloudNode node = new CloudNode();
		String id = setNode(ips[i], hosts[i], users[i], keys[i], types[i], node, node_id);
		addNode(node, id);
	    }
	}
    }

    private void addNode(CloudNode node, String id) {
	nodes.put(id, node);
    }

    private String setNode(String ip, String host, String user, String key, String type, CloudNode node, int id) {
	node.setIp(ip);
	node.setHostname(host);
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
	if (type.compareTo("SMALL") == 0) {
	    return 256;
	} else if (type.compareTo("MEDIUM") == 0) {
	    return 512;
	} else if (type.compareTo("LARGE") == 0) {
	    return 768;
	} else
	    return 256;
    }

    public CloudNode getNode(String nodeId) throws NodeNotFoundException {
	if (nodes.containsKey(nodeId))
	    return nodes.get(nodeId);
	throw new NodeNotFoundException(nodeId);
    }

    public List<CloudNode> getNodes() {
	List<CloudNode> theList = new ArrayList<CloudNode>();
	theList.addAll(nodes.values());
	return theList;
    }

    @Override
    public CloudNode createOrUseExistingNode(NodeSpec nodeSpec) throws NodeNotCreatedException {

	if (nodes != null && !nodes.keySet().isEmpty()) {
	    Iterator<String> it = nodes.keySet().iterator();
	    return nodes.get(it.next());
	} else {
	    // TODO should throws RunNodesException
	    // actually we should't use RunNodesException, but a Exception of
	    // our own
	    throw new IllegalStateException(
		    "FixedCloudProvider does not creates nodes and there is no node available in the moment.");
	}
    }

    public String getProviderName() {
	return FIXED_CLOUD_PROVIDER;
    }

    /*
     * ============================= UNSUPPORTED OPERATIONS
     * ============================
     */

    public void destroyNode(String id) {

	throw new UnsupportedOperationException("FixedCloudProvider does not destroy nodes");
    }

    @Override
    public CloudNode createNode(NodeSpec nodeSpec) throws NodeNotCreatedException {

	throw new UnsupportedOperationException("FixedCloudProvider cannot create new nodes");
    }
}
