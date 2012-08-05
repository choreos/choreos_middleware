package org.ow2.choreos.enactment;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.enactment.client.EnactEngClient;
import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.enactment.rest.EnactEngServer;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * It is the same than SimpleEnactmentTest, but using the REST API. 
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer.
 * 
 * This test will enact a choreography with two services,
 * with a service depending on the other.
 *
 * @author leonardo
 *
 */
public class RestEnactmentTest {

	private static final String AIRLINE = "airline";
	private static final String TRAVEL_AGENCY = "travelagency";	
	private static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/enact_test/airline-service.jar";
	private static final String TRAVEL_AGENCY_JAR = "http://valinhos.ime.usp.br:54080/enact_test/travel-agency-service.jar";	
	private static final int AIRLINE_PORT = 1234;
	private static final int TRAVEL_AGENCY_PORT = 1235;	
	
	private Choreography chorSpec;
	
	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
		EnactEngServer.start();
	}
	
	@AfterClass
	public static void shutDownServers() {
		EnactEngServer.stop();
	}
	
	@Before
	public void setUp() {
		
		chorSpec = new Choreography(); 
		
		ChorService airline = new ChorService();
		airline.setName(AIRLINE);
		airline.setCodeUri(AIRLINE_JAR);
		airline.setEndpointName(AIRLINE);
		airline.setPort(AIRLINE_PORT);
		airline.setType(ServiceType.JAR);
		airline.getRoles().add(AIRLINE);
		chorSpec.addService(airline);
		
		ChorService travel = new ChorService();
		travel.setName(TRAVEL_AGENCY);
		travel.setCodeUri(TRAVEL_AGENCY_JAR);
		travel.setEndpointName(TRAVEL_AGENCY);
		travel.setPort(TRAVEL_AGENCY_PORT);
		travel.setType(ServiceType.JAR);
		travel.getRoles().add(TRAVEL_AGENCY);
		ServiceDependence dep = new ServiceDependence(AIRLINE, AIRLINE);
		travel.getDependences().add(dep);
		chorSpec.addService(travel);
	}
	
	@Test
	public void shouldEnactChoreography() throws Exception {
		
		String host = EnactEngServer.URL;
		EnactmentEngine ee = new EnactEngClient(host);
		Map<String, Service> deployedServices = ee.enact(chorSpec);
		
		Service travel = deployedServices.get(TRAVEL_AGENCY);
		WSClient client = new WSClient(travel.getUri() + "?wsdl");
		Item response = client.request("buyTrip");
		String codes = response.getChild("return").getContent();
		
		assertEquals("33--22", codes);
	}
}
