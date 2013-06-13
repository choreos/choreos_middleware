/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.datamodel.xml.ChorXmlWriter;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ModelsForTest {

    public static final String AIRLINE = "airline";
    public static final String TRAVEL_AGENCY = "travelagency";
    public static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar";
    public static final String TRAVEL_AGENCY_JAR = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar";
    public static final String AIRLINE_WAR = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war";
    public static final String TRAVEL_AGENCY_WAR = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war";

    public static final String AIRLINE_IP = "192.168.56.101";
    public static final String TRAVEL_AGENCY_IP = "192.168.56.102";
    public static final int AIRLINE_PORT = 1234;
    public static final int TRAVEL_AGENCY_PORT = 1235;
    public static final String AIRLINE_URI = "http://" + AIRLINE_IP + ":" + AIRLINE_PORT + "/" + AIRLINE + "/";
    public static final String TRAVEL_AGENCY_URI = "http://" + TRAVEL_AGENCY_IP + ":" + TRAVEL_AGENCY_PORT + "/"
	    + TRAVEL_AGENCY + "/";

    private final String serviceVersion = "0.1";
    private final int numberOfTravelInstances = 1;

    private PackageType packageType;
    private ServiceType serviceType;
    private ResourceImpact resourceImpact = new ResourceImpact();
    private int numberOfAirlineServices = 1;

    private Choreography chor;
    private ChoreographySpec chorSpec;

    private DeployableServiceSpec airlineSpec;
    private DeployableServiceSpec travelSpec;

    private DeployableService airlineService;
    private DeployableService travelService;

    private Logger logger = Logger.getLogger(ModelsForTest.class);

    public ModelsForTest(ServiceType serviceType, PackageType packageType) {
	this.packageType = packageType;
	this.serviceType = serviceType;
	initAirlineSpec();
	initAirlineService();
	initTravelSpec();
	initTravelService();
	createChorSpec();
	createChoreography();
    }

    public ModelsForTest(ServiceType serviceType, PackageType packageType, int numberOfAirlineServices) {
	this.packageType = packageType;
	this.serviceType = serviceType;
	this.numberOfAirlineServices = numberOfAirlineServices;
	initAirlineSpec();
	initAirlineService();
	initTravelSpec();
	initTravelService();
	createChorSpec();
	createChoreography();
    }

    public ModelsForTest(ServiceType serviceType, PackageType packageType, ResourceImpact resourceImpact) {
	this.packageType = packageType;
	this.serviceType = serviceType;
	this.resourceImpact = resourceImpact;
	initAirlineSpec();
	initAirlineService();
	initTravelSpec();
	initTravelService();
	createChorSpec();
	createChoreography();
    }

    public DeployableServiceSpec getAirlineSpec() {
	return airlineSpec;
    }

    public DeployableServiceSpec getTravelSpec() {
	return travelSpec;
    }

    public DeployableService getTravelService() {
	return travelService;
    }

    public DeployableService getAirlineService() {
	return airlineService;
    }

    public ChoreographySpec getChorSpec() {
	return this.chorSpec;
    }

    public Choreography getChoreography() {
	return this.chor;
    }

    private void createChorSpec() {
	this.chorSpec = new ChoreographySpec(this.airlineSpec, this.travelSpec);
    }

    private void initAirlineSpec() {
	if (packageType == PackageType.COMMAND_LINE) {
	    airlineSpec = new DeployableServiceSpec(AIRLINE, serviceType, packageType, resourceImpact, serviceVersion,
		    AIRLINE_JAR, AIRLINE_PORT, AIRLINE, numberOfAirlineServices);
	} else {
	    airlineSpec = new DeployableServiceSpec(AIRLINE, serviceType, packageType, resourceImpact, serviceVersion,
		    AIRLINE_WAR, AIRLINE, numberOfAirlineServices);
	}
	airlineSpec.setRoles(Collections.singletonList(AIRLINE));
    }

    private void initTravelSpec() {
	if (packageType == PackageType.COMMAND_LINE) {
	    travelSpec = new DeployableServiceSpec(TRAVEL_AGENCY, serviceType, packageType, resourceImpact,
		    serviceVersion, TRAVEL_AGENCY_JAR, TRAVEL_AGENCY_PORT, TRAVEL_AGENCY, numberOfTravelInstances);
	} else {
	    travelSpec = new DeployableServiceSpec(TRAVEL_AGENCY, serviceType, packageType, resourceImpact,
		    serviceVersion, TRAVEL_AGENCY_WAR, TRAVEL_AGENCY, numberOfTravelInstances);
	}
	travelSpec.setRoles(Collections.singletonList(TRAVEL_AGENCY));
	travelSpec.addDependency(new ServiceDependency(AIRLINE, AIRLINE));
    }

    public void initTravelService() {
	Node node = createNode("2", TRAVEL_AGENCY_IP, "choreos-node");
	travelService = new DeployableService(this.travelSpec);
	ServiceInstance instance = new ServiceInstance(node);
	instance.setInstanceId(TRAVEL_AGENCY + "1");
	instance.setServiceSpec(this.travelSpec);
	travelService.setServiceInstances(Collections.singletonList(instance));
    }

    public void initAirlineService() {
	airlineService = new DeployableService(this.airlineSpec);
	Node node = createNode("1", AIRLINE_IP, "choreos-node"); // TODO
								 // instances
								 // should be in
								 // different
								 // nodes
	List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
	for (int i = 0; i < numberOfAirlineServices; i++) {
	    ServiceInstance instance = new ServiceInstance(node);
	    instance.setInstanceId(AIRLINE + i);
	    instance.setServiceSpec(this.airlineSpec);
	    instances.add(instance);
	}
	airlineService.setServiceInstances(instances);
    }

    private void createChoreography() {
	if (chorSpec == null)
	    createChorSpec();
	this.chor = new Choreography();
	this.chor.setId("1");
	this.chor.setChoreographySpec(this.chorSpec);
	this.chor.addService(travelService);
	this.chor.addService(airlineService);
    }

    private Node createNode(String id, String ip, String hostname) {
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

    /**
     * Prints ChorSpec and Choreography XML representations
     * 
     */
    public static void main(String[] args) throws JAXBException, IOException {
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	System.out.println("ChorSpec XML representation:");
	System.out.println(models.getChorSpecXML());
	System.out.println("\nChoreography XML representation:");
	System.out.println(models.getChoreographyXML());
    }

}