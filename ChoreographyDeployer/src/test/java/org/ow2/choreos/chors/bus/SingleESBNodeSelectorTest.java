package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;

public class SingleESBNodeSelectorTest {

	private static final String ESB_ADMIN_ENDPOINT = "http://192.168.56.101:8180/services/adminExternalEndpoint";

	private Choreography chor;
	private int serviceInstancesN;
	private BusHandler busHandler;

	@BeforeClass
	public static void tearDownAfterClass() throws Exception {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() throws NoBusAvailableException {

		ModelsForTest models = new ModelsForTest(ServiceType.SOAP,
				PackageType.COMMAND_LINE);
		this.chor = models.getChoreography();
		this.serviceInstancesN = 0;
		for (ChoreographyService svc : this.chor.getChoreographyServices()) {
			this.serviceInstancesN += ((DeployableService) (svc.getService()))
					.getInstances().size();
		}

		EasyESBNode esbNode = new EasyESBNodeImpl(ESB_ADMIN_ENDPOINT);
		this.busHandler = mock(BusHandler.class);
		when(this.busHandler.retrieveBusNode()).thenReturn(esbNode);
	}

	@Test
	public void shouldSelectAwaysTheSameNode() {

		ESBNodesSelector selector = new SingleESBNodeSelector(this.busHandler);
		Map<ServiceInstance, EasyESBNode> nodes = selector
				.selectESBNodes(this.chor);

		assertEquals(serviceInstancesN, nodes.size());

		Iterator<ServiceInstance> it = nodes.keySet().iterator();
		String firstAdminEndpoint = nodes.get(it.next()).getAdminEndpoint();
		assertTrue(firstAdminEndpoint
				.contains(":8180/services/adminExternalEndpoint"));

		for (EasyESBNode esbNode : nodes.values()) {
			assertEquals(firstAdminEndpoint, esbNode.getAdminEndpoint());
		}
	}

}
