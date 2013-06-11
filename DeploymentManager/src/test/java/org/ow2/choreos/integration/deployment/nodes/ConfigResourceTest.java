/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.jclouds.compute.RunNodesException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

@Category(IntegrationTest.class)
public class ConfigResourceTest extends BaseTest {

    /**
     * This test supposes the "getting-started" recipe is already available on
     * the chef server This recipe must create the getting-started.txt file on
     * home directory
     * 
     * @throws RunNodesException
     * @throws PrepareDeploymentFailedException
     * @throws JSchException
     * @throws SshCommandFailed
     * 
     * @throws Exception
     */
    @Test
    public void shouldApplyValidCookbook() throws NodeNotCreatedException, PrepareDeploymentFailedException, JSchException,
	    SshCommandFailed {

	String RECIPE_NAME = "getting-started";
	String CREATED_FILE = "chef-getting-started.txt";
	CloudProvider cp = new FixedCloudProvider();
	Node node = cp.createOrUseExistingNode(new NodeSpec());

	NodePoolManager npm = new NPMImpl(cp);
	int num_replicas = 1;
	DeploymentRequest config = new DeploymentRequest(RECIPE_NAME, null, num_replicas);

	List<Node> returnedNodes = npm.prepareDeployment(config);
	assertTrue(returnedNodes != null);
	assertTrue(returnedNodes.get(0).hasIp());
	assertEquals(node.getIp(), returnedNodes.get(0).getIp());

	try {
	    npm.updateNode(node.getId());
	} catch (NodeNotUpdatedException e) {
	    fail();
	} catch (NodeNotFoundException e) {
	    fail();
	}

	// verify if the file getting-started is actually there
	SshUtil ssh = new SshUtil(returnedNodes.get(0).getIp(), node.getUser(), node.getPrivateKeyFile());
	String returnText = ssh.runCommand("ls " + CREATED_FILE, true);
	assertTrue(returnText.trim().equals(CREATED_FILE));
    }

}
