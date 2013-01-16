package org.ow2.choreos.chors;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ServiceDependency;
import org.ow2.choreos.chors.datamodel.xml.ChorXmlWriter;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.Service;

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
	private ArtifactType type;
	
	private ChorServiceSpec airlineSpec, travelSpec;
	
	public ModelsForTest(ArtifactType type) {
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
		}
	}
	
	private void createJarChorSpec() {

		this.chorSpec = new ChorSpec(); 
		
		this.airlineSpec = new ChorServiceSpec();
		this.airlineSpec.setName(AIRLINE);
		this.airlineSpec.setCodeUri(AIRLINE_JAR);
		this.airlineSpec.setEndpointName(AIRLINE);
		this.airlineSpec.setPort(AIRLINE_PORT);
		this.airlineSpec.setArtifactType(ArtifactType.COMMAND_LINE);
		this.airlineSpec.getRoles().add(AIRLINE);
		this.chorSpec.addServiceSpec(this.airlineSpec);
		
		this.travelSpec = new ChorServiceSpec();
		this.travelSpec.setName(TRAVEL_AGENCY);
		this.travelSpec.setCodeUri(TRAVEL_AGENCY_JAR);
		this.travelSpec.setEndpointName(TRAVEL_AGENCY);
		this.travelSpec.setPort(TRAVEL_AGENCY_PORT);
		this.travelSpec.setArtifactType(ArtifactType.COMMAND_LINE);
		this.travelSpec.getRoles().add(TRAVEL_AGENCY);
		ServiceDependency dep = new ServiceDependency(AIRLINE, AIRLINE);
		this.travelSpec.getDependencies().add(dep);
		this.chorSpec.addServiceSpec(this.travelSpec);
	}
	
	private void createWarChorSpec() {

		this.chorSpec = new ChorSpec(); 
		
		this.airlineSpec = new ChorServiceSpec();
		this.airlineSpec.setName(AIRLINE);
		this.airlineSpec.setCodeUri(AIRLINE_WAR);
		this.airlineSpec.setEndpointName(AIRLINE);
		this.airlineSpec.setArtifactType(ArtifactType.TOMCAT);
		this.airlineSpec.getRoles().add(AIRLINE);
		this.chorSpec.addServiceSpec(this.airlineSpec);
		
		this.travelSpec = new ChorServiceSpec();
		this.travelSpec.setName(TRAVEL_AGENCY);
		this.travelSpec.setCodeUri(TRAVEL_AGENCY_WAR);
		this.travelSpec.setEndpointName(TRAVEL_AGENCY);
		this.travelSpec.setArtifactType(ArtifactType.TOMCAT);
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
		
		Service airlineService = new Service();
		airlineService.setHost("choreos-node1");
		airlineService.setIp("192.168.56.101");
		airlineService.setName(AIRLINE);
		airlineService.setNodeId("choreos-node1");
		airlineService.setSpec(this.airlineSpec);
		this.chor.getDeployedServices().add(airlineService);
		
		Service travelService = new Service();
		travelService.setHost("choreos-node2");
		travelService.setIp("192.168.56.102");
		travelService.setName(TRAVEL_AGENCY);
		travelService.setNodeId("choreos-node2");
		travelService.setSpec(this.travelSpec);
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
		
		ModelsForTest models = new ModelsForTest(ArtifactType.COMMAND_LINE);
		System.out.println("ChorSpec XML representation:");
		System.out.println(models.getChorSpecXML());
		System.out.println("\nChoreography XML representation:");
		System.out.println(models.getChoreographyXML());
	}

}
