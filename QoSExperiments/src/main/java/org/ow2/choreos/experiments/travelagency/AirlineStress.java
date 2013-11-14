package org.ow2.choreos.experiments.travelagency;

import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.experiments.travelagency.client.TravelAgencyService;
import org.ow2.choreos.experiments.travelagency.client.TravelAgencyServiceService;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.client.ServicesClient;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.services.datamodel.qos.DesiredQoS;
import org.ow2.choreos.services.datamodel.qos.ResponseTimeMetric;
import org.ow2.choreos.tests.ModelsForTest;

public class AirlineStress {

    private static ChoreographyDeployer enactmentEngine;
    private static ServicesManager servicesManager;

    private ChoreographySpec chorSpec;
    private ModelsForTest models;
    private Choreography chor;

    Logger logger = Logger.getLogger(AirlineStress.class);

    static {
	// Deployment Manager should be running
	enactmentEngine = new ChorDeployerClient("http://localhost:9102/choreographydeployer/");
	// Choreography Deployer should be running
	servicesManager = new ServicesClient("http://localhost:9100/deploymentmanager/");
    }

    public void setUp() {
	models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT);
	chorSpec = models.getChorSpec();

	DesiredQoS desiredQoS = new DesiredQoS();
	ResponseTimeMetric responseTime = new ResponseTimeMetric();
	responseTime.setAcceptablePercentage(0.05f);
	responseTime.setMaxDesiredResponseTime(120f);
	desiredQoS.setResponseTimeMetric(responseTime);

	((DeployableServiceSpec) chorSpec.getServiceSpecByName("airline")).setDesiredQoS(desiredQoS);

    }

    public void shouldRunExperiment() throws Exception {

	setUp();
	String chorId = enactmentEngine.createChoreography(chorSpec);
	chor = enactmentEngine.enactChoreography(chorId);

	runExperiment();

    }

    private void runExperiment() throws InterruptedException, IOException {

	String travelAgencyURI = ((DeployableService) chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY))
		.getInstances().get(0).getNativeUri();

	long TIME_UNIT_IN_MS = 12 * 1000; // 60 * 1000 = 1 minute; 12 * 1000 =
					  // 12
					  // minutes

	long rateVector[][] = { { 1800, 30 }, { 1500, 10 }, { 1300, 10 }, { 1100, 10 }, { 900, 15 }, { 700, 15 },
		{ 400, 30 }, { 800, 15 }, { 1000, 15 }, { 1500, 15 }, { 1700, 30 }, { 2000, 15 }, { 2800, 30 },
		{ 3000, 30 } };

	System.out.println("Press ENTER to start experiment:");
	System.in.read();
	logger.info("Experiment started");

	for (int j = 0; j < 14; j++) {

	    long rate = rateVector[j][0];
	    long time = rateVector[j][1];

	    long timeCounter = 0;
	    String travelAgencyWsdlLocation = travelAgencyURI + "?wsdl";

	    String namespace = "http://choreos.ow2.org/";
	    String local = "TravelAgencyServiceService";

	    QName travelAgencyNamespace = new QName(namespace, local);
	    TravelAgencyServiceService travel = new TravelAgencyServiceService(new URL(travelAgencyWsdlLocation),
		    travelAgencyNamespace);

	    TravelAgencyService wsClient = travel.getTravelAgencyServicePort();

	    logger.info("Starting loop; rate = " + rate + "ms; time = " + time);
	    while (timeCounter < time * TIME_UNIT_IN_MS) {
		timeCounter += rate;
		wsClient.buyTrip();
		Thread.sleep(rate);
	    }
	    logger.info("Finished loop; rate = " + rate + "ms; time = " + time);
	}

	logger.info("Experiment finished");
    }

    public static void main(String[] args) {
	try {
	    new AirlineStress().shouldRunExperiment();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
