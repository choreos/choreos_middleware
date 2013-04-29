package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.utils.LogConfigurator;

public class SimpleBusHandlerTest {

	private static final String EXPECTED_BUS_ADMIN_ENDPOINT_SUFFIX = ":8180/services/adminExternalEndpoint";
	private NodePoolManager npm;

	@BeforeClass
	public static void setUpClass() {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() throws ConfigNotAppliedException {

		Node node = new Node();
		node.setId("1");
		node.setIp("192.168.56.101");
		List<Node> nodes1 = new ArrayList<Node>();
		nodes1.add(node);

		node = new Node();
		node.setId("2");
		node.setIp("192.168.56.102");
		List<Node> nodes2 = new ArrayList<Node>();
		nodes2.add(node);
		
		this.npm = mock(NodePoolManager.class);
		when(this.npm.applyConfig(any(Config.class))).thenReturn(nodes1).thenReturn(nodes2);
	}
	
	@Test
	public void shouldReturnDifferentESBNodes()
			throws NoBusAvailableException {

		BusHandler handler = new SimpleBusHandler(this.npm);
		EasyESBNode retrievedBusNode1 = handler.retrieveBusNode();
		String adminEndpoint1 = retrievedBusNode1.getAdminEndpoint();
		assertTrue(adminEndpoint1.contains(EXPECTED_BUS_ADMIN_ENDPOINT_SUFFIX));

		EasyESBNode retrievedBusNode2 = handler.retrieveBusNode();
		String adminEndpoint2 = retrievedBusNode2.getAdminEndpoint();
		assertTrue(adminEndpoint2.contains(EXPECTED_BUS_ADMIN_ENDPOINT_SUFFIX));

		assertTrue(!adminEndpoint1.equals(adminEndpoint2));
	}

}
