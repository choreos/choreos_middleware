/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.ee.nodes.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.ee.config.CloudConfiguration;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.ee.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;
import org.ow2.choreos.utils.SshUtil;

/**
 * Please, before running these tests, read the README file
 * 
 * @author leonardo
 */
@Category(IntegrationTest.class)
public class FixedConnectionTest {

    private static final String CLOUD_ACCOUNT = CloudConfiguration.DEFAULT;

    @BeforeClass
    public static void setupClass() {
	LogConfigurator.configLog();
    }

    @Test
    public void shouldConnectToTheNode() throws NodeNotCreatedException {

	CloudProvider cp = new FixedCloudProvider();
	cp.setCloudConfiguration(CloudConfiguration.getCloudConfigurationInstance(CLOUD_ACCOUNT));
	CloudNode node = cp.createOrUseExistingNode(new NodeSpec());

	SshUtil ssh = null;
	ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	assertTrue(ssh.isAccessible());
	ssh.disconnect();
    }

}
