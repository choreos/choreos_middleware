package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.utils.LogConfigurator;

public class IdlePoolTest {

	private static final int N = 3;
	private CloudProvider cp;
	
	@BeforeClass
	public static void setUpClass() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() throws RunNodesException {
	
		cp = mock(CloudProvider.class);
		OngoingStubbing<Node> ongoingStubbing = 
				when(cp.createNode(any(Node.class), any(ResourceImpact.class)));
		for (int i=0; i<N; i++) {
			Node node = new Node();
			node.setId("node" + i);
			ongoingStubbing = ongoingStubbing.thenReturn(node);
		}
	}
	
	@Test
	public void shouldCreateExtraVMs() throws InterruptedException {
		
		IdlePool pool = IdlePool.getInstance();
		int howManyVMs = N;
		pool.createExtraVMs(howManyVMs, cp);
		
		Thread.sleep(100);
		
		Set<String> idlePool = pool.getIdleNodes();
		assertEquals(howManyVMs, idlePool.size());
	}
}
