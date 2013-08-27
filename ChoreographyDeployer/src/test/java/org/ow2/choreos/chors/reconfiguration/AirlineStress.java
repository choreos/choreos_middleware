package org.ow2.choreos.chors.reconfiguration;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.rest.ChorDeployerServer;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class AirlineStress {

    private static final String BUS_PROPERTY = "BUS";

    private static final String AIRLINE = ModelsForTest.AIRLINE;
    private static final String TRAVEL_AGENCY = ModelsForTest.TRAVEL_AGENCY;
    private ChoreographySpec chorSpec;

    private ModelsForTest models;

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @AfterClass
    public static void shutDownServers() {

    }

    @Before
    public void setUp() {
	ChoreographyDeployerConfiguration.set(BUS_PROPERTY, "false");
	models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT);
	chorSpec = models.getChorSpec();
    }

    @Test
    public void shouldEnactChoreography() throws Exception {
	String host = ChorDeployerServer.URL;
	// ChoreographyDeployer ee = new ChorDeployerClient(host);
	ChoreographyDeployer ee = new ChoreographyDeployerImpl();
	String chorId = ee.createChoreography(chorSpec);
	Choreography chor = ee.enactChoreography(chorId);
	startSimulation(chor);
    }

    private void startSimulation(Choreography chor) throws XmlException, IOException, FrameworkException,
	    WSDLException, InvalidOperationNameException, InterruptedException {
	String uri = ((DeployableService) chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY))
		.getInstances().get(0).getNativeUri();
	WSClient client = new WSClient(uri + "?wsdl");
	for (int i = 0; i < 100000; i++) {
	    Item response = client.request("buyTrip");
	    Thread.sleep(1200);

	}

	// should start scenario with a runner
    }
}
