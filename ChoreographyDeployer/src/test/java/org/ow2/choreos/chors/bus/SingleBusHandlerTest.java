package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.LogConfigurator;

public class SingleBusHandlerTest {
	
	private static final String NODE_IP = "192.168.56.101";
	private static final String EXPECTED_BUS_ADMIN_ENDPOINT = 
			"http://" + NODE_IP + ":8180/services/adminExternalEndpoint";
	private NodePoolManager npm;

	@BeforeClass
	public static void setUpClass() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() throws ConfigNotAppliedException {

		Node node = new Node();
		node.setId("1");
		node.setIp(NODE_IP);
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		this.npm = mock(NodePoolManager.class);
		when(this.npm.applyConfig(any(Config.class))).thenReturn(nodes);
	}
	
	@Test
	public void shouldAlwaysReturnTheSameESBNode() throws NoBusAvailableException {
		
		BusHandler handler = new SingleBusHandler(this.npm);
		EasyESBNode retrievedBusNode = handler.retrieveBusNode();
		assertEquals(EXPECTED_BUS_ADMIN_ENDPOINT, retrievedBusNode.getAdminEndpoint());

		retrievedBusNode = handler.retrieveBusNode();
		assertEquals(EXPECTED_BUS_ADMIN_ENDPOINT, retrievedBusNode.getAdminEndpoint());
	}

	@Test
	public void shouldConcurrentlyAlwaysReturnTheSameESBNode() throws NoBusAvailableException, InterruptedException, ExecutionException {
		
		final int N = 3;
		ExecutorService executor = Executors.newFixedThreadPool(N);
		BusHandler handler = new SingleBusHandler(this.npm, 1);
		
		List<Future<EasyESBNode>> futures = new ArrayList<Future<EasyESBNode>>(); 
		for (int i=0; i<N; i++) {
			Retriever retriever = new Retriever(handler);
			Future<EasyESBNode> f = executor.submit(retriever);
			futures.add(f);
		}
		
		final int ONE_MINUTE = 1;
		Concurrency.waitExecutor(executor, ONE_MINUTE);
		
		for (Future<EasyESBNode> f: futures) {
			EasyESBNode esbNode = f.get();
			assertEquals(EXPECTED_BUS_ADMIN_ENDPOINT, esbNode.getAdminEndpoint());
		}
	}
	
	private class Retriever implements Callable<EasyESBNode> {

		BusHandler handler;
		
		public Retriever(BusHandler handler) {
			this.handler = handler;
		}
		
		@Override
		public EasyESBNode call() throws Exception {
			return handler.retrieveBusNode();
		}
		
	}

}
