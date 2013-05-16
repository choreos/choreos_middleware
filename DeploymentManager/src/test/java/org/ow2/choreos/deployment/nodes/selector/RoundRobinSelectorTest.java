package org.ow2.choreos.deployment.nodes.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMMocks;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.ResourceImpactDefs.MemoryTypes;

public class RoundRobinSelectorTest {
	
	private NodePoolManager npm1, npm2;
	
	@Before
	public void setup() throws NodeNotFoundException {
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
		
	    npm1 = NPMMocks.getMock();
	    npm2 = NPMMocks.getMock();
	}
	
	@Test
	public void shouldWorkWithOneSelector() throws NodeNotSelectedException {
		
		RoundRobinSelector rr = new RoundRobinSelector();
		
		Config conf1 = new Config("", null, 1);
		Config conf2 = new Config("", null, 2);
		
		List<Node> a = rr.selectNodes(conf1, npm1);
		List<Node> b = rr.selectNodes(conf1, npm1);
		List<Node> c = rr.selectNodes(conf1, npm1);
		List<Node> d = rr.selectNodes(conf1, npm1);
		
		assertFalse(a.isEmpty());
		assertFalse(b.isEmpty());
		assertFalse(c.isEmpty());
		assertFalse(d.isEmpty());
		
		assertFalse(a.get(0).equals(b.get(0)));
		assertFalse(a.get(0).equals(c.get(0)));
		assertFalse(c.get(0).equals(b.get(0)));
		
		assertTrue(a.get(0).equals(d.get(0)));
		
		List<Node> aa = rr.selectNodes(conf2, npm1);
		List<Node> bb = rr.selectNodes(conf2, npm1);
		
		assertFalse(aa.isEmpty());
		assertFalse(bb.isEmpty());
		
		assertFalse(aa.get(0).equals(aa.get(1)));
		assertFalse(aa.get(1).equals(bb.get(0)));
		assertTrue(aa.get(0).equals(bb.get(1)));
		
	}
	
	@Test
	public void shouldWorkWithTwoSelectors() throws NodeNotSelectedException {
		
		Configuration.set("NODE_SELECTOR", "ROUND_ROBIN");
		NodeSelector rr = NodeSelectorFactory.getInstance();
		NodeSelector rr2 = NodeSelectorFactory.getInstance();
		// r1 and rr2 are the same instance
		
		Config conf1 = new Config("", null, 1);
		Config conf2 = new Config("", null, 2);
		
		List<Node> a = rr.selectNodes(conf1, npm1);
		List<Node> b = rr2.selectNodes(conf1, npm2);
		List<Node> c = rr.selectNodes(conf1, npm1);
		List<Node> d = rr2.selectNodes(conf1, npm2);
		
		assertFalse(a.get(0).equals(b.get(0)));
		assertTrue(a.get(0).equals(d.get(0)));
		assertFalse(a.get(0).equals(c.get(0)));
		assertFalse(c.get(0).equals(d.get(0)));
		assertFalse(b.get(0).equals(d.get(0)));
		
		List<Node> aa = rr.selectNodes(conf2, npm1);
		List<Node> bb = rr2.selectNodes(conf2, npm2);
		List<Node> cc = rr.selectNodes(conf2, npm1);
		
		assertTrue(aa.get(0).equals(bb.get(1)));
		assertTrue(aa.get(1).equals(cc.get(0)));
		assertTrue(bb.get(0).equals(cc.get(1)));
		assertFalse(aa.get(0).equals(aa.get(1)));
		assertFalse(bb.get(0).equals(bb.get(1)));
		assertFalse(cc.get(0).equals(cc.get(1)));
		assertFalse(aa.get(0).equals(bb.get(0)));
	
	}
	
	@Test
	public void shouldChooseCorrectlyTypedVM() throws NodeNotSelectedException {
		
		RoundRobinSelector rr = new RoundRobinSelector();
		
		ResourceImpact res_imp1 = new ResourceImpact();
		res_imp1.setMemory(MemoryTypes.SMALL);
		
		ResourceImpact res_imp2 = new ResourceImpact();
		res_imp2.setMemory(MemoryTypes.MEDIUM);
		
		ResourceImpact res_imp4 = new ResourceImpact();
		
		Config conf1 = new Config("", res_imp1, 2);
		Config conf2 = new Config("", res_imp2, 1);
		Config conf4 = new Config("", res_imp4, 3);
		
		List<Node> a = rr.selectNodes(conf1, npm1);
		assertEquals(2, a.size());
		assertEquals(256, a.get(0).getRam().intValue());
		assertEquals(256, a.get(1).getRam().intValue());
		
		List<Node> b = rr.selectNodes(conf2, npm1);
		assertEquals(1, b.size());
		assertTrue(b.get(0).getRam() == 512);
		
		List<Node> d = rr.selectNodes(conf4, npm1);
		assertEquals(3, d.size());
	}
	
	@Test(expected=NodeNotSelectedException.class)
	public void shouldNotSelectNode() throws NodeNotSelectedException {
		
		Configuration.set("NODE_SELECTOR", "ROUND_ROBIN");
		NodeSelector rr = NodeSelectorFactory.getInstance();

		ResourceImpact res_imp3 = new ResourceImpact();
		res_imp3.setMemory(MemoryTypes.LARGE);
		Config conf3 = new Config("", res_imp3, 1);

		rr.selectNodes(conf3, npm1); 
	}

}
