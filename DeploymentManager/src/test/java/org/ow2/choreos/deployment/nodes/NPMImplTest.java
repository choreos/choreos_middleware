/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

import static org.mockito.Mockito.mock;

public class NPMImplTest {

    @Test
    public void shouldCreateNode() throws Exception {

	NodeRegistry reg = NodeRegistry.getInstance();
	reg.clear();

	CloudProvider cp = mock(CloudProvider.class);
	NodeCreator creator = NodeCreatorMocks.getGoodMock();
	IdlePool pool = IdlePool.getCleanInstance(1, creator);

	NodePoolManager npm = new NPMImpl(cp, creator, pool);

	CloudNode createdNode = npm.createNode(new NodeSpec());
	assertTrue(isNodeOK(createdNode));
	CloudNode fromRegNode = reg.getNode(createdNode.getId());
	assertTrue(isNodeOK(fromRegNode));
	assertEquals(fromRegNode, createdNode);
    }

    @Test(expected = NodeNotFoundException.class)
    public void shouldCreateAndDeleteNode() throws Exception {

	NodeRegistry reg = NodeRegistry.getInstance();
	reg.clear();

	CloudProvider cp = mock(CloudProvider.class);
	NodeCreator creator = NodeCreatorMocks.getGoodMock();
	IdlePool pool = IdlePool.getCleanInstance(1, creator);

	NodePoolManager npm = new NPMImpl(cp, creator, pool);

	CloudNode createdNode = npm.createNode(new NodeSpec());
	assertTrue(isNodeOK(createdNode));
	CloudNode fromRegNode = reg.getNode(createdNode.getId());
	assertTrue(isNodeOK(fromRegNode));
	assertEquals(fromRegNode, createdNode);

	npm.destroyNode(createdNode.getId());
	fromRegNode = reg.getNode(createdNode.getId());
    }

    @Test
    public void shouldAccessNodeFromDifferentNPMInstances() throws Exception {

	NodeRegistry reg = NodeRegistry.getInstance();
	reg.clear();

	CloudProvider cp1 = mock(CloudProvider.class);
	NodeCreator creator1 = NodeCreatorMocks.getGoodMock();
	IdlePool pool1 = IdlePool.getCleanInstance(1, creator1);
	NodePoolManager npm1 = new NPMImpl(cp1, creator1, pool1);

	CloudNode createdNode = npm1.createNode(new NodeSpec());
	assertTrue(isNodeOK(createdNode));

	CloudProvider cp2 = mock(CloudProvider.class);
	NodeCreator creator2 = NodeCreatorMocks.getGoodMock();
	IdlePool pool2 = IdlePool.getCleanInstance(1, creator2);
	NodePoolManager npm2 = new NPMImpl(cp2, creator2, pool2);

	CloudNode fromOtherNPM = npm2.getNode(createdNode.getId());
	assertTrue(isNodeOK(fromOtherNPM));
	assertEquals(fromOtherNPM, createdNode);
    }

    private boolean isNodeOK(CloudNode node) {

	return (node != null) && (node.getId() != null) && (!node.getId().isEmpty());
    }
}
