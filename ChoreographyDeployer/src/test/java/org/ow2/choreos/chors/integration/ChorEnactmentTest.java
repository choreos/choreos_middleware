package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test will enact a choreography with two services. One of them will serve
 * with two replicas
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer
 * 
 * @author tfmend
 * 
 */
@Category(IntegrationTest.class)
public class ChorEnactmentTest {

    private ChoreographySpec spec;

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() {

	Configuration.set(Option.BUS, "false");
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	spec = models.getChorSpecWithReplicas(2);
    }

    @Test
    public void shouldEnactChoreographyWithTwoAirlineServices() throws Exception {

	ChoreographyDeployer ee = new ChoreographyDeployerImpl();

	String chorId = ee.createChoreography(spec);
	Choreography chor = ee.enactChoreography(chorId);

	ChoreographyService airline = chor.getServiceByChorServiceSpecName(ModelsForTest.AIRLINE);
	assertEquals(2, airline.getService().getUris().size());

	ChoreographyService travel = chor.getServiceByChorServiceSpecName(ModelsForTest.TRAVEL_AGENCY);
	WSClient client = new WSClient(travel.getService().getUris().get(0) + "?wsdl");
	Item response = client.request("buyTrip");
	String codes = response.getChild("return").getContent();
	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));

	WSClient client2 = new WSClient(travel.getService().getUris().get(0) + "?wsdl");
	Item response2 = client2.request("buyTrip");
	String codes2 = response2.getChild("return").getContent();
	assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));

	assertFalse(codes.equals(codes2));

    }

}
