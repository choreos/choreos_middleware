package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;
import org.ow2.choreos.utils.SshUtil;


/**
 * Please, before running these tests, read the README file
 * 
 * @author leonardo
 */
@Category(IntegrationTest.class)
public class FixedCloudProviderTest {

	@Before
	public void setUp() {
		LogConfigurator.configLog();
	}

	@Test
	public void shouldReturnNodeInfo() throws RunNodesException {

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(new Node());

		assertTrue(node.getHostname() != null && !node.getHostname().isEmpty());

		Pattern pat = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
		Matcher matcher = pat.matcher(node.getIp());
		assertTrue(matcher.matches());
	}

	@Test
	public void shouldConnectToTheNode() throws RunNodesException {

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(new Node());

		SshUtil ssh = null;
		ssh = new SshUtil(node.getIp(), node.getUser(),
				node.getPrivateKeyFile());
		assertTrue(ssh.isAccessible());
		ssh.disconnect();
	}
}
