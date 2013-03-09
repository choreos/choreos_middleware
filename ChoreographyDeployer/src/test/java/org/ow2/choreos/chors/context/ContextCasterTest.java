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
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.deployment.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * 
 * @author leonardo
 *
 */
public class ContextCasterTest {

	private static final String AIRLINE = "airline";
	private static final String TRAVEL_AGENCY = "travelagency";	
	private static final String AIRLINE_URI = "http://localhost:1234/airline";
	private static final String AIRLINE_PROXIFIED_URI = "http://localhost:8180/services/AirlineServicePortClientProxyEndpoint";
	private static final String TRAVEL_AGENCY_URI = "http://localhost:1235/travelagency";	
	private static final String TRAVEL_AGENCY_PROXIFIED_URI = "http://localhost:8180/services/TravelAgencyServicePortClientProxyEndpoint";
	
	private ChorSpec chorSpec;
	private Service airlineServ, travelServ;
	private Map<String, Service> deployedServices;
	
	@BeforeClass
	public static void configLog() {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() {
		
		ModelsForTest models = new ModelsForTest(PackageType.COMMAND_LINE);
		this.chorSpec = models.getChorSpec(); 
		this.deployedServices = new HashMap<String, Service>();
		
		airlineServ = new Service();
		airlineServ.setName(AIRLINE);
		airlineServ.setSpec(chorSpec.getServiceSpecByName(AIRLINE));
		ServiceInstance airlineInstance = new ServiceInstance(); 
		airlineInstance.setInstanceId(AIRLINE);
		airlineInstance.setNativeUri(AIRLINE_URI);
		airlineServ.addInstance(airlineInstance);
		this.deployedServices.put(AIRLINE, airlineServ);

		travelServ = new Service();
		travelServ.setName(TRAVEL_AGENCY);
		travelServ.setSpec(chorSpec.getServiceSpecByName(TRAVEL_AGENCY));
		ServiceInstance travelInstance = new ServiceInstance(); 
		travelInstance.setInstanceId(TRAVEL_AGENCY);
		travelInstance.setNativeUri(TRAVEL_AGENCY_URI);
		travelServ.addInstance(travelInstance);
		this.deployedServices.put(TRAVEL_AGENCY, travelServ);
	}

	private void setUpBusUris() throws ServiceInstanceNotFoundException {
	
		ServiceInstance airlineInstance = this.airlineServ.getInstance(AIRLINE);
		airlineInstance.setBusUri(ServiceType.SOAP, AIRLINE_PROXIFIED_URI);
		ServiceInstance travelInstance = this.travelServ.getInstance(TRAVEL_AGENCY);
		travelInstance.setBusUri(ServiceType.SOAP, TRAVEL_AGENCY_PROXIFIED_URI);
	}
	
	@Test
	public void shouldPassAirlineProxifiedAddressToTravelAgency() throws ContextNotSentException, ServiceInstanceNotFoundException {
		
		this.setUpBusUris();
		ContextSender sender = mock(ContextSender.class);
		ContextCaster caster = new ContextCaster(sender);
		caster.cast(chorSpec, deployedServices);
		
		List<String> expectedAirlineUrisList = new ArrayList<String>();
		expectedAirlineUrisList.add(AIRLINE_PROXIFIED_URI);
		verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
	}
	
	@Test
	public void shouldPassAirlineNativeUriToTravelAgency() throws ContextNotSentException {
		
		ContextSender sender = mock(ContextSender.class);
		ContextCaster caster = new ContextCaster(sender);
		caster.cast(chorSpec, deployedServices);
		
		List<String> expectedAirlineUrisList = new ArrayList<String>();
		expectedAirlineUrisList.add(AIRLINE_URI);
		verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
	}

}
