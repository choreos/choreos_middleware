package org.ow2.choreos.chors.integration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChorDeployerImpl;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpactDefs;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 *
 * @author tfmend, nelson
 *
 */
@Category(IntegrationTest.class)
public class VerticalScalingTest {

	private ChorSpec smallSpec;
	private ChorSpec mediumSpec;
	private ChorSpec largeSpec;
	
	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() {
		
		ModelsForTest models = new ModelsForTest(PackageType.COMMAND_LINE);
		smallSpec = models.getChorSpecWithResourceImpact(ResourceImpactDefs.MemoryTypes.SMALL);
		mediumSpec = models.getChorSpecWithResourceImpact(ResourceImpactDefs.MemoryTypes.MEDIUM);
		largeSpec = models.getChorSpecWithResourceImpact(ResourceImpactDefs.MemoryTypes.LARGE);
		
	}
	
	@Test
	public void shouldEnactChoreographyWithTwoAirlineServicesAndChangeToThree() throws Exception {
		
		ChoreographyDeployer ee = new ChorDeployerImpl();
		
		String chorId = ee.createChoreography(smallSpec);
		Choreography chor = ee.enact(chorId);


		Service airline = chor.getDeployedServiceByName(ModelsForTest.AIRLINE);
		
		Service travel = chor.getDeployedServiceByName(ModelsForTest.TRAVEL_AGENCY);
		WSClient client = new WSClient(travel.getUris().get(0) + "?wsdl");
		
		String codes, codes2, codes3 = "";
		
		Item response = client.request("buyTrip");
		codes = response.getChild("return").getContent();		
		response = client.request("buyTrip");
		codes2 = response.getChild("return").getContent();

		assertEquals(2, airline.getUris().size());
		assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
		assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
		assertFalse(codes.equals(codes2));
		
		
		
		
		
		ee.update(chorId, mediumSpec);
		chor = ee.enact(chorId);
		
		airline = chor.getDeployedServiceByName(ModelsForTest.AIRLINE);
		
		travel = chor.getDeployedServiceByName(ModelsForTest.TRAVEL_AGENCY);
		client = new WSClient(travel.getUris().get(0) + "?wsdl");

		response = client.request("buyTrip");
		codes = response.getChild("return").getContent();		
		response = client.request("buyTrip");
		codes2 = response.getChild("return").getContent();		
		response = client.request("buyTrip");
		codes3 = response.getChild("return").getContent();
		
		assertEquals(3, airline.getUris().size());
		assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
		assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
		assertTrue(codes3.startsWith("33") && codes3.endsWith("--22"));
		assertFalse(codes.equals(codes2));
		assertFalse(codes3.equals(codes));
		assertFalse(codes3.equals(codes2));
		
		
	}

}
