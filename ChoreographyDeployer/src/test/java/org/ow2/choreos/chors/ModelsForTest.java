package org.ow2.choreos.chors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceDependency;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.xml.ChorXmlWriter;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpactDefs.MemoryTypes;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

public class ModelsForTest {

	private Logger logger = Logger.getLogger(ModelsForTest.class);

	public static final String AIRLINE = "airline";
	public static final String TRAVEL_AGENCY = "travelagency";
	public static final String AIRLINE_JAR = Locations.get("AIRLINE_JAR");
	public static final String TRAVEL_AGENCY_JAR = Locations
			.get("TRAVEL_AGENCY_JAR");
	public static final String AIRLINE_WAR = Locations.get("AIRLINE_WAR");
	public static final String TRAVEL_AGENCY_WAR = Locations
			.get("TRAVEL_AGENCY_WAR");
	public static final int AIRLINE_PORT = 1234;
	public static final int TRAVEL_AGENCY_PORT = 1235;

	private ChoreographySpec chorSpec;
	private Choreography chor;
	private PackageType type;

	private ServiceSpec airlineSpec;

	private ServiceSpec travelSpec;

	private ChoreographyServiceSpec airlineChoreographyServiceSpec;

	private ChoreographyServiceSpec travelAgencyChoreographyServiceSpec;

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
			throw new IllegalStateException(
					"Type shold be COMMAND_LINE or TOMCAT, not " + type);
		}
	}

	private void createJarChorSpec() {

		createServiceSpecsForAirlineAndTravelAgency();

		createChoreographyServiceSpecForAirlineAndTravelAgency();

		ChoreographyServiceDependency dep = new ChoreographyServiceDependency(
				AIRLINE, AIRLINE);
		this.travelAgencyChoreographyServiceSpec.addDependency(dep);
		
		this.chorSpec = new ChoreographySpec(this.airlineChoreographyServiceSpec,this.travelAgencyChoreographyServiceSpec);
	}

	private void createChoreographyServiceSpecForAirlineAndTravelAgency() {
		this.airlineChoreographyServiceSpec = new ChoreographyServiceSpec(
				airlineSpec, "comp1", "chor1", null, AIRLINE);
		this.airlineChoreographyServiceSpec.addRole(AIRLINE);

		this.travelAgencyChoreographyServiceSpec = new ChoreographyServiceSpec(
				travelSpec, "comp1", "chor1", null, TRAVEL_AGENCY);
		this.travelAgencyChoreographyServiceSpec.addRole(TRAVEL_AGENCY);
	}

	private void createServiceSpecsForAirlineAndTravelAgency() {
		this.airlineSpec = new DeployedServiceSpec(ServiceType.SOAP,
				PackageType.COMMAND_LINE, null, null, AIRLINE_JAR,
				AIRLINE_PORT, AIRLINE, 1);

		this.travelSpec = new DeployedServiceSpec(ServiceType.SOAP,
				PackageType.COMMAND_LINE, null, null, TRAVEL_AGENCY_JAR,
				TRAVEL_AGENCY_PORT, TRAVEL_AGENCY, 1);
	}

	private void createWarChorSpec() {

		this.chorSpec = new ChoreographySpec();

		this.airlineSpec = new ServiceSpec();
		this.airlineSpec.setName(AIRLINE);
		this.airlineSpec.setPackageUri(AIRLINE_WAR);
		this.airlineSpec.setEndpointName(AIRLINE);
		this.airlineSpec.setPackageType(PackageType.TOMCAT);
		this.airlineSpec.setType(ServiceType.SOAP);
		this.airlineSpec.getRoles().add(AIRLINE);
		this.chorSpec.addChoreographyServiceSpec(this.airlineSpec);

		this.travelSpec = new ServiceSpec();
		this.travelSpec.setName(TRAVEL_AGENCY);
		this.travelSpec.setPackageUri(TRAVEL_AGENCY_WAR);
		this.travelSpec.setEndpointName(TRAVEL_AGENCY);
		this.travelSpec.setPackageType(PackageType.TOMCAT);
		this.travelSpec.setType(ServiceType.SOAP);
		this.travelSpec.getRoles().add(TRAVEL_AGENCY);
		ChoreographyServiceDependency dep = new ChoreographyServiceDependency(
				AIRLINE, AIRLINE);
		this.travelSpec.getDependencies().add(dep);
		this.chorSpec.addChoreographyServiceSpec(this.travelSpec);
	}

	private void creteChoreography() {

		if (chorSpec == null)
			createChorSpec();

		this.chor = new Choreography();
		this.chor.setId("1");
		this.chor.setChoreographySpec(this.chorSpec);

		// create nodes
		Node node1 = new Node();
		node1.setId("1");
		node1.setIp("192.168.56.101");
		node1.setHostname("choreos-node");
		Node node2 = new Node();
		node2.setId("2");
		node2.setIp("192.168.56.102");
		node2.setHostname("choreos-node");

		// create service
		Service airlineService = new Service();
		airlineService.setName(AIRLINE);
		airlineService.setSpec(this.airlineSpec);

		// create instance
		ServiceInstance airline = new ServiceInstance();
		airline.setMyParentServiceSpec(airlineService.getSpec());
		airline.setNode(node1);

		// add instance to service
		airlineService.addInstance(airline);
		// add service to choreography
		this.chor.getDeployedChoreographyServices().add(airlineService);

		Service travelService = new Service();
		travelService.setName(TRAVEL_AGENCY);
		travelService.setSpec(this.travelSpec);

		ServiceInstance travel = new ServiceInstance();
		travel.setMyParentServiceSpec(travelService.getSpec());
		travel.setNode(node2);

		travelService.addInstance(travel);
		this.chor.getDeployedChoreographyServices().add(travelService);
	}

	public ChoreographySpec getChorSpec() {

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

	public ChoreographySpec getChorSpecWithReplicas(int numberOfAirlineServices) {

		ChoreographySpec spec = new ChoreographySpec();

		ServiceSpec airlineServiceSpec = new ServiceSpec();

		airlineServiceSpec.setName(AIRLINE);
		airlineServiceSpec.setPackageUri(AIRLINE_JAR);
		airlineServiceSpec.setEndpointName(AIRLINE);
		airlineServiceSpec.setPort(AIRLINE_PORT);
		airlineServiceSpec.setPackageType(PackageType.COMMAND_LINE);
		airlineServiceSpec.getRoles().add(AIRLINE);
		airlineServiceSpec.setNumberOfInstances(numberOfAirlineServices);
		spec.addChoreographyServiceSpec(airlineServiceSpec);

		ServiceSpec travelServiceSpec = new ServiceSpec();

		travelServiceSpec.setName(TRAVEL_AGENCY);
		travelServiceSpec.setPackageUri(TRAVEL_AGENCY_JAR);
		travelServiceSpec.setEndpointName(TRAVEL_AGENCY);
		travelServiceSpec.setPort(TRAVEL_AGENCY_PORT);
		travelServiceSpec.setPackageType(PackageType.COMMAND_LINE);
		travelServiceSpec.getRoles().add(TRAVEL_AGENCY);
		ChoreographyServiceDependency dep = new ChoreographyServiceDependency(
				AIRLINE, AIRLINE);
		travelServiceSpec.getDependencies().add(dep);
		spec.addChoreographyServiceSpec(travelServiceSpec);

		return spec;
	}

	public static void main(String[] args) throws JAXBException, IOException {

		ModelsForTest models = new ModelsForTest(PackageType.COMMAND_LINE);
		System.out.println("ChorSpec XML representation:");
		System.out.println(models.getChorSpecXML());
		System.out.println("\nChoreography XML representation:");
		System.out.println(models.getChoreographyXML());
	}

	public ChoreographySpec getChorSpecWithResourceImpact(MemoryTypes type) {

		ChoreographySpec spec = new ChoreographySpec();

		ServiceSpec airlineServiceSpec = new ServiceSpec();

		airlineServiceSpec.setName(AIRLINE);
		airlineServiceSpec.setPackageUri(AIRLINE_JAR);
		airlineServiceSpec.setEndpointName(AIRLINE);
		airlineServiceSpec.setPort(AIRLINE_PORT);
		airlineServiceSpec.setPackageType(PackageType.COMMAND_LINE);

		ResourceImpact r1 = new ResourceImpact();
		r1.setMemory(type);
		airlineServiceSpec.setResourceImpact(r1);

		airlineServiceSpec.getRoles().add(AIRLINE);
		spec.addChoreographyServiceSpec(airlineServiceSpec);

		ServiceSpec travelServiceSpec = new ServiceSpec();

		travelServiceSpec.setName(TRAVEL_AGENCY);
		travelServiceSpec.setPackageUri(TRAVEL_AGENCY_JAR);
		travelServiceSpec.setEndpointName(TRAVEL_AGENCY);
		travelServiceSpec.setPort(TRAVEL_AGENCY_PORT);
		travelServiceSpec.setPackageType(PackageType.COMMAND_LINE);

		travelServiceSpec.getRoles().add(TRAVEL_AGENCY);
		ChoreographyServiceDependency dep = new ChoreographyServiceDependency(
				AIRLINE, AIRLINE);
		travelServiceSpec.getDependencies().add(dep);
		spec.addChoreographyServiceSpec(travelServiceSpec);

		return spec;
	}
}
