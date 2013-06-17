/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.NotImplementedException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class NPMMocks {

    public static NodePoolManager getMock() throws NodeNotFoundException {

	CloudNode node1 = createNode("1", "192.168.122.14", "choreos1", "SMALL");
	CloudNode node2 = createNode("2", "192.168.122.160", "choreos2", "SMALL");
	CloudNode node3 = createNode("3", "192.168.122.182", "choreos3", "MEDIUM");

	List<CloudNode> nodes = new ArrayList<CloudNode>();
	nodes.add(node1);
	nodes.add(node2);
	nodes.add(node3);

	NodePoolManager npmMock = mock(NodePoolManager.class);
	when(npmMock.getNodes()).thenReturn(nodes);
	when(npmMock.getNode("1")).thenReturn(node1);
	when(npmMock.getNode("2")).thenReturn(node2);
	when(npmMock.getNode("3")).thenReturn(node3);
	return npmMock;
    }

    public static NodePoolManager getDynamicMock() throws NodeNotCreatedException {

	NodePoolManager npmMock = new NodePoolManager() {

	    AtomicInteger counter = new AtomicInteger();
	    List<CloudNode> nodes = new ArrayList<CloudNode>();

	    @Override
	    public void updateNode(String nodeId) throws NodeNotUpdatedException, NodeNotFoundException {
		throw new NotImplementedException();
	    }

	    @Override
	    public List<CloudNode> getNodes() {
		return nodes;
	    }

	    @Override
	    public CloudNode getNode(String nodeId) throws NodeNotFoundException {
		for (CloudNode node : nodes) {
		    if (nodeId.equals(node.getId())) {
			return node;
		    }
		}
		throw new NoSuchElementException();
	    }

	    @Override
	    public void destroyNodes() throws NodeNotDestroyed {
		throw new NotImplementedException();
	    }

	    @Override
	    public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException {
		throw new NotImplementedException();
	    }

	    @Override
	    public CloudNode createNode(NodeSpec NodeSpec) throws NodeNotCreatedException {

		CloudNode n = new CloudNode();
		try {
		    Thread.sleep(200);
		} catch (InterruptedException e) {
		    System.out.println("Exception at sleeping");
		}
		String id = Integer.toString(counter.getAndIncrement());
		n.setId(id);
		nodes.add(n);
		return n;
	    }

	    @Override
	    public List<CloudNode> prepareDeployment(DeploymentRequest config) throws PrepareDeploymentFailedException {
		throw new NotImplementedException();
	    }
	};

	return npmMock;
    }

    private static CloudNode createNode(String id, String ip, String host, String type) {

	CloudNode node = new CloudNode();
	node.setId(id);
	node.setIp(ip);
	node.setHostname(host);
	node.setPrivateKey("choreos.pem");
	node.setCpus(1);
	node.setRam(memFromType(type));
	node.setSo("Ubuntu server 10.04");
	node.setStorage(10000);
	node.setZone("BR");
	return node;
    }

    private static int memFromType(String type) {
	if (type.compareTo("SMALL") == 0) {
	    return 256;
	} else if (type.compareTo("MEDIUM") == 0) {
	    return 512;
	} else if (type.compareTo("LARGE") == 0) {
	    return 768;
	} else
	    return 256;
    }
}
