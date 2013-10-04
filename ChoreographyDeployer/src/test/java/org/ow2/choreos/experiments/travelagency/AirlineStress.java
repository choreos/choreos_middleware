package org.ow2.choreos.experiments.travelagency;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
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
import org.ow2.choreos.chors.reconfiguration.EnactmentEngineGlimpseConsumerService;
import org.ow2.choreos.chors.rest.ChorDeployerServer;
import org.ow2.choreos.experiments.travelagency.client.TravelAgencyService;
import org.ow2.choreos.experiments.travelagency.client.TravelAgencyServiceService;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;

import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

@Category(IntegrationTest.class)
public class AirlineStress {

    private static final String BUS_PROPERTY = "BUS";

    private ChoreographyDeployer enactmentEngine;

    private ChoreographySpec chorSpec;
    private ModelsForTest models;
    private Choreography chor;

    Logger logger = Logger.getLogger(AirlineStress.class);

    private static ChorDeployerServer server;

    @BeforeClass
    public static void startConfig() {
	ModelsForTest.AIRLINE_JAR = "http://www.ime.usp.br/~tfurtado/downloads/airline.jar";
	ModelsForTest.TRAVEL_AGENCY_JAR = "http://www.ime.usp.br/~tfurtado/downloads/travelagency.jar";
	ModelsForTest.AIRLINE_WAR = "http://www.ime.usp.br/~tfurtado/downloads/airline.war";
	ModelsForTest.TRAVEL_AGENCY_WAR = "http://www.ime.usp.br/~tfurtado/downloads/travelagency.war";

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
	String[] args = null;
	EnactmentEngineGlimpseConsumerService.main(args);

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

	String travelAgencyURI = ((DeployableService) chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY))
		.getInstances().get(0).getNativeUri();

	long TIME_UNIT_IN_MS = 12 * 1000; // 60 * 1000 = 1 minute; 12 * 1000 =
					  // 12
					  // minutes

	long rateVector[][] = { { 1800, 30 }, { 1500, 10 }, { 1300, 10 }, { 1100, 10 }, { 900, 15 }, { 700, 15 },
		{ 1000, 15 }, { 1500, 15 }, { 1700, 30 }, { 2000, 15 }, { 2800, 15 }, { 3000, 10 } };

	System.out.println("Press ENTER to start experiment:");
	System.in.read();
	logger.info("Experiment started");
	for (int j = 0; j < 12; j++) {

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
}
