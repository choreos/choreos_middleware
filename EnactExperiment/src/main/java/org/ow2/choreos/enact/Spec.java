package org.ow2.choreos.enact;

import org.ow2.choreos.enactment.datamodel.ChorServiceSpec;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;

public class Spec {

	private static final String AIRLINE = "airline";
	private static final String TRAVEL_AGENCY = "travelagency";	
	private static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/enact_test/airline-service.jar";
	private static final String TRAVEL_AGENCY_JAR = "http://valinhos.ime.usp.br:54080/enact_test/travel-agency-service.jar";	
	private static final int AIRLINE_PORT = 1234;
	private static final int TRAVEL_AGENCY_PORT = 1235;	
	
	public static ChorSpec getSpec() {
		
		ChorSpec chorSpec = new ChorSpec(); 
		
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
		
		return chorSpec;
	}

}
