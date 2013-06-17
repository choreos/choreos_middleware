/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderMocks;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class NodeCreatorTest {

    @Test
    public void shouldCreateNodeAndRegistryIt() throws Exception {

	CloudProvider goodCP = CloudProviderMocks.getGoodMock();

	NodeCreator nodeCreator = new NodeCreator(goodCP, false);
	CloudNode createdNode = nodeCreator.create(new NodeSpec());
	assertTrue(this.isNodeOK(createdNode));
    }

    private boolean isNodeOK(CloudNode node) {

	return (node != null) && (node.getId() != null) && (!node.getId().isEmpty());
    }

    @Test(expected = NodeNotCreatedException.class)
    public void shouldNotCreateNode() throws Exception {

	CloudProvider badCP = CloudProviderMocks.getBadMock();

	NodeCreator nodeCreator = new NodeCreator(badCP, false);
	nodeCreator.create(new NodeSpec());
    }

}
