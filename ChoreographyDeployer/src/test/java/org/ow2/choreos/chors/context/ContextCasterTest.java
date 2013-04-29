package org.ow2.choreos.chors.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * 
 * @author leonardo
 *
 */
public class ContextCasterTest {

	private static final String AIRLINE = ModelsForTest.AIRLINE;
	private static final String TRAVEL_AGENCY = ModelsForTest.TRAVEL_AGENCY;	
	private static final String AIRLINE_URI = ModelsForTest.AIRLINE_URI;
	private static final String AIRLINE_PROXIFIED_URI = "http://localhost:8180/services/AirlineServicePortClientProxyEndpoint";
	private static final String TRAVEL_AGENCY_URI = ModelsForTest.TRAVEL_AGENCY_URI;	
	private static final String TRAVEL_AGENCY_PROXIFIED_URI = "http://localhost:8180/services/TravelAgencyServicePortClientProxyEndpoint";
	
	private Map<String, ChoreographyService> deployedServices;
	
	ModelsForTest models;
	private ChoreographyService travelChoreographyService;
	private ChoreographyService airlineChoreographyService;
	
	@BeforeClass
	public static void configLog() {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() {
		models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
		travelChoreographyService = models.getTravelChoreographyService();
		airlineChoreographyService = models.getAirlineChoreographyService();
		
		this.deployedServices = new HashMap<String, ChoreographyService>();
		this.deployedServices.put(AIRLINE, airlineChoreographyService);
		this.deployedServices.put(TRAVEL_AGENCY, travelChoreographyService);
	}

	private void setUpBusUris() throws ServiceInstanceNotFoundException {
	
		ServiceInstance airlineInstance = ((DeployableService)(deployedServices.get(AIRLINE).getService())).getInstances().get(0); 
		airlineInstance.setBusUri(ServiceType.SOAP, AIRLINE_PROXIFIED_URI);
		ServiceInstance travelInstance = ((DeployableService)(deployedServices.get(TRAVEL_AGENCY).getService())).getInstances().get(0);
		travelInstance.setBusUri(ServiceType.SOAP, TRAVEL_AGENCY_PROXIFIED_URI);
	}
	
	@Test
	public void shouldPassAirlineProxifiedAddressToTravelAgency() throws ContextNotSentException, ServiceInstanceNotFoundException {
		
		this.setUpBusUris();
		
		ContextSender sender = mock(ContextSender.class);
		ContextCaster caster = new ContextCaster(sender);
		caster.cast(models.getChorSpec(), deployedServices);
		
		List<String> expectedAirlineUrisList = new ArrayList<String>();
		expectedAirlineUrisList.add(AIRLINE_PROXIFIED_URI);
		

		
		verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
	}
	
	@Test
	public void shouldPassAirlineNativeUriToTravelAgency() throws ContextNotSentException {
		
		ContextSender sender = mock(ContextSender.class);
		ContextCaster caster = new ContextCaster(sender);
		caster.cast(models.getChorSpec(), deployedServices);
		
		List<String> expectedAirlineUrisList = new ArrayList<String>();
		expectedAirlineUrisList.add(AIRLINE_URI);
		verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
	}
	
	@After
	public void tearDown() {
		System.out.println("Cleaning fixtures!");
		models = null;
	}

}
