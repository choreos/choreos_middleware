package org.ow2.choreos.chors;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test will enact a choreography with two services,
 * with a service depending on the other.
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer
 *
 * @author leonardo
 *
 */
@Category(IntegrationTest.class)
public class WARChorEnactmentTest {

	private ChorSpec chorSpec;
	
	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() {
		
		ModelsForTest models = new ModelsForTest(ArtifactType.TOMCAT);
		chorSpec = models.getChorSpec(); 
	}
	
	@Test
	public void shouldEnactChoreography() throws Exception {
		
		ChoreographyDeployer ee = new ChorDeployerImpl();
		String chorId = ee.createChoreography(chorSpec);
		Choreography chor = ee.enact(chorId);

		Service travel = chor.getDeployedServiceByName(ModelsForTest.TRAVEL_AGENCY);
		String uri = travel.getUri();
		if (uri.endsWith("/"))
			uri = uri.substring(0, uri.length()-1);
		WSClient client = new WSClient(uri + "?wsdl");
		Item response = client.request("buyTrip");
		String codes = response.getChild("return").getContent();
		
		assertEquals("33--22", codes);
	}

}
