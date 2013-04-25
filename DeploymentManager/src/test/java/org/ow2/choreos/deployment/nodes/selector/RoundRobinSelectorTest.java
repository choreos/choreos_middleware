package org.ow2.choreos.deployment.nodes.selector;

import static org.junit.Assert.assertEquals;
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
import org.ow2.choreos.ee.api.ResourceImpact;
import org.ow2.choreos.ee.api.ResourceImpactDefs.MemoryTypes;

public class RoundRobinSelectorTest {
	
	private CloudProvider cloudProvider, cloudProvider2;
	
	@Before
	public void setup() {
		String [] ips = {"192.168.122.14", "192.168.122.160", "192.128.122.182"};
		String [] hosts = {"choreos1", "choreos2", "choreos13"};
		String [] users = {"choreos", "choreos", "choreos"};
		String [] keys = {"choreos.pem", "choreos.pem", "choreos.pem"};
		String [] types = {"SMALL", "SMALL", "MEDIUM"};
		Configuration.set("MAPPER_POLICY", "EXACT_FIT");
		Configuration.set("FIXED_VM_IPS", ips);
		Configuration.set("FIXED_VM_HOSTNAMES", hosts);
		Configuration.set("FIXED_VM_USERS", users);
		Configuration.set("FIXED_VM_PRIVATE_SSH_KEYS", keys);
	    Configuration.set("FIXED_VM_TYPES", types);
		cloudProvider = new FixedCloudProvider();
		cloudProvider2 = new FixedCloudProvider();
	}
	
	@Test
	public void shouldWorkWithOneSelector() {
		
		Configuration.set("NODE_SELECTOR", "ROUND_ROBIN");
		NodeSelector rr = NodeSelectorFactory.getInstance(cloudProvider);
		
		Config conf1 = new Config("", null, 1);
		Config conf2 = new Config("", null, 2);
		
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
	public void shouldWorkWithTwoSelectors() {
		
		Configuration.set("NODE_SELECTOR", "ROUND_ROBIN");
		NodeSelector rr = NodeSelectorFactory.getInstance(cloudProvider);
		NodeSelector rr2 = NodeSelectorFactory.getInstance(cloudProvider2);
		
		Config conf1 = new Config("", null, 1);
		Config conf2 = new Config("", null, 2);
		
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
	
	@Test
	public void shouldChooseCorrectlyTypedVM() {
		Configuration.set("NODE_SELECTOR", "ROUND_ROBIN");
		NodeSelector rr = NodeSelectorFactory.getInstance(cloudProvider);
		
		ResourceImpact res_imp1 = new ResourceImpact();
		res_imp1.setMemory(MemoryTypes.SMALL);
		
		ResourceImpact res_imp2 = new ResourceImpact();
		res_imp2.setMemory(MemoryTypes.MEDIUM);
		
		ResourceImpact res_imp3 = new ResourceImpact();
		res_imp3.setMemory(MemoryTypes.LARGE);

		ResourceImpact res_imp4 = new ResourceImpact();
		
		Config conf1 = new Config("", res_imp1, 2);
		Config conf2 = new Config("", res_imp2, 1);
		Config conf3 = new Config("", res_imp3, 1);
		Config conf4 = new Config("", res_imp4, 3);
		
		List<Node> a = rr.selectNodes(conf1);
		assertEquals(2, a.size());
		assertEquals(256, a.get(0).getRam().intValue());
		assertEquals(256, a.get(1).getRam().intValue());
		
		List<Node> b = rr.selectNodes(conf2);
		assertEquals(1, b.size());
		assertTrue(b.get(0).getRam() == 512);
		
		List<Node> c = rr.selectNodes(conf3);
		assertTrue(c.size() == 0);

		List<Node> d = rr.selectNodes(conf4);
		assertEquals(3, d.size());
	}

}
