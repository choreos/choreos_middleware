package org.ow2.choreos.npm.cloudprovider;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.utils.LogConfigurator;
import org.ow2.choreos.npm.utils.SshUtil;

import com.jcraft.jsch.JSchException;


/**
 * Please, before running these tests, read the README file
 * 
 * @author leonardo
 */
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

		try {
			SshUtil ssh = new SshUtil(node.getIp(), node.getUser(),
					node.getPrivateKeyFile());
			assertTrue(ssh.isAccessible());
			ssh.disconnect();
		} catch (JSchException e) {
			System.out.println("Could not connect! " + e);
			fail();
		}
	}
}
