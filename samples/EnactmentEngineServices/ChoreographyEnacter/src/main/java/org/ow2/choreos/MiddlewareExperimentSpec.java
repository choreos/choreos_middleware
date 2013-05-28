package org.ow2.choreos;

import java.util.Arrays;

import org.ow2.choreos.chors.datamodel.ChoreographyServiceDependency;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.ServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceType;


public class MiddlewareExperimentSpec {

	public static final String AIRLINE = "airline";
	public static final String TRAVEL_AGENCY = "travelagency";	
	public static final String AIRLINE_WAR = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war";
	//public static final String AIRLINE_WAR = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war";
	//public static final String TRAVEL_AGENCY_WAR = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war";
	public static final String TRAVEL_AGENCY_WAR = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war";
	
	private ChoreographySpec spec = null;
	
	public MiddlewareExperimentSpec () {
		this.spec = getSpec();
	}
	
	public ChoreographySpec getInitialChoreographySpec() {
		return this.spec;
	}
	
	private ChoreographySpec getSpec() {

		// create airline service spec
		ServiceSpec airlineServiceSpec = getAirlineServiceSpec();
		// create travel agency service spec
		ServiceSpec travelAgencyServiceSpec = getTravelAgencyServiceSpec();
		// create airline choreography service spec
		ChoreographyServiceSpec airlineChoreographyServiceSpec = 
				getChoreographyServiceSpecForAirline(airlineServiceSpec);
		// create travel agency choreography service spec
		ChoreographyServiceSpec travelAgencyChoreographyServiceSpec = 
				getChoreographyServiceSpecForTravelAgency(travelAgencyServiceSpec);
		// return a new choreography spec
		return new ChoreographySpec(airlineChoreographyServiceSpec, 
				travelAgencyChoreographyServiceSpec);
	}

	private DeployableServiceSpec getTravelAgencyServiceSpec() {
		return new DeployableServiceSpec(ServiceType.SOAP, 
				PackageType.TOMCAT, 
				new ResourceImpact(), 
				"1.1", 
				TRAVEL_AGENCY_WAR, 
				TRAVEL_AGENCY, 
				1);
	}

	private ChoreographyServiceSpec getChoreographyServiceSpecForTravelAgency(
			ServiceSpec travelAgencyServiceSpec) {
		return new ChoreographyServiceSpec(travelAgencyServiceSpec, 
				"usp", 
				"choreos", 
				Arrays.asList(TRAVEL_AGENCY),
				Arrays.asList(new ChoreographyServiceDependency(AIRLINE,AIRLINE)),
				TRAVEL_AGENCY);
	}

	private DeployableServiceSpec getAirlineServiceSpec() {
		return new DeployableServiceSpec(ServiceType.SOAP, 
				PackageType.TOMCAT, 
				new ResourceImpact(), 
				"1.1", 
				AIRLINE_WAR, 
				AIRLINE, 
				1 /* starts with just one replica */ );
	}

	private ChoreographyServiceSpec getChoreographyServiceSpecForAirline(
			ServiceSpec airlineServiceSpec) {
		return new ChoreographyServiceSpec(airlineServiceSpec, 
				"usp", 
				"choreos", 
				Arrays.asList(AIRLINE), 
				AIRLINE);
	}
}
