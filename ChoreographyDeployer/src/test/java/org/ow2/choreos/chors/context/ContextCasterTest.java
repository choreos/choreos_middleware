package org.ow2.choreos.chors.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.deployment.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * 
 * @author leonardo
 *
 */
public class ContextCasterTest {

	private static final String AIRLINE = ModelsForTest.AIRLINE;
	private static final String TRAVEL_AGENCY = ModelsForTest.TRAVEL_AGENCY;	
	private static final String AIRLINE_URI = "http://localhost:1234/airline";
	private static final String AIRLINE_PROXIFIED_URI = "http://localhost:8180/services/AirlineServicePortClientProxyEndpoint";
	private static final String TRAVEL_AGENCY_URI = "http://localhost:1235/travelagency";	
	private static final String TRAVEL_AGENCY_PROXIFIED_URI = "http://localhost:8180/services/TravelAgencyServicePortClientProxyEndpoint";
	
	private Map<String, ChoreographyService> deployedServices;
	
	ModelsForTest models;
	
	@BeforeClass
	public static void configLog() {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() {
		
		models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
		this.deployedServices = new HashMap<String, ChoreographyService>();
		this.deployedServices.put(AIRLINE, models.getAirlineChoreographyService());
		this.deployedServices.put(TRAVEL_AGENCY, models.getTravelChoreographyService());
	}

	private void setUpBusUris() throws ServiceInstanceNotFoundException {
	
		ServiceInstance airlineInstance = ((DeployedService)(deployedServices.get(AIRLINE).getService())).getInstance(AIRLINE); 
		airlineInstance.setBusUri(ServiceType.SOAP, AIRLINE_PROXIFIED_URI);
		ServiceInstance travelInstance = ((DeployedService)(deployedServices.get(AIRLINE).getService())).getInstance(TRAVEL_AGENCY);
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

}
