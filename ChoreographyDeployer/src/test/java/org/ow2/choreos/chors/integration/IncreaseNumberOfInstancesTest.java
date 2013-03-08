package org.ow2.choreos.chors.integration;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChorDeployerImpl;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test will enact a choreography with two services.
 * One of them will serve with two replicas
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer
 *
 * @author tfmend
 *
 */
@Category(IntegrationTest.class)
public class IncreaseNumberOfInstancesTest {

	private ChorSpec spec;
	private ChorSpec newSpec;
	
	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() {
		
		ModelsForTest models = new ModelsForTest(PackageType.COMMAND_LINE);
		spec = models.getChorSpecWithReplicas(2);
		newSpec = models.getChorSpecWithReplicas(3);
	}
	
	@Test
	public void shouldEnactChoreographyWithTwoAirlineServices() throws Exception {
		
		ChoreographyDeployer ee = new ChorDeployerImpl();
		
		String chorId = ee.createChoreography(spec);
		Choreography chor = ee.enact(chorId);


		Service airline = chor.getDeployedServiceByName(ModelsForTest.AIRLINE);
		
		assertEquals(2, airline.getUris().size());
		
		Service travel = chor.getDeployedServiceByName(ModelsForTest.TRAVEL_AGENCY);
		
		
		WSClient client = new WSClient(travel.getUris().get(0) + "?wsdl");
		Item response = client.request("buyTrip");
		String codes = response.getChild("return").getContent();
		assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
		
		WSClient client2 = new WSClient(travel.getUris().get(0) + "?wsdl");
		Item response2 = client2.request("buyTrip");
		String codes2 = response2.getChild("return").getContent();
		assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));

		assertFalse(codes.equals(codes2));
		
		ee.update(chorId, newSpec);
		chor = ee.enact(chorId);
		
		WSClient client3 = new WSClient(travel.getUris().get(0) + "?wsdl");
		Item response3 = client3.request("buyTrip");
		String codes3 = response3.getChild("return").getContent();
		assertTrue(codes3.startsWith("33") && codes3.endsWith("--22"));
		
		assertFalse(codes3.equals(codes2));
		assertFalse(codes3.equals(codes));
		
		assertEquals(3, airline.getUris().size());
		
	}

}
