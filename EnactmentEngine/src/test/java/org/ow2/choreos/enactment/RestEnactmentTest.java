package org.ow2.choreos.enactment;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.enactment.client.EnactEngClient;
import org.ow2.choreos.enactment.datamodel.ChorServiceSpec;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
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
	
	private ChorSpec chorSpec;
	
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
		
		chorSpec = new ChorSpec(); 
		
		ChorServiceSpec airline = new ChorServiceSpec();
		airline.setName(AIRLINE);
		airline.setCodeUri(AIRLINE_JAR);
		airline.setEndpointName(AIRLINE);
		airline.setPort(AIRLINE_PORT);
		airline.setType(ServiceType.COMMAND_LINE);
		airline.getRoles().add(AIRLINE);
		chorSpec.addServiceSpec(airline);
		
		ChorServiceSpec travel = new ChorServiceSpec();
		travel.setName(TRAVEL_AGENCY);
		travel.setCodeUri(TRAVEL_AGENCY_JAR);
		travel.setEndpointName(TRAVEL_AGENCY);
		travel.setPort(TRAVEL_AGENCY_PORT);
		travel.setType(ServiceType.COMMAND_LINE);
		travel.getRoles().add(TRAVEL_AGENCY);
		ServiceDependence dep = new ServiceDependence(AIRLINE, AIRLINE);
		travel.getDependences().add(dep);
		chorSpec.addServiceSpec(travel);
	}
	
	@Test
	public void shouldConfigureAChoreography() throws Exception {
		
		String host = EnactEngServer.URL;
		EnactmentEngine ee = new EnactEngClient(host);
		String chorId = ee.createChoreography(chorSpec);

		assertEquals("1", chorId);
	}
	
	@Test
	public void shouldRetrieveChoreographySpec() throws Exception {
		
		String host = EnactEngServer.URL;
		EnactmentEngine ee = new EnactEngClient(host);
		String chorId = ee.createChoreography(chorSpec);
		Choreography chor = ee.getChoreography(chorId);
		
		ChorServiceSpec travel = chor.getChorSpec().getServiceSpecByName(TRAVEL_AGENCY);
		assertEquals(chorSpec.getServiceSpecByName(TRAVEL_AGENCY), travel);
		
		ChorServiceSpec airline = chor.getChorSpec().getServiceSpecByName(AIRLINE);
		assertEquals(chorSpec.getServiceSpecByName(AIRLINE), airline);		
	}
	
	@Test
	public void shouldEnactChoreography() throws Exception {
		
		String host = EnactEngServer.URL;
		EnactmentEngine ee = new EnactEngClient(host);
		String chorId = ee.createChoreography(chorSpec);
		Choreography chor = ee.enact(chorId);
		
		Service travel = chor.getDeployedServiceByName(TRAVEL_AGENCY);
		String uri = travel.getUri();
		WSClient client = new WSClient(uri + "?wsdl");
		Item response = client.request("buyTrip");
		String codes = response.getChild("return").getContent();
		
		assertEquals("33--22", codes);
	}
	
}
