package org.ow2.choreos.chors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceDependency;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.xml.ChorXmlWriter;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpactDefs.MemoryTypes;
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
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
	private ServiceType serviceType;

	private ServiceSpec airlineSpec;

	private ServiceSpec travelSpec;
	
	private Service airlineService;
	private Service travelAgencyService;

	private ChoreographyServiceSpec airlineChoreographyServiceSpec;

	private ChoreographyServiceSpec travelAgencyChoreographyServiceSpec;

	private ChoreographyService travelChoreographyService;

	public ChoreographyServiceSpec getAirlineChoreographyServiceSpec() {
		return airlineChoreographyServiceSpec;
	}

	public ChoreographyServiceSpec getTravelAgencyChoreographyServiceSpec() {
		return travelAgencyChoreographyServiceSpec;
	}

	public ChoreographyService getTravelChoreographyService() {
		return travelChoreographyService;
	}

	public ChoreographyService getAirlineChoreographyService() {
		return airlineChoreographyService;
	}

	private ChoreographyService airlineChoreographyService;

	public ModelsForTest(ServiceType serviceType, PackageType type) {
		this.type = type;
		this.serviceType = serviceType;

		initAirlineSpecs();
		initTravelAgencySpecs();
		initAirlineServices();
		initTravelServices();
		createChorSpec();
		createChoreography();
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

	private void initAirlineSpecs() {
		if (type == PackageType.COMMAND_LINE) {
			airlineSpec = new DeployedServiceSpec(serviceType, type,
					null, null, AIRLINE_JAR, AIRLINE_PORT, AIRLINE, 1);
		} else {
			airlineSpec = new DeployedServiceSpec(serviceType, type,
					null, null, AIRLINE_JAR, AIRLINE, 1);
		}
		List<String> roles = new ArrayList<String>();
		roles.add(AIRLINE);

		airlineChoreographyServiceSpec = new ChoreographyServiceSpec(
				airlineSpec, null, null, roles, TRAVEL_AGENCY);
	}
	
	public ServiceSpec getAirlineSpec() {
		initAirlineSpecs();
		return airlineSpec;
	}

	public ServiceSpec getTravelSpec() {
		initTravelAgencySpecs();
		return travelSpec;
	}
	
	public void initTravelServices() {
		initTravelAgencySpecs();
		Node node= createTestNode("2", "192.168.56.102", "choreos-node");
		travelAgencyService = new DeployedService(
				(DeployedServiceSpec) this.travelSpec);
		ServiceInstance agency = new ServiceInstance(node,
				(DeployedService) travelAgencyService);
		((DeployedService) travelAgencyService).addInstance(agency);
		travelChoreographyService = new ChoreographyService(travelAgencyChoreographyServiceSpec);
		travelChoreographyService.setService(getTravelService());
	}
	
	public Service getTravelService() {
		return travelAgencyService;
	}
	
	public void initAirlineServices() {
		initAirlineSpecs();
		Node node= createTestNode("1", "192.168.56.101", "choreos-node");
		airlineService = new DeployedService(
				(DeployedServiceSpec) this.airlineSpec);
		ServiceInstance airline = new ServiceInstance(node,
				(DeployedService) airlineService);
		((DeployedService) airlineService).addInstance(airline);
		airlineChoreographyService = new ChoreographyService(airlineChoreographyServiceSpec);
		airlineChoreographyService.setService(getAirlineService());
	}

	public Service getAirlineService() {
		return airlineService;
	}
	
	private void createJarChorSpec() {

		createServiceSpecsForAirlineAndTravelAgencyUsingJAR();

		createChoreographyServiceSpecForAirlineAndTravelAgency();

		ChoreographyServiceDependency dep = new ChoreographyServiceDependency(
				AIRLINE, AIRLINE);
		this.travelAgencyChoreographyServiceSpec.addDependency(dep);

		this.chorSpec = new ChoreographySpec(
				this.airlineChoreographyServiceSpec,
				this.travelAgencyChoreographyServiceSpec);
	}

	private void initTravelAgencySpecs() {
		if (type == PackageType.COMMAND_LINE) {
		travelSpec = new DeployedServiceSpec(serviceType,
				type, null, null, TRAVEL_AGENCY_JAR,
				TRAVEL_AGENCY_PORT, TRAVEL_AGENCY, 1);
		} else {
			travelSpec = new DeployedServiceSpec(serviceType, type,
					null, null, TRAVEL_AGENCY_JAR, TRAVEL_AGENCY, 1);
		}

		List<String>roles = new ArrayList<String>();
		roles.add(TRAVEL_AGENCY);

		List<ChoreographyServiceDependency> dependencies =
				new ArrayList<ChoreographyServiceDependency>();
		dependencies.add(new ChoreographyServiceDependency(AIRLINE, AIRLINE));
		
		travelAgencyChoreographyServiceSpec = new ChoreographyServiceSpec(
				travelSpec, null, null, roles, dependencies, TRAVEL_AGENCY);
	}

	private void createChoreographyServiceSpecForAirlineAndTravelAgency() {
		this.airlineChoreographyServiceSpec = new ChoreographyServiceSpec(
				airlineSpec, "comp1", "chor1", null, AIRLINE);
		this.airlineChoreographyServiceSpec.addRole(AIRLINE);

		this.travelAgencyChoreographyServiceSpec = new ChoreographyServiceSpec(
				travelSpec, "comp1", "chor1", null, TRAVEL_AGENCY);
		this.travelAgencyChoreographyServiceSpec.addRole(TRAVEL_AGENCY);
	}

	private void createServiceSpecsForAirlineAndTravelAgencyUsingJAR() {
		this.airlineSpec = new DeployedServiceSpec(ServiceType.SOAP,
				PackageType.COMMAND_LINE, null, null, AIRLINE_JAR,
				AIRLINE_PORT, AIRLINE, 1);

		this.travelSpec = new DeployedServiceSpec(ServiceType.SOAP,
				PackageType.COMMAND_LINE, null, null, TRAVEL_AGENCY_JAR,
				TRAVEL_AGENCY_PORT, TRAVEL_AGENCY, 1);
	}

	private void createWarChorSpec() {

		createServiceSpecsForAirlineAndTravelAgencyUsingWAR();

		createChoreographyServiceSpecForAirlineAndTravelAgency();

		ChoreographyServiceDependency dep = new ChoreographyServiceDependency(
				AIRLINE, AIRLINE);
		this.travelAgencyChoreographyServiceSpec.addDependency(dep);

		this.chorSpec = new ChoreographySpec(
				this.airlineChoreographyServiceSpec,
				this.travelAgencyChoreographyServiceSpec);
	}

	private void createServiceSpecsForAirlineAndTravelAgencyUsingWAR() {
		this.airlineSpec = new DeployedServiceSpec(ServiceType.SOAP,
				PackageType.TOMCAT, null, null, AIRLINE_WAR, AIRLINE, 1);

		this.travelSpec = new DeployedServiceSpec(ServiceType.SOAP,
				PackageType.COMMAND_LINE, null, null, TRAVEL_AGENCY_WAR,
				TRAVEL_AGENCY, 1);
	}

	private void createChoreography() {

		if (chorSpec == null)
			createChorSpec();

		this.chor = new Choreography();
		this.chor.setId("1");
		this.chor.setChoreographySpec(this.chorSpec);

		initAirlineChoreographyService();
		initTravelChoreographyService();
		this.chor.addChoreographyService(travelChoreographyService);
		this.chor.addChoreographyService(airlineChoreographyService);
	}

	private void initTravelChoreographyService() {
		travelChoreographyService = new ChoreographyService(
				this.travelAgencyChoreographyServiceSpec);
		travelChoreographyService.setService(getTravelService());
	}

	private void initAirlineChoreographyService() {
		airlineChoreographyService = new ChoreographyService(
				this.airlineChoreographyServiceSpec);
		airlineChoreographyService.setService(getAirlineService());
	}

	private Node createTestNode(String id, String ip, String hostname) {
		Node node1 = new Node();
		node1.setId(id);
		node1.setIp(ip);
		node1.setHostname(hostname);
		return node1;
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

		ServiceSpec airlineServiceSpec = new DeployedServiceSpec(
				ServiceType.SOAP, PackageType.COMMAND_LINE, null, null,
				AIRLINE_JAR, AIRLINE_PORT, AIRLINE, numberOfAirlineServices);

		List<String> roles = new ArrayList<String>();
		roles.add(AIRLINE);
		ChoreographyServiceSpec airChorServiceSpec = new ChoreographyServiceSpec(
				airlineServiceSpec, null, null, roles, AIRLINE);

		spec.addChoreographyServiceSpec(airChorServiceSpec);
		initTravelAgencySpecs();
		spec.addChoreographyServiceSpec(this.travelAgencyChoreographyServiceSpec);
		return spec;
	}

	public static void main(String[] args) throws JAXBException, IOException {

		ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
		System.out.println("ChorSpec XML representation:");
		System.out.println(models.getChorSpecXML());
		System.out.println("\nChoreography XML representation:");
		System.out.println(models.getChoreographyXML());
	}

	public ChoreographySpec getChorSpecWithResourceImpact(MemoryTypes type) {

		ChoreographySpec spec = new ChoreographySpec();

		ResourceImpact r1 = new ResourceImpact();
		r1.setMemory(type);

		ServiceSpec airlineServiceSpec = new DeployedServiceSpec(
				ServiceType.SOAP, PackageType.COMMAND_LINE, r1, null,
				AIRLINE_JAR, AIRLINE_PORT, AIRLINE, 1);

		List<String> roles = new ArrayList<String>();
		roles.add(AIRLINE);
		ChoreographyServiceSpec airChorServiceSpec = new ChoreographyServiceSpec(
				airlineServiceSpec, null, null, roles, AIRLINE);

		spec.addChoreographyServiceSpec(airChorServiceSpec);
		initTravelAgencySpecs();
		spec.addChoreographyServiceSpec(travelAgencyChoreographyServiceSpec);
		return spec;
	}
}
