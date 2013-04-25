package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import org.ow2.choreos.ee.api.ChoreographySpec;
import org.ow2.choreos.ee.api.PackageType;
import org.ow2.choreos.ee.api.ServiceType;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test will enact a choreography with two services. One of them will serve
 * with two replicas
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer
 * 
 * @author tfmend
 * 
 */
@Category(IntegrationTest.class)
public class IncreaseNumberOfInstancesTest {

	private ChoreographySpec spec;
	private ChoreographySpec newSpec;

	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() {

		ModelsForTest models = new ModelsForTest(ServiceType.SOAP,
				PackageType.COMMAND_LINE);
		spec = models.getChorSpecWithReplicas(2);
		newSpec = models.getChorSpecWithReplicas(3);
	}

	@Test
	public void shouldEnactChoreographyWithTwoAirlineServicesAndChangeToThree()
			throws Exception {

		ChoreographyDeployer ee = new ChoreographyDeployerImpl();

		String chorId = ee.createChoreography(spec);
		Choreography chor = ee.enactChoreography(chorId);
		
		ChoreographyService airline = chor
				.getDeployedChoreographyServiceByChoreographyServiceUID(ModelsForTest.AIRLINE);

		ChoreographyService travel = chor
				.getDeployedChoreographyServiceByChoreographyServiceUID(ModelsForTest.TRAVEL_AGENCY);
		WSClient client = new WSClient(travel.getService().getUris().get(0)
				+ "?wsdl");

		String codes, codes2, codes3 = "";

		
		Item response = client.request("buyTrip");
		codes = response.getChild("return").getContent();
		response = client.request("buyTrip");
		codes2 = response.getChild("return").getContent();

		assertEquals(2, airline.getService().getUris().size());
		assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
		assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
		assertFalse(codes.equals(codes2));
		

		ee.updateChoreography(chorId, newSpec);
		chor = ee.enactChoreography(chorId);

		Thread.sleep(4000);

		airline = chor
				.getDeployedChoreographyServiceByChoreographyServiceUID(ModelsForTest.AIRLINE);

		travel = chor
				.getDeployedChoreographyServiceByChoreographyServiceUID(ModelsForTest.TRAVEL_AGENCY);
		
		client = new WSClient(travel.getService().getUris().get(0) + "?wsdl");

		response = client.request("buyTrip");
		codes = response.getChild("return").getContent();
		response = client.request("buyTrip");
		codes2 = response.getChild("return").getContent();
		response = client.request("buyTrip");
		codes3 = response.getChild("return").getContent();

		assertEquals(3, airline.getService().getUris().size());
		assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
		assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
		assertTrue(codes3.startsWith("33") && codes3.endsWith("--22"));
		assertFalse(codes.equals(codes2));
		assertFalse(codes3.equals(codes));
		assertFalse(codes3.equals(codes2));

	}

}
