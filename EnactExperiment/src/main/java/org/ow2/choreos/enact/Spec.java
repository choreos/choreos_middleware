package org.ow2.choreos.enact;

import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.ServiceDependency;
import org.ow2.choreos.servicedeployer.datamodel.ArtifactType;

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
		airline.setArtifactType(ArtifactType.COMMAND_LINE);
		airline.getRoles().add(AIRLINE);
		chorSpec.addServiceSpec(airline);
		
		ChorServiceSpec travel = new ChorServiceSpec();
		travel.setName(TRAVEL_AGENCY);
		travel.setCodeUri(TRAVEL_AGENCY_JAR);
		travel.setEndpointName(TRAVEL_AGENCY);
		travel.setPort(TRAVEL_AGENCY_PORT);
		travel.setArtifactType(ArtifactType.COMMAND_LINE);
		travel.getRoles().add(TRAVEL_AGENCY);
		ServiceDependency dep = new ServiceDependency(AIRLINE, AIRLINE);
		travel.getDependencies().add(dep);
		chorSpec.addServiceSpec(travel);
		
		return chorSpec;
	}

}
