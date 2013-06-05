/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.CommandLineException;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class AWSCloudProviderTest {

    private final CloudProvider infra = new AWSCloudProvider();
    private Node node = new Node();
    private ResourceImpact resourceImpact = new ResourceImpact();

    @Before
    public void SetUp() {
	LogConfigurator.configLog();
	node.setImage("us-east-1/ami-ccf405a5");
	DeploymentManagerConfiguration.set("DEFAULT_PROVIDER", "");
    }

    @Test
    public void shouldCreateAndDeleteNode() throws NodeNotCreatedException, NodeNotDestroyed, NodeNotFoundException,
	    CommandLineException, InterruptedException {

	Node created = infra.createNode(node, resourceImpact);
	System.out.println("created " + created);
	assertTrue(created != null);

	infra.destroyNode(created.getId());
    }

}