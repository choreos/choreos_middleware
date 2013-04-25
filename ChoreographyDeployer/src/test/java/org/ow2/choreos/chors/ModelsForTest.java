package org.ow2.choreos.chors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.xml.ChorXmlWriter;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.services.datamodel.DeployableService;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;

import org.ow2.choreos.ee.api.ChoreographyServiceDependency;
import org.ow2.choreos.ee.api.ChoreographyServiceSpec;
import org.ow2.choreos.ee.api.ChoreographySpec;
import org.ow2.choreos.ee.api.DeployableServiceSpec;
import org.ow2.choreos.ee.api.PackageType;
import org.ow2.choreos.ee.api.ResourceImpact;
import org.ow2.choreos.ee.api.ResourceImpactDefs.MemoryTypes;
import org.ow2.choreos.ee.api.ServiceSpec;
import org.ow2.choreos.ee.api.ServiceType;

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

	public static final String AIRLINE_IP = "192.168.56.101";
	public static final String TRAVEL_AGENCY_IP = "192.168.56.102";
	public static final int AIRLINE_PORT = 1234;
	public static final int TRAVEL_AGENCY_PORT = 1235;
	public static final String AIRLINE_URI = "http://" + AIRLINE_IP + ":"
			+ AIRLINE_PORT + "/" + AIRLINE + "/";
	public static final String TRAVEL_AGENCY_URI = "http://" + TRAVEL_AGENCY_IP
			+ ":" + TRAVEL_AGENCY_PORT + "/" + TRAVEL_AGENCY + "/";

	private PackageType packageType;
	private ServiceType serviceType;
	private Choreography chor;
	private ChoreographySpec chorSpec;

	private ServiceSpec airlineSpec;
	private ServiceSpec travelSpec;

	private Service airlineService;
	private Service travelAgencyService;

	private ChoreographyServiceSpec airlineChoreographyServiceSpec;
	private ChoreographyServiceSpec travelAgencyChoreographyServiceSpec;

	private ChoreographyService travelChoreographyService;
	private ChoreographyService airlineChoreographyService;

	public ModelsForTest(ServiceType serviceType, PackageType type) {
		this.packageType = type;
		this.serviceType = serviceType;

		initAirlineSpecs();
		initAirlineServices();

		initTravelAgencySpecs();
		initTravelServices();
		createChorSpec();
		createChoreography();

	}

	public ServiceSpec getAirlineSpec() {
		return airlineSpec;
	}

	public ServiceSpec getTravelSpec() {
		return travelSpec;
	}

	public Service getTravelService() {
		return travelAgencyService;
	}

	public Service getAirlineService() {
		return airlineService;
	}

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

	public ChoreographySpec getChorSpec() {
		return this.chorSpec;
	}

	public Choreography getChoreography() {
		return this.chor;
	}

	private void createChorSpec() {

		this.chorSpec = new ChoreographySpec(
				this.airlineChoreographyServiceSpec,
				this.travelAgencyChoreographyServiceSpec);
	}

	private void initAirlineSpecs() {
		if (packageType == PackageType.COMMAND_LINE) {
			airlineSpec = new DeployableServiceSpec(serviceType, packageType,
					null, null, AIRLINE_JAR, AIRLINE_PORT, AIRLINE, 1);
		} else {
			airlineSpec = new DeployableServiceSpec(serviceType, packageType,
					null, null, AIRLINE_JAR, AIRLINE, 1);
		}
		List<String> roles = new ArrayList<String>();
		roles.add(AIRLINE);

		airlineChoreographyServiceSpec = new ChoreographyServiceSpec(
				airlineSpec, null, null, roles, AIRLINE);
	}

	private void initTravelAgencySpecs() {
		if (packageType == PackageType.COMMAND_LINE) {
			travelSpec = new DeployableServiceSpec(serviceType, packageType,
					null, null, TRAVEL_AGENCY_JAR, TRAVEL_AGENCY_PORT,
					TRAVEL_AGENCY, 1);
		} else {
			travelSpec = new DeployableServiceSpec(serviceType, packageType,
					null, null, TRAVEL_AGENCY_JAR, TRAVEL_AGENCY, 1);
		}

		List<String> roles = new ArrayList<String>();
		roles.add(TRAVEL_AGENCY);

		List<ChoreographyServiceDependency> dependencies = new ArrayList<ChoreographyServiceDependency>();
		dependencies.add(new ChoreographyServiceDependency(AIRLINE, AIRLINE));

		travelAgencyChoreographyServiceSpec = new ChoreographyServiceSpec(
				travelSpec, null, null, roles, dependencies, TRAVEL_AGENCY);
	}

	public void initTravelServices() {
		Node node = createTestNode("2", TRAVEL_AGENCY_IP, "choreos-node");
		travelAgencyService = new DeployableService(
				(DeployableServiceSpec) this.travelSpec);

		((DeployableService) travelAgencyService)
				.addInstance(new ServiceInstance(node));
		travelChoreographyService = new ChoreographyService(
				travelAgencyChoreographyServiceSpec);
		travelChoreographyService.setService(getTravelService());
	}

	public void initAirlineServices() {
		Node node = createTestNode("1", AIRLINE_IP, "choreos-node");
		airlineService = new DeployableService(
				(DeployableServiceSpec) this.airlineSpec);
		((DeployableService) airlineService)
				.addInstance(new ServiceInstance(node));
		airlineChoreographyService = new ChoreographyService(
				airlineChoreographyServiceSpec);
		airlineChoreographyService.setService(getAirlineService());
	}

	private void createChoreography() {

		if (chorSpec == null)
			createChorSpec();

		this.chor = new Choreography();
		this.chor.setId("1");
		this.chor.setChoreographySpec(this.chorSpec);

		this.chor.addChoreographyService(travelChoreographyService);
		this.chor.addChoreographyService(airlineChoreographyService);
	}

	private Node createTestNode(String id, String ip, String hostname) {
		Node node1 = new Node();
		node1.setId(id);
		node1.setIp(ip);
		node1.setHostname(hostname);
		return node1;
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

		ServiceSpec airlineServiceSpec = new DeployableServiceSpec(
				ServiceType.SOAP, PackageType.COMMAND_LINE, null, null,
				AIRLINE_JAR, AIRLINE_PORT, AIRLINE, numberOfAirlineServices);

		List<String> roles = new ArrayList<String>();
		roles.add(AIRLINE);
		ChoreographyServiceSpec airChorServiceSpec = new ChoreographyServiceSpec(
				airlineServiceSpec, null, null, roles, AIRLINE);

		spec.addChoreographyServiceSpec(airChorServiceSpec);
		
		ServiceSpec travelAgencyServiceSpec = new DeployableServiceSpec(
				ServiceType.SOAP, PackageType.COMMAND_LINE, null, null,
				TRAVEL_AGENCY_JAR, TRAVEL_AGENCY_PORT, TRAVEL_AGENCY, 1);

		List<String> roles1 = new ArrayList<String>();
		roles1.add(TRAVEL_AGENCY);
		
		ChoreographyServiceSpec travelAgencyChorServiceSpec = new ChoreographyServiceSpec(
				travelAgencyServiceSpec, null, null, roles1, TRAVEL_AGENCY);
		
		travelAgencyChorServiceSpec.addDependency(new ChoreographyServiceDependency(AIRLINE, AIRLINE));
		
		spec.addChoreographyServiceSpec(travelAgencyChorServiceSpec);

		return spec;
	}

	public static void main(String[] args) throws JAXBException, IOException {

		ModelsForTest models = new ModelsForTest(ServiceType.SOAP,
				PackageType.COMMAND_LINE);
		System.out.println("ChorSpec XML representation:");
		System.out.println(models.getChorSpecXML());
		System.out.println("\nChoreography XML representation:");
		System.out.println(models.getChoreographyXML());
	}

	public ChoreographySpec getChorSpecWithResourceImpact(MemoryTypes type) {

		ChoreographySpec spec = new ChoreographySpec();

		ResourceImpact r1 = new ResourceImpact();
		r1.setMemory(type);

		ServiceSpec airlineServiceSpec = new DeployableServiceSpec(
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