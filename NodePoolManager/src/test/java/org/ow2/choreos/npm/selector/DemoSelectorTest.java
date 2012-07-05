package org.ow2.choreos.npm.selector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.selector.DemoSelector;
import org.ow2.choreos.npm.selector.NodeSelector;


public class DemoSelectorTest {

	private CloudProvider cloudProvider;
	private Node node1, node2;
	
	private String[] VM1 = {"serviceairline-service", "servicesa-CD-AirlineService-provide",
			"servicesa-SOAP-AirlineService-provide", "servicesa-SOAP-AirlineService-consume"};
	private String[] VM2 = {"servicehotel-service", "servicesa-CD-HotelService-provide",
			"servicesa-SOAP-HotelService-provide", "servicesa-SOAP-HotelService-consume"};
	
	@Before
	public void setUp() {
		
		node1 = new Node();
		node1.setHostname("node1");
		node2 = new Node();
		node2.setHostname("node2");
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		cloudProvider = Mockito.mock(CloudProvider.class);
		when(cloudProvider.getNodes()).thenReturn(nodes);
	}
	
	@Test
	public void test() {
		
		NodeSelector selector = new DemoSelector(this.cloudProvider);
		
		for (String svc: VM1) {
			Config config = new Config();
			config.setName(svc);
			assertEquals(node1, selector.selectNode(config));
		}

		for (String svc: VM2) {
			Config config = new Config();
			config.setName(svc);
			assertEquals(node2, selector.selectNode(config));
		}
		
		Config config = new Config();
		config.setName("other");
		assertEquals(node1, selector.selectNode(config));
	}
}
