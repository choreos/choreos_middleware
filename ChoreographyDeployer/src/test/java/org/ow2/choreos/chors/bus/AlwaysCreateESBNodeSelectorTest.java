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
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;

public class AlwaysCreateESBNodeSelectorTest {

	private static final String ESB_ADMIN_ENDPOINT1 = "http://192.168.56.101:8180/services/adminExternalEndpoint";
	private static final String ESB_ADMIN_ENDPOINT2 = "http://192.168.56.102:8180/services/adminExternalEndpoint";

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
			this.serviceInstancesN += ((DeployedService) svc.getService())
					.getInstances().size();
		}

		EasyESBNode esbNode1 = new EasyESBNodeImpl(ESB_ADMIN_ENDPOINT1);
		EasyESBNode esbNode2 = new EasyESBNodeImpl(ESB_ADMIN_ENDPOINT2);
		this.busHandler = mock(BusHandler.class);
		when(this.busHandler.retrieveBusNode()).thenReturn(esbNode1)
				.thenReturn(esbNode2);
	}

	@Test
	public void shouldSelectDifferentNodes() {

		ESBNodesSelector selector = new AlwaysCreateESBNodeSelector(
				this.busHandler);
		Map<ServiceInstance, EasyESBNode> nodes = selector
				.selectESBNodes(this.chor);

		assertEquals(serviceInstancesN, nodes.size());

		Iterator<ServiceInstance> it = nodes.keySet().iterator();
		String firstAdminEndpoint = nodes.get(it.next()).getAdminEndpoint();
		assertTrue(firstAdminEndpoint
				.contains(":8180/services/adminExternalEndpoint"));

		String secondAdminEndpoint = nodes.get(it.next()).getAdminEndpoint();
		assertTrue(secondAdminEndpoint
				.contains(":8180/services/adminExternalEndpoint"));
		assertTrue(!firstAdminEndpoint.equals(secondAdminEndpoint));
	}
}