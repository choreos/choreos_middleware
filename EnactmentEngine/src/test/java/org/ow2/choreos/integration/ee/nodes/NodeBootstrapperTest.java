/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.ee.nodes;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.ee.CloudConfiguration;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.ee.nodes.cm.BootstrapChecker;
import org.ow2.choreos.ee.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * Before run this test, restore your VM to a snapshot before the bootstrap
 * 
 * If the machine is already bootstrapped the test must still pass, but it will
 * not properly test the system
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class NodeBootstrapperTest {

    protected CloudNode node;

    /*
     * You may edit this attr to the actual cloud account you want to use
     */
    private static final String CLOUD_ACCOUNT = CloudConfiguration.DEFAULT;

    @Before
    public void setUp() {
	LogConfigurator.configLog();
    }

    /**
     * Beware, this test will leave the node bootstrapped
     * 
     * @throws Exception
     */
    @Test
    public void shouldLeaveNodeBootstraped() throws Exception {

	CloudProviderFactory factory = CloudProviderFactory.getFactoryInstance();
	CloudConfiguration cloudConfigurationInstance = CloudConfiguration.getCloudConfigurationInstance(CLOUD_ACCOUNT);
	CloudProvider cp = factory.getCloudProviderInstance(cloudConfigurationInstance);
	node = cp.createNode(new NodeSpec());
	System.out.println(node);

	BootstrapChecker checker = new BootstrapChecker();
	if (!checker.isBootstrapped(node)) {
	    System.out.println("Going to bootstrap the node");
	    NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
	    bootstrapper.bootstrapNode();
	    System.out.println("Checking if bootstrap was OK");
	    assertTrue(checker.isBootstrapped(node));
	    System.out.println("OK!");
	} else {
	    System.out.println("Node was already bootstrapped");
	}
    }
}
