package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChorDeployerImpl;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test will enact a choreography with two services,
 * with a service depending on the other (like SimpleChorEnactment),
 * but using the bus to integrate the services.
 * 
 * Before the test, start the DeploymentManager server
 *
 * @author leonardo, tfmend, nelson
 *
 */
@Category(IntegrationTest.class)
public class ChorEnactmentWithBusTest {

	private ChorSpec chorSpec;
	
	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() {
		
		Configuration.set(Option.BUS, "true");
		ModelsForTest models = new ModelsForTest(PackageType.COMMAND_LINE);
		chorSpec = models.getChorSpec(); 
	}
	
	@Test
	public void shouldEnactChoreography() throws Exception {
		
		ChoreographyDeployer ee = new ChorDeployerImpl();
		
		String chorId = ee.createChoreography(chorSpec);
		Choreography chor = ee.enact(chorId);

		Service travel = chor.getDeployedServiceByName(ModelsForTest.TRAVEL_AGENCY);
		ServiceInstance airlineInstance = chor.getDeployedServiceByName(ModelsForTest.AIRLINE).getInstances().get(0);
		ServiceInstance travelInstance = travel.getInstances().get(0);
		String airlineProxifiedUri = airlineInstance.getBusUri(ServiceType.SOAP);
		String travelProxifiedUri = travelInstance.getBusUri(ServiceType.SOAP);
		System.out.println("airline proxified: " + airlineProxifiedUri);
		System.out.println("travel agency proxified: " + travelProxifiedUri);
		assertNotNull(airlineProxifiedUri);
		assertNotNull(travelProxifiedUri);
		assertTrue(airlineProxifiedUri.contains(":8180/services/AirlineServicePortClientProxyEndpoint"));
		assertTrue(travelProxifiedUri.contains(":8180/services/TravelAgencyServicePortClientProxyEndpoint"));
		
		WSClient client = new WSClient(travelProxifiedUri + "?wsdl");
		Item response = client.request("buyTrip");
		String codes = response.getChild("return").getContent();
		assertTrue(codes.startsWith("33") && codes.endsWith("--22"));

	}
}
