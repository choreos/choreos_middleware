/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
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
 * This test will enact a choreography with two services, with a service
 * depending on the other.
 * 
 * Before the test, start the DeploymentManager
 * 
 * @author leonardo, tfmend, nelson
 * 
 */
@Category(IntegrationTest.class)
public class SimpleChorEnactmentTest {
    
    private static final String BUS_PROPERTY = "BUS";

    protected ChoreographySpec chorSpec;

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() {

	ChoreographyDeployerConfiguration.set(BUS_PROPERTY, "false");
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	chorSpec = models.getChorSpec();
    }

    @Test
    public void shouldEnactChoreography() throws Exception {

	ChoreographyDeployer ee = new ChoreographyDeployerImpl();

	String chorId = ee.createChoreography(chorSpec);
	Choreography chor = ee.enactChoreography(chorId);

	ChoreographyService travel = chor.getServiceByChorServiceSpecName(ModelsForTest.TRAVEL_AGENCY);
	WSClient client = new WSClient(travel.getService().getUris().get(0) + "?wsdl");
	Item response = client.request("buyTrip");
	String codes = response.getChild("return").getContent();

	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
    }
}
