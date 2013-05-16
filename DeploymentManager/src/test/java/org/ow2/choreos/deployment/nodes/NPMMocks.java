package org.ow2.choreos.deployment.nodes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Node;

public class NPMMocks {

	public static NodePoolManager getMock() throws NodeNotFoundException {
		
		Node node1 = createNode("1", "192.168.122.14", "choreos1", "SMALL");
		Node node2 = createNode("2", "192.168.122.160", "choreos2", "SMALL");
		Node node3 = createNode("3", "192.168.122.182", "choreos3", "MEDIUM");
		
		List<Node> nodes = new ArrayList<Node>();
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
	
	private static Node createNode(String id, String ip, String host, String type) {
		
		Node node = new Node();
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
		if(type.compareTo("SMALL") == 0) {
			return 256;
		} else if(type.compareTo("MEDIUM") == 0) {
			return 512;
		} else if(type.compareTo("LARGE") == 0) {
			return 768;
		} else return 256;
	}
}
