/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class CloudProviderMocks {

    private static final String CP_MOCK_NAME = "MockCloudProvider";

    /**
     * This mock has a initial list of two nodes (node1 and node2) and supports
     * the creation of one node (node3).
     * 
     * @return
     * @throws Exception
     */
    public static CloudProvider getGoodMock() throws Exception {

	CloudNode node1 = new CloudNode();
	node1.setId("1");
	node1.setIp("192.168.0.11");
	CloudNode node2 = new CloudNode();
	node2.setId("2");
	node2.setIp("192.168.0.12");
	CloudNode node3 = new CloudNode();
	node3.setId("3");
	node3.setIp("192.168.0.13");

	List<CloudNode> initialNodes = new ArrayList<CloudNode>();
	initialNodes.add(node1);
	initialNodes.add(node2);

	CloudProvider cpMock = mock(CloudProvider.class);
	when(cpMock.getCloudProviderName()).thenReturn(CP_MOCK_NAME);
	when(cpMock.getNodes()).thenReturn(initialNodes);
	when(cpMock.getNode("1")).thenReturn(node1);
	when(cpMock.getNode("2")).thenReturn(node1);
	when(cpMock.createNode(any(NodeSpec.class))).thenReturn(node3);
	when(cpMock.createOrUseExistingNode(any(NodeSpec.class))).thenReturn(node1);

	return cpMock;
    }

    /**
     * This mock has a initial list of two nodes (node1 and node2) If someone
     * tries to create a new node, an exception is thrown
     * 
     * @return
     * @throws Exception
     */
    public static CloudProvider getBadMock() throws Exception {

	CloudNode node1 = new CloudNode();
	node1.setId("1");
	node1.setIp("192.168.0.11");
	CloudNode node2 = new CloudNode();
	node2.setId("2");
	node2.setIp("192.168.0.12");

	List<CloudNode> initialNodes = new ArrayList<CloudNode>();
	initialNodes.add(node1);
	initialNodes.add(node2);

	CloudProvider cpMock = mock(CloudProvider.class);
	when(cpMock.getCloudProviderName()).thenReturn(CP_MOCK_NAME);
	when(cpMock.getNodes()).thenReturn(initialNodes);
	when(cpMock.getNode("1")).thenReturn(node1);
	when(cpMock.getNode("2")).thenReturn(node1);
	when(cpMock.createNode(any(NodeSpec.class))).thenThrow(new NodeNotCreatedException("3"));
	when(cpMock.createOrUseExistingNode(any(NodeSpec.class))).thenReturn(node1);

	return cpMock;
    }

    /**
     * This mock has a initial list of two nodes (node1 and node2) If someone
     * tries to create a new node, an exception is thrown in the first try. In
     * subsequent invocations, the node3 is created.
     * 
     * @return
     * @throws Exception
     */
    public static CloudProvider getIntermitentMock() throws Exception {

	CloudNode node1 = new CloudNode();
	node1.setId("1");
	node1.setIp("192.168.0.11");
	CloudNode node2 = new CloudNode();
	node2.setId("2");
	node2.setIp("192.168.0.12");
	CloudNode node3 = new CloudNode();
	node3.setId("3");
	node3.setIp("192.168.0.13");

	List<CloudNode> initialNodes = new ArrayList<CloudNode>();
	initialNodes.add(node1);
	initialNodes.add(node2);

	CloudProvider cpMock = mock(CloudProvider.class);
	when(cpMock.getCloudProviderName()).thenReturn(CP_MOCK_NAME);
	when(cpMock.getNodes()).thenReturn(initialNodes);
	when(cpMock.getNode("1")).thenReturn(node1);
	when(cpMock.getNode("2")).thenReturn(node1);
	when(cpMock.createNode(any(NodeSpec.class))).thenThrow(new NodeNotCreatedException("3"))
		.thenReturn(node3);
	when(cpMock.createOrUseExistingNode(any(NodeSpec.class))).thenReturn(node1);

	return cpMock;
    }

}
