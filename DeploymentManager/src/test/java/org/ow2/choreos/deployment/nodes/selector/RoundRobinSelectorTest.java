package org.ow2.choreos.deployment.nodes.selector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;

public class RoundRobinSelectorTest {
	
	private CloudProvider cloudProvider, cloudProvider2;
	
	@Before
	public void setup() {
		String [] ips = {"192.168.122.14", "192.168.122.160", "192.128.122.182"};
		String [] hosts = {"choreos1", "choreos2", "choreos13"};
		String [] users = {"choreos", "choreos", "choreos"};
		String [] keys = {"choreos.pem", "choreos.pem", "choreos.pem"};
		Configuration.set("FIXED_VM_IPS", ips);
		Configuration.set("FIXED_VM_HOSTNAMES", hosts);
		Configuration.set("FIXED_VM_USERS", users);
		Configuration.set("FIXED_VM_PRIVATE_SSH_KEYS", keys);
		cloudProvider = new FixedCloudProvider();
		cloudProvider2 = new FixedCloudProvider();
	}
	
	@Test
	public void test() {
		
		Configuration.set("NODE_SELECTOR", "ROUND_ROBIN");
		NodeSelector rr = NodeSelectorFactory.getInstance(cloudProvider);
		
		Config conf1 = new Config("", 1);
		Config conf2 = new Config("", 2);
		
		List<Node> a = rr.selectNodes(conf1);
		List<Node> b = rr.selectNodes(conf1);
		List<Node> c = rr.selectNodes(conf1);
		List<Node> d = rr.selectNodes(conf1);
		
		assertFalse(a.isEmpty());
		assertFalse(b.isEmpty());
		assertFalse(c.isEmpty());
		assertFalse(d.isEmpty());
		
		assertFalse(a.get(0).equals(b.get(0)));
		assertFalse(a.get(0).equals(c.get(0)));
		assertFalse(c.get(0).equals(b.get(0)));
		
		assertTrue(a.get(0).equals(d.get(0)));
		
		List<Node> aa = rr.selectNodes(conf2);
		List<Node> bb = rr.selectNodes(conf2);
		
		assertFalse(aa.isEmpty());
		assertFalse(bb.isEmpty());
		
		assertFalse(aa.get(0).equals(aa.get(1)));
		assertFalse(aa.get(1).equals(bb.get(0)));
		assertTrue(aa.get(0).equals(bb.get(1)));
		
	}
	
	@Test
	public void test2() {
		
		Configuration.set("NODE_SELECTOR", "ROUND_ROBIN");
		NodeSelector rr = NodeSelectorFactory.getInstance(cloudProvider);
		NodeSelector rr2 = NodeSelectorFactory.getInstance(cloudProvider2);
		
		Config conf1 = new Config("", 1);
		Config conf2 = new Config("", 2);
		
		List<Node> a = rr.selectNodes(conf1);
		List<Node> b = rr2.selectNodes(conf1);
		List<Node> c = rr.selectNodes(conf1);
		List<Node> d = rr2.selectNodes(conf1);
		
		assertFalse(a.get(0).equals(b.get(0)));
		assertTrue(a.get(0).equals(d.get(0)));
		assertFalse(a.get(0).equals(c.get(0)));
		assertFalse(c.get(0).equals(d.get(0)));
		assertFalse(b.get(0).equals(d.get(0)));
		
		List<Node> aa = rr.selectNodes(conf2);
		List<Node> bb = rr2.selectNodes(conf2);
		List<Node> cc = rr.selectNodes(conf2);
		
		assertTrue(aa.get(0).equals(bb.get(1)));
		assertTrue(aa.get(1).equals(cc.get(0)));
		assertTrue(bb.get(0).equals(cc.get(1)));
		assertFalse(aa.get(0).equals(aa.get(1)));
		assertFalse(bb.get(0).equals(bb.get(1)));
		assertFalse(cc.get(0).equals(cc.get(1)));
		assertFalse(aa.get(0).equals(bb.get(0)));
	
	}

}
