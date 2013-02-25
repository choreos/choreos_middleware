package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.ServiceDependency;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;


/**
 * To run this test you must before
 * start the airline and travel-agency services
 * provided within the samples/EnactmentEngineServices project
 * on localhost.
 * 
 * You must not also call the travel agency setInvocationAddress
 * operation before run the test
 * 
 * @author leonardo
 *
 */
@Category(IntegrationTest.class)
public class ContextCasterTest {

	private static final String AIRLINE = "airline";
	private static final String TRAVEL_AGENCY = "travelagency";	
	private static final String AIRLINE_URI = "http://localhost:1234/airline";
	private static final String TRAVEL_AGENCY_URI = "http://localhost:1235/travelagency";	
	
	private ChorSpec chorSpec; 
	private Map<String, Service> deployedServices;
	
	@BeforeClass
	public static void configLog() {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() {
		
		chorSpec = new ChorSpec(); 
		deployedServices = new HashMap<String, Service>();
		
		ChorServiceSpec airline = new ChorServiceSpec();
		airline.setName(AIRLINE);
		airline.getRoles().add(AIRLINE);
		chorSpec.addChorServiceSpec(airline);
		
		ChorServiceSpec travel = new ChorServiceSpec();
		travel.setName(TRAVEL_AGENCY);
		travel.getRoles().add(TRAVEL_AGENCY);
		ServiceDependency dep = new ServiceDependency(AIRLINE, AIRLINE);
		travel.getDependencies().add(dep);
		chorSpec.addChorServiceSpec(travel);
		
		Service airlineServ = new Service();
		airlineServ.setName(AIRLINE);
	//	airlineServ.setUri(AIRLINE_URI);
		deployedServices.put(AIRLINE, airlineServ);

		Service travelServ = new Service();
		travelServ.setName(TRAVEL_AGENCY);
	//	travelServ.setUri(TRAVEL_AGENCY_URI);
		deployedServices.put(TRAVEL_AGENCY, travelServ);
	}
	
	
	@Test
	public void test() throws WSDLException, XmlException, IOException, FrameworkException, InvalidOperationNameException, NoSuchFieldException {
		
		checkPreCondition();
		
		ContextCaster caster = new ContextCaster();
		caster.cast(chorSpec, deployedServices);
		
		WSClient travel = new WSClient(TRAVEL_AGENCY_URI + "?wsdl");
		Item response = travel.request("buyTrip");
		String codes = response.getChild("return").getContent();
		
		assertEquals("33--22", codes);
	}

	private void checkPreCondition() throws WSDLException, XmlException, IOException, FrameworkException, InvalidOperationNameException, NoSuchFieldException {

		WSClient travel = new WSClient(TRAVEL_AGENCY_URI + "?wsdl");
		Item response = travel.request("buyTrip");
		String codes = response.getChild("return").getContent();
		
		assertEquals("Not possible to buy now", codes);
	}

}
