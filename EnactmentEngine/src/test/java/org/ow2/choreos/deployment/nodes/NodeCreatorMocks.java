/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.ow2.choreos.deployment.nodes.NodeCreator;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class NodeCreatorMocks {

    /**
     * This mock supports the creation of one node.
     * 
     * @return
     * @throws Exception
     */
    public static NodeCreator getGoodMock() throws Exception {
        CloudNode node1 = new CloudNode();
        node1.setId("1");
        node1.setIp("192.168.0.11");
        NodeCreator mock = mock(NodeCreator.class);
        when(mock.createBootstrappedNode(any(NodeSpec.class))).thenReturn(node1);
        return mock;
    }

    /**
     * If someone tries to create a new node, an exception is thrown.
     * 
     * @return
     * @throws Exception
     */
    public static NodeCreator getBadMock() throws Exception {
        NodeCreator mock = mock(NodeCreator.class);
        when(mock.createBootstrappedNode(any(NodeSpec.class))).thenThrow(new NodeNotCreatedException("3"));
        return mock;
    }

    /**
     * If someone tries to create a new node, an exception is thrown in the
     * first try. In subsequent invocations, a node is created.
     * 
     * @return
     * @throws Exception
     */
    public static NodeCreator getIntermitentMock() throws Exception {
        CloudNode node1 = new CloudNode();
        node1.setId("1");
        node1.setIp("192.168.0.11");
        NodeCreator mock = mock(NodeCreator.class);
        when(mock.createBootstrappedNode(any(NodeSpec.class))).thenThrow(new NodeNotCreatedException("3")).thenReturn(
                node1);
        return mock;
    }

}
