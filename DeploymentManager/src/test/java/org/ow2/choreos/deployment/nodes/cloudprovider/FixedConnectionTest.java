package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.jclouds.compute.RunNodesException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.SshUtil;

/**
 * Please, before running these tests, read the README file
 * 
 * @author leonardo
 */
@Category(IntegrationTest.class)
public class FixedConnectionTest {

	@Test
	public void shouldConnectToTheNode() throws RunNodesException {

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(new Node(), new ResourceImpact());

		SshUtil ssh = null;
		ssh = new SshUtil(node.getIp(), node.getUser(),
				node.getPrivateKeyFile());
		assertTrue(ssh.isAccessible());
		ssh.disconnect();
	}

}
