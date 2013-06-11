/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.nodes.datamodel.MemoryType;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * 
 * @author tfmend, nelson
 * 
 */
@Category(IntegrationTest.class)
public class VerticalScalingTest {

    private ChoreographySpec smallSpec;
    private ChoreographySpec mediumSpec;

    /**
     * Needs to be manually defined with same ip addrress according to the first
     * medium ip in the DeploymentManager properties file
     */
    private static final String MEDIUM_VM_IP = "192.168.122.14";

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() {

	ResourceImpact smallImpact = new ResourceImpact();
	smallImpact.setMemory(MemoryType.SMALL);
	ModelsForTest smallModels = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, smallImpact);
	smallSpec = smallModels.getChorSpec();

	ResourceImpact mediumImpact = new ResourceImpact();
	smallImpact.setMemory(MemoryType.MEDIUM);
	ModelsForTest mediumModels = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, mediumImpact);
	mediumSpec = mediumModels.getChorSpec();
    }

    @Test
    public void shouldMigrateAirlineServiceFromSmallToMediumMachine() throws Exception {

	ChoreographyDeployer ee = new ChoreographyDeployerImpl();

	String chorId = ee.createChoreography(smallSpec);
	Choreography chor = ee.enactChoreography(chorId);

	DeployableService airline = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);
	DeployableService travel = chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY);

	WSClient client = new WSClient(travel.getUris().get(0) + "?wsdl");

	String codes = "";

	Item response = client.request("buyTrip");
	codes = response.getChild("return").getContent();

	assertEquals(1, airline.getUris().size());
	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));

	ee.updateChoreography(chorId, mediumSpec);
	chor = ee.enactChoreography(chorId);
	Thread.sleep(4000);

	airline = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);
	travel = chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY);

	client = new WSClient(travel.getUris().get(0) + "?wsdl");

	response = client.request("buyTrip");
	codes = response.getChild("return").getContent();

	assertEquals(1, airline.getUris().size());
	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));

	String actualIp = airline.getUris().get(0);

	Matcher m = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}").matcher(actualIp);
	if (m.find()) {
	    assertEquals(MEDIUM_VM_IP, m.group());
	} else {
	    fail("Invalid IP");
	}
    }

}
