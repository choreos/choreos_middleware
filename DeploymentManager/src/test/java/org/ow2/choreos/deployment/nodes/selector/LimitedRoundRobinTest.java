package org.ow2.choreos.deployment.nodes.selector;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMMocks;
import org.ow2.choreos.nodes.NPMException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.LogConfigurator;

public class LimitedRoundRobinTest {

    @BeforeClass
    public static void setUpClass() {
	LogConfigurator.configLog();
    }

    @Test
    public void shouldCreateSomeVMsAndAfterRoundRobin() throws NPMException, NodeNotSelectedException {

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
    public void shouldCreateSomeVMsAndAfterRoundRobinWithDoubleInstances() throws NPMException,
	    NodeNotSelectedException {

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

    @Test
    public void shouldCreateSomeVMsAndAfterRoundRobinConcurrently() throws InterruptedException, ExecutionException,
	    NPMException {

	Configuration.set("VM_LIMIT", "3");
	NodePoolManager npm = NPMMocks.getDynamicMock();
	LimitedRoundRobin limitedRRSelector = new LimitedRoundRobin();
	Config conf = new Config("config", new ResourceImpact(), 1);

	final int N = 6;
	ExecutorService executor = Executors.newFixedThreadPool(N);
	List<CallableSelector> selectors = new ArrayList<CallableSelector>();
	List<Future<Node>> futures = new ArrayList<Future<Node>>();
	for (int i = 0; i < N; i++) {
	    CallableSelector selector = new CallableSelector(limitedRRSelector, conf, npm);
	    selectors.add(selector);
	    Future<Node> f = executor.submit(selector);
	    futures.add(f);
	}

	Concurrency.waitExecutor(executor, 1);

	int count = 0, count0 = 0, count1 = 0, count2 = 0;
	for (Future<Node> f : futures) {
	    Node node = f.get();
	    count++;
	    if (node.getId().equals("0"))
		count0++;
	    if (node.getId().equals("1"))
		count1++;
	    if (node.getId().equals("2"))
		count2++;
	}

	assertEquals(6, count);
	assertEquals(2, count0);
	assertEquals(2, count1);
	assertEquals(2, count2);
    }

    private class CallableSelector implements Callable<Node> {

	NodeSelector selector;
	Config config;
	NodePoolManager npm;

	CallableSelector(NodeSelector selector, Config config, NodePoolManager npm) {
	    this.selector = selector;
	    this.config = config;
	    this.npm = npm;
	}

	@Override
	public Node call() throws Exception {

	    List<Node> nodes = selector.selectNodes(config, npm);
	    return nodes.get(0);
	}

    }
}
