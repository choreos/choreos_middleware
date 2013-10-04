/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.Alarm;
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
public class IncreaseNumberOfInstancesTest {
    
    private static final String BUS_PROPERTY = "BUS";

    private ChoreographySpec spec;
    private ChoreographySpec newSpec;

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() {
        ChoreographyDeployerConfiguration.set(BUS_PROPERTY, "false");
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT, 2);
	spec = models.getChorSpec();
	ModelsForTest newModels = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT, 3);
	newSpec = newModels.getChorSpec();
    }

    @Test
    public void shouldEnactChoreographyWithTwoAirlineServicesAndChangeToThree() throws Exception {

	ChoreographyDeployer ee = new ChoreographyDeployerImpl();

	String chorId = ee.createChoreography(spec);
	Choreography chor = ee.enactChoreography(chorId);

	Service airline = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);

	Service travel = chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY);
	WSClient client = new WSClient(travel.getUris().get(0) + "?wsdl");

	String codes, codes2, codes3 = "";

	Item response = client.request("buyTrip");
	codes = response.getChild("return").getContent();
	response = client.request("buyTrip");
	codes2 = response.getChild("return").getContent();

	assertEquals(2, airline.getUris().size());
	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
	assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
	assertFalse(codes.equals(codes2));

	System.in.read();

	ee.updateChoreography(chorId, newSpec);
	chor = ee.enactChoreography(chorId);

	airline = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);
	travel = chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY);
	client = new WSClient(travel.getUris().get(0) + "?wsdl");

	response = client.request("buyTrip");
	codes = response.getChild("return").getContent();
	response = client.request("buyTrip");
	codes2 = response.getChild("return").getContent();
	response = client.request("buyTrip");
	codes3 = response.getChild("return").getContent();

	assertEquals(3, airline.getUris().size());
	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
	assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
	assertTrue(codes3.startsWith("33") && codes3.endsWith("--22"));
	assertFalse(codes.equals(codes2));
	assertFalse(codes3.equals(codes));
	assertFalse(codes3.equals(codes2));
	
	Alarm alarm = new Alarm();
	alarm.play();

    }

}
