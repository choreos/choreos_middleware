package org.ow2.choreos.integration.chors.reconfiguration;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Scanner;

import org.apache.xmlbeans.XmlException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.reconfiguration.SimpleLogger;
import org.ow2.choreos.chors.reconfiguration.SimpleLoggerImpl;
import org.ow2.choreos.chors.rest.ChorDeployerServer;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;

import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

@Category(IntegrationTest.class)
public class AirlineStress {

    private static final String BUS_PROPERTY = "BUS";

    // private EnactmentEngineGlimpseConsumerService glimpseConsumerService;
    private ChoreographyDeployer enactmentEngine;

    private ChoreographySpec chorSpec;
    private ModelsForTest models;
    private Choreography chor;

    SimpleLogger logger = new SimpleLoggerImpl("experiments.log");

    private static ChorDeployerServer server;

    @BeforeClass
    public static void startConfig() {
	server = new ChorDeployerServer();
	server.start();
    }

    @AfterClass
    public static void shutDownServers() {
	server.stop();
    }

    @Before
    public void setUp() {
	ChoreographyDeployerConfiguration.set(BUS_PROPERTY, "false");

	enactmentEngine = new ChorDeployerClient("http://localhost:9102/choreographydeployer/");
	// glimpseConsumerService = new EnactmentEngineGlimpseConsumerService();
	// glimpseConsumerService.execute();

	models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT);
	chorSpec = models.getChorSpec();
    }

    @Test
    public void shouldRunExperiment() throws Exception {

	String chorId = enactmentEngine.createChoreography(chorSpec);
	chor = enactmentEngine.enactChoreography(chorId);

	runExperiment();

	assertTrue(true);
    }

    private void runExperiment() throws XmlException, IOException, FrameworkException, WSDLException,
	    InvalidOperationNameException, InterruptedException, NoSuchFieldException {

	Scanner scanner = new Scanner(System.in);
	Scanner sc = scanner;

	String travelAgencyURI = ((DeployableService) chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY))
		.getInstances().get(0).getNativeUri();

	long TIME_UNIT_IN_MS = 30 * 1000; // 60 * 1000 = 1 minute; 12 * 1000 =
					  // 12
					  // minutes

	long rateVector[][] = { { 2000, 30 }, { 1900, 10 }, { 1800, 10 }, { 1500, 10 }, { 1300, 15 }, { 1100, 15 },
		{ 1200, 15 }, { 1500, 15 }, { 1700, 30 }, { 2000, 15 }, { 2800, 15 }, { 3000, 10 } };

	System.out.println("Press ENTER to start experiment:");
	System.in.read();
	logger.info("Experiment started");
	for (int j = 0; j < 12; j++) {

	    long rate = rateVector[j][0];
	    long time = rateVector[j][1];

	    long timeCounter = 0;

	    logger.info("Starting loop; rate = " + rate + "ms; time = " + time);
	    while (timeCounter < time * TIME_UNIT_IN_MS) {
		timeCounter += rate;
		new WSClient(travelAgencyURI + "?wsdl").request("buyTrip").getChild("return").getContent();
		Thread.sleep(rate);
	    }
	    logger.info("Finished loop; rate = " + rate + "ms; time = " + time);
	}

	logger.info("Experiment finished");
    }
}
