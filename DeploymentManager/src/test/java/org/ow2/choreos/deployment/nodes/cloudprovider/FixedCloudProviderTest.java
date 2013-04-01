package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.utils.LogConfigurator;



public class FixedCloudProviderTest {

	@Before
	public void setUp() {
		LogConfigurator.configLog();
	}

	@Test
	public void shouldReturnNodeInfo() throws RunNodesException {

		Configuration.set("FIXED_VM_IPS", "192.168.56.101");
		Configuration.set("FIXED_VM_HOSTNAMES", "choreos");
		Configuration.set("FIXED_VM_PRIVATE_SSH_KEYS", "key");
		Configuration.set("FIXED_VM_USERS", "choreos");
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null, null);

		assertTrue(node.getHostname() != null && !node.getHostname().isEmpty());

		Pattern pat = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
		Matcher matcher = pat.matcher(node.getIp());
		assertTrue(matcher.matches());
	}
	
	@Test
	public void shouldReturnAvalableVMs() {
		
		Configuration.set("FIXED_VM_IPS", "192.168.56.101, 192.168.56.102");
		Configuration.set("FIXED_VM_HOSTNAMES", "choreos, choreos");
		Configuration.set("FIXED_VM_PRIVATE_SSH_KEYS", "key, samekey");
		Configuration.set("FIXED_VM_USERS", "choreos, choreos");

		CloudProvider cp = new FixedCloudProvider();
		List<Node> nodes = cp.getNodes();
		assertEquals(2, nodes.size());
		
		Node node0 = nodes.get(0);
		Node node1 = nodes.get(1);
		
		assertTrue(node0.getId().equals("0") || node0.getId().equals("1"));
		assertTrue(node1.getId().equals("0") || node1.getId().equals("1"));
		assertTrue(!node0.getId().equals(node1.getId()));

		assertTrue(node0.getIp().equals("192.168.56.101") || node0.getIp().equals("192.168.56.102"));
		assertTrue(node1.getIp().equals("192.168.56.101") || node1.getIp().equals("192.168.56.102"));
		assertTrue(!node0.getIp().equals(node1.getIp()));
	}
	
	@Test(expected=NodeNotFoundException.class)
	public void shouldNotFindVMs() throws NodeNotFoundException {
		
		Configuration.set("FIXED_VM_IPS", "192.168.56.101; 192.168.56.102 ");
		Configuration.set("FIXED_VM_HOSTNAMES", "choreos, choreos");
		Configuration.set("FIXED_VM_PRIVATE_SSH_KEYS", "key, samekey");
		Configuration.set("FIXED_VM_USERS", "choreos, choreos");		
		CloudProvider cp = new FixedCloudProvider();
		cp.getNode("2");
	}


}
