package org.ow2.choreos.deployment.nodes.selector;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMMocks;
import org.ow2.choreos.nodes.NPMException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public class LimitedRoundRobinTest {


	@Test
	public void shouldCreateSomeVMsAndAfterRoundRobin() throws NPMException, NodeNotSelectedException  {

		Configuration.set("VM_LIMIT", "3");
		NodePoolManager npm = NPMMocks.getDynamicMock();
		LimitedRoundRobin selector = new LimitedRoundRobin();
		Config conf = new Config("config", new ResourceImpact(), 1);
		assertEquals("0", selector.selectNodes(conf, npm).get(0).getId());
		assertEquals("1", selector.selectNodes(conf, npm).get(0).getId());
		assertEquals("2", selector.selectNodes(conf, npm).get(0).getId());
		assertEquals("0", selector.selectNodes(conf, npm).get(0).getId());
		assertEquals("1", selector.selectNodes(conf, npm).get(0).getId());
		assertEquals("2", selector.selectNodes(conf, npm).get(0).getId());
		assertEquals("0", selector.selectNodes(conf, npm).get(0).getId());
	}
	
	@Test
	public void shouldCreateSomeVMsAndAfterRoundRobinWithDoubleInstances() throws NPMException, NodeNotSelectedException  {

		Configuration.set("VM_LIMIT", "3");
		NodePoolManager npm = NPMMocks.getDynamicMock();
		LimitedRoundRobin selector = new LimitedRoundRobin();
		Config conf = new Config("config", new ResourceImpact(), 2);
		List<Node> nodes = selector.selectNodes(conf, npm);
		assertEquals("0", nodes.get(0).getId());
		assertEquals("1", nodes.get(1).getId());
		nodes = selector.selectNodes(conf, npm);
		assertEquals("2", nodes.get(0).getId());
		assertEquals("0", nodes.get(1).getId());
	}
}
