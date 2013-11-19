/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Test;
import org.ow2.choreos.ee.CloudConfiguration;
import org.ow2.choreos.ee.nodes.IdlePool;
import org.ow2.choreos.ee.nodes.IdlePoolFactory;
import org.ow2.choreos.ee.nodes.NPMFactory;
import org.ow2.choreos.ee.nodes.NodeCreator;
import org.ow2.choreos.ee.nodes.NodeCreatorFactory;
import org.ow2.choreos.ee.nodes.NodeRegistry;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class NPMImplTest {

    private static final int POOL_INITIAL_SIZE = 1;
    private static final int POOL_THRESHOLD = -1;

    @Test
    public void shouldCreateNode() throws Exception {

	NodeRegistry reg = NodeRegistry.getInstance();
	reg.clear();

	CloudProvider cp = mock(CloudProvider.class);
	CloudProviderFactory.cloudProviderForTesting = cp;
	CloudProviderFactory.testing = true;

	NodeCreator creator = NodeCreatorMocks.getGoodMock();
	NodeCreatorFactory.nodeCreatorForTesting = creator;
	NodeCreatorFactory.testing = true;
	IdlePool pool = IdlePoolFactory.getCleanInstance(CloudConfiguration.getCloudConfigurationInstance(),
		POOL_INITIAL_SIZE, POOL_THRESHOLD);

	NodePoolManager npm = NPMFactory.getNewNPMInstance(CloudConfiguration.DEFAULT, pool);

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
	CloudProviderFactory.cloudProviderForTesting = cp;
	CloudProviderFactory.testing = true;

	NodeCreator creator = NodeCreatorMocks.getGoodMock();
	NodeCreatorFactory.nodeCreatorForTesting = creator;
	NodeCreatorFactory.testing = true;

	IdlePool pool = IdlePoolFactory.getCleanInstance(CloudConfiguration.getCloudConfigurationInstance(),
		POOL_INITIAL_SIZE, POOL_THRESHOLD);

	NodePoolManager npm = NPMFactory.getNewNPMInstance(CloudConfiguration.DEFAULT, pool);

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

	CloudProvider cp = mock(CloudProvider.class);
	CloudProviderFactory.cloudProviderForTesting = cp;
	CloudProviderFactory.testing = true;

	NodeCreator creator1 = NodeCreatorMocks.getGoodMock();
	NodeCreatorFactory.nodeCreatorForTesting = creator1;
	NodeCreatorFactory.testing = true;
	IdlePool pool1 = IdlePoolFactory.getCleanInstance(CloudConfiguration.getCloudConfigurationInstance(),
		POOL_INITIAL_SIZE, POOL_THRESHOLD);
	NodePoolManager npm1 = NPMFactory.getNewNPMInstance(CloudConfiguration.DEFAULT, pool1);

	CloudNode createdNode = npm1.createNode(new NodeSpec());
	assertTrue(isNodeOK(createdNode));

	NodeCreator creator2 = NodeCreatorMocks.getGoodMock();
	NodeCreatorFactory.nodeCreatorForTesting = creator2;
	NodeCreatorFactory.testing = true;

	IdlePool pool2 = IdlePoolFactory.getCleanInstance(CloudConfiguration.getCloudConfigurationInstance(),
		POOL_INITIAL_SIZE, POOL_THRESHOLD);
	NodePoolManager npm2 = NPMFactory.getNewNPMInstance(CloudConfiguration.DEFAULT, pool2);

	CloudNode fromOtherNPM = npm2.getNode(createdNode.getId());
	assertTrue(isNodeOK(fromOtherNPM));
	assertEquals(fromOtherNPM, createdNode);
    }

    private boolean isNodeOK(CloudNode node) {
	return (node != null) && (node.getId() != null) && (!node.getId().isEmpty());
    }

    @After
    public void tearDown() {
	NodeCreatorFactory.testing = false;
	CloudProviderFactory.testing = false;
    }
}
