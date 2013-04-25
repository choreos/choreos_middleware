package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.rest.ChorDeployerServer;
import org.ow2.choreos.deployment.services.datamodel.DeployableService;
import org.ow2.choreos.ee.api.ChoreographyServiceSpec;
import org.ow2.choreos.ee.api.ChoreographySpec;
import org.ow2.choreos.ee.api.PackageType;
import org.ow2.choreos.ee.api.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * It is the same than SimpleEnactmentTest, but using the REST API.
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer.
 * 
 * This test will enact a choreography with two services, with a service
 * depending on the other.
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class RestEnactmentTest {

	private static final String AIRLINE = ModelsForTest.AIRLINE;
	private static final String TRAVEL_AGENCY = ModelsForTest.TRAVEL_AGENCY;

	private static ChorDeployerServer server;
	private ChoreographySpec chorSpec;

	private ModelsForTest models;

	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
		server = new ChorDeployerServer();
		server.start();
	}

	@AfterClass
	public static void shutDownServers() {
		server.stop();
	}

	@Before
	public void setUp() {

		models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
		chorSpec = new ChoreographySpec();
		chorSpec.addChoreographyServiceSpec(models
				.getAirlineChoreographyServiceSpec());
		chorSpec.addChoreographyServiceSpec(models
				.getTravelAgencyChoreographyServiceSpec());
	}

	@Test
	public void shouldConfigureAChoreography() throws Exception {

		String host = ChorDeployerServer.URL;
		ChoreographyDeployer ee = new ChorDeployerClient(host);
		String chorId = ee.createChoreography(chorSpec);

		assertEquals("1", chorId);
	}

	@Test
	public void shouldRetrieveChoreographySpec() throws Exception {

		String host = ChorDeployerServer.URL;
		ChoreographyDeployer ee = new ChorDeployerClient(host);
		String chorId = ee.createChoreography(chorSpec);
		Choreography chor = ee.getChoreography(chorId);

		ChoreographyServiceSpec travel = chor.getRequestedChoreographySpec()
				.getChoreographyServiceSpecByChoreographyServiceUID(
						TRAVEL_AGENCY);
		assertEquals(
				chorSpec.getChoreographyServiceSpecByChoreographyServiceUID(TRAVEL_AGENCY),
				travel);

		ChoreographyServiceSpec airline = chor.getRequestedChoreographySpec()
				.getChoreographyServiceSpecByChoreographyServiceUID(AIRLINE);
		assertEquals(
				chorSpec.getChoreographyServiceSpecByChoreographyServiceUID(AIRLINE),
				airline);
	}

	@Test
	public void shouldEnactChoreography() throws Exception {

		String host = ChorDeployerServer.URL;
		ChoreographyDeployer ee = new ChorDeployerClient(host);
		String chorId = ee.createChoreography(chorSpec);
		Choreography chor = ee.enactChoreography(chorId);
		System.out.println("A chor: " + chor);

		String uri =
				((DeployableService) chor.getDeployedChoreographyServiceByChoreographyServiceUID(
						ModelsForTest.TRAVEL_AGENCY).getService()).getInstances().get(0).getNativeUri();
		WSClient client = new WSClient(uri + "?wsdl");
		Item response = client.request("buyTrip");
		String codes = response.getChild("return").getContent();

		assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
	}

}
