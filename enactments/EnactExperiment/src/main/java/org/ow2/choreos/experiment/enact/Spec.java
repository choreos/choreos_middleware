package org.ow2.choreos.experiment.enact;

import org.ow2.choreos.chors.datamodel.ChoreographyServiceDependency;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;

public class Spec {

	private static final String AIRLINE = "airline";
	private static final String TRAVEL_AGENCY = "travelagency";	
	private static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/chordeployer/samples/v1-2/airline-service.jar";
	private static final String TRAVEL_AGENCY_JAR = "http://valinhos.ime.usp.br:54080/chordeployer/samples/v1-2/travel-agency-service.jar";	
	private static final int AIRLINE_PORT = 1234;
	private static final int TRAVEL_AGENCY_PORT = 1235;	
	
	public static ChoreographySpec getSpec() {
		
		ChoreographySpec choreographySpec = new ChoreographySpec(); 
		
		ChoreographyServiceSpec airlineChorServiceSpec = new ChoreographyServiceSpec();
		airlineChorServiceSpec.setName(AIRLINE);
		airlineChorServiceSpec.getRoles().add(AIRLINE);
		DeployableServiceSpec airlineServiceSpec = new DeployableServiceSpec(); 
		airlineServiceSpec.setPackageUri(AIRLINE_JAR);
		airlineServiceSpec.setEndpointName(AIRLINE);
		airlineServiceSpec.setPort(AIRLINE_PORT);
		airlineServiceSpec.setPackageType(PackageType.COMMAND_LINE);
		airlineChorServiceSpec.setServiceSpec(airlineServiceSpec);
		choreographySpec.addChoreographyServiceSpec(airlineChorServiceSpec);
		
		ChoreographyServiceSpec travelChorServiceSpec = new ChoreographyServiceSpec();
		travelChorServiceSpec.setName(TRAVEL_AGENCY);
		travelChorServiceSpec.getRoles().add(TRAVEL_AGENCY);
		ChoreographyServiceDependency dep = new ChoreographyServiceDependency(AIRLINE, AIRLINE);
		travelChorServiceSpec.getDependencies().add(dep);
		DeployableServiceSpec travelServiceSpec = new DeployableServiceSpec();
		travelServiceSpec.setPackageUri(TRAVEL_AGENCY_JAR);
		travelServiceSpec.setEndpointName(TRAVEL_AGENCY);
		travelServiceSpec.setPort(TRAVEL_AGENCY_PORT);
		travelServiceSpec.setPackageType(PackageType.COMMAND_LINE);
		travelChorServiceSpec.setServiceSpec(travelServiceSpec);
		choreographySpec.addChoreographyServiceSpec(travelChorServiceSpec);
		
		return choreographySpec;
	}

}
