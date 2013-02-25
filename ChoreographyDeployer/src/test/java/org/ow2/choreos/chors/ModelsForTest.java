package org.ow2.choreos.chors;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ServiceDependency;
import org.ow2.choreos.chors.datamodel.xml.ChorXmlWriter;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;

public class ModelsForTest {

	private Logger logger = Logger.getLogger(ModelsForTest.class);

	public static final String AIRLINE = "airline";
	public static final String TRAVEL_AGENCY = "travelagency";	
	public static final String AIRLINE_JAR = Locations.get("AIRLINE_JAR");
	public static final String TRAVEL_AGENCY_JAR = Locations.get("TRAVEL_AGENCY_JAR");	
	public static final String AIRLINE_WAR = Locations.get("AIRLINE_WAR");
	public static final String TRAVEL_AGENCY_WAR = Locations.get("TRAVEL_AGENCY_WAR");	
	public static final int AIRLINE_PORT = 1234;
	public static final int TRAVEL_AGENCY_PORT = 1235;	

	private ChorSpec chorSpec;
	private Choreography chor;
	private PackageType type;

	private ChorServiceSpec airlineSpec, travelSpec;

	public ModelsForTest(PackageType type) {
		this.type = type;
		createChorSpec();
		creteChoreography();
	}

	private void createChorSpec() {
		switch (type) {
		case COMMAND_LINE: 
			createJarChorSpec();
			break;
		case TOMCAT:
			createWarChorSpec();
			break;
		default:
			throw new IllegalStateException("Type shold be COMMAND_LINE or TOMCAT, not " + type);
		}
	}

	private void createJarChorSpec() {

		this.chorSpec = new ChorSpec(); 

		this.airlineSpec = new ChorServiceSpec();
		this.airlineSpec.setName(AIRLINE);
		this.airlineSpec.setPackageUri(AIRLINE_JAR);
		this.airlineSpec.setEndpointName(AIRLINE);
		this.airlineSpec.setPort(AIRLINE_PORT);
		this.airlineSpec.setPackageType(PackageType.COMMAND_LINE);
		this.airlineSpec.getRoles().add(AIRLINE);
		this.chorSpec.addServiceSpec(this.airlineSpec);

		this.travelSpec = new ChorServiceSpec();
		this.travelSpec.setName(TRAVEL_AGENCY);
		this.travelSpec.setPackageUri(TRAVEL_AGENCY_JAR);
		this.travelSpec.setEndpointName(TRAVEL_AGENCY);
		this.travelSpec.setPort(TRAVEL_AGENCY_PORT);
		this.travelSpec.setPackageType(PackageType.COMMAND_LINE);
		this.travelSpec.getRoles().add(TRAVEL_AGENCY);
		ServiceDependency dep = new ServiceDependency(AIRLINE, AIRLINE);
		this.travelSpec.getDependencies().add(dep);
		this.chorSpec.addServiceSpec(this.travelSpec);
	}

	private void createWarChorSpec() {

		this.chorSpec = new ChorSpec(); 

		this.airlineSpec = new ChorServiceSpec();
		this.airlineSpec.setName(AIRLINE);
		this.airlineSpec.setPackageUri(AIRLINE_WAR);
		this.airlineSpec.setEndpointName(AIRLINE);
		this.airlineSpec.setPackageType(PackageType.TOMCAT);
		this.airlineSpec.getRoles().add(AIRLINE);
		this.chorSpec.addServiceSpec(this.airlineSpec);

		this.travelSpec = new ChorServiceSpec();
		this.travelSpec.setName(TRAVEL_AGENCY);
		this.travelSpec.setPackageUri(TRAVEL_AGENCY_WAR);
		this.travelSpec.setEndpointName(TRAVEL_AGENCY);
		this.travelSpec.setPackageType(PackageType.TOMCAT);
		this.travelSpec.getRoles().add(TRAVEL_AGENCY);
		ServiceDependency dep = new ServiceDependency(AIRLINE, AIRLINE);
		this.travelSpec.getDependencies().add(dep);
		this.chorSpec.addServiceSpec(this.travelSpec);
	}

	private void creteChoreography() {

		if (chorSpec == null)
			createChorSpec();

		this.chor = new Choreography();
		this.chor.setId("1");
		this.chor.setChorSpec(this.chorSpec);

		// create service
		Service airlineService = new Service();
		airlineService.setName(AIRLINE);
		airlineService.setSpec(this.airlineSpec);

		// create instance
		ServiceInstance airline = new ServiceInstance();
		airline.setMyParentServiceSpec(airlineService.getSpec());

		// add instance to service
		airlineService.addInstance(airline);
		// add service to choreography
		this.chor.getDeployedServices().add(airlineService);

		Service travelService = new Service();
		travelService.setName(TRAVEL_AGENCY);
		travelService.setSpec(this.travelSpec);

		ServiceInstance travel = new ServiceInstance();
		travel.setMyParentServiceSpec(travelService.getSpec());


		travelService.addInstance(travel);
		this.chor.getDeployedServices().add(travelService);
	}

	public ChorSpec getChorSpec() {

		return this.chorSpec;
	}

	public Choreography getChoreography() {

		return this.chor;
	}

	private String getChorSpecXML() {

		ChorXmlWriter writer = new ChorXmlWriter();
		try {
			return writer.getChorSpecXML(this.chorSpec);
		} catch (JAXBException e) {
			logger.error("It should never happen");
			return null;
		}
	}

	private String getChoreographyXML() {

		ChorXmlWriter writer = new ChorXmlWriter();
		try {
			return writer.getChoreographyXML(this.chor);
		} catch (JAXBException e) {
			logger.error("It should never happen");
			return null;
		}
	}

	public static void main(String[] args) throws JAXBException, IOException {

		ModelsForTest models = new ModelsForTest(PackageType.COMMAND_LINE);
		System.out.println("ChorSpec XML representation:");
		System.out.println(models.getChorSpecXML());
		System.out.println("\nChoreography XML representation:");
		System.out.println(models.getChoreographyXML());
	}

	public ChorSpec getChorSpecWithReplicas(int numberOfAirlineServices) {
		
		ChorSpec spec = new ChorSpec(); 

		ChorServiceSpec airlineServiceSpec = new ChorServiceSpec();
		
		airlineServiceSpec.setName(AIRLINE);
		airlineServiceSpec.setPackageUri(AIRLINE_JAR);
		airlineServiceSpec.setEndpointName(AIRLINE);
		airlineServiceSpec.setPort(AIRLINE_PORT);
		airlineServiceSpec.setPackageType(PackageType.COMMAND_LINE);
		airlineServiceSpec.getRoles().add(AIRLINE);
		airlineServiceSpec.setNumberOfInstances(numberOfAirlineServices);
		spec.addServiceSpec(airlineServiceSpec);

		ChorServiceSpec travelServiceSpec = new ChorServiceSpec();
		
		travelServiceSpec.setName(TRAVEL_AGENCY);
		travelServiceSpec.setPackageUri(TRAVEL_AGENCY_JAR);
		travelServiceSpec.setEndpointName(TRAVEL_AGENCY);
		travelServiceSpec.setPort(TRAVEL_AGENCY_PORT);
		travelServiceSpec.setPackageType(PackageType.COMMAND_LINE);
		travelServiceSpec.getRoles().add(TRAVEL_AGENCY);
		ServiceDependency dep = new ServiceDependency(AIRLINE, AIRLINE);
		travelServiceSpec.getDependencies().add(dep);
		spec.addServiceSpec(travelServiceSpec);
		
		return spec;
	}

}
