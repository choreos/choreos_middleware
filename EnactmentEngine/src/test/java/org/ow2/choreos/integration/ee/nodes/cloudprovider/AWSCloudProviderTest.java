/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.ee.nodes.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.ee.DeploymentManagerConfiguration;
import org.ow2.choreos.ee.nodes.cloudprovider.AWSCloudProvider;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.CommandLineException;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class AWSCloudProviderTest {

    private final CloudProvider infra = new AWSCloudProvider();
    private NodeSpec nodeSpec = new NodeSpec();

    @Before
    public void SetUp() {
        LogConfigurator.configLog();
        nodeSpec.setImage("us-east-1/ami-ccf405a5");
        DeploymentManagerConfiguration.set("DEFAULT_PROVIDER", "");
    }

    @Test
    public void shouldCreateAndDeleteNode() throws NodeNotCreatedException, NodeNotDestroyed, NodeNotFoundException,
            CommandLineException, InterruptedException {

        CloudNode created = infra.createNode(nodeSpec);
        System.out.println("created " + created);
        assertTrue(created != null);

        Thread.sleep(1000);

        infra.destroyNode(created.getId());
    }

}