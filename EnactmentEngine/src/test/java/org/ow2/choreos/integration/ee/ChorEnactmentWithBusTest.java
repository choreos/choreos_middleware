/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.ee;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.ee.ChoreographyDeployerImpl;
import org.ow2.choreos.ee.config.ChoreographyDeployerConfiguration;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test will enact a choreography with two services, with a service
 * depending on the other (like SimpleChorEnactment), but using the bus to
 * integrate the services.
 * 
 * Before the test, start the DeploymentManager server. You must also configure
 * the BUS_POLICY property.
 * 
 * @author leonardo, tfmend, nelson
 * 
 */
@Category(IntegrationTest.class)
public class ChorEnactmentWithBusTest {

    private ChoreographySpec chorSpec;

    @BeforeClass
    public static void startServers() {
        LogConfigurator.configLog();
    }

    @Before
    public void setUp() {
        ChoreographyDeployerConfiguration.set("BUS", "true");
        ChoreographyDeployerConfiguration.set("IDLE_POOL", "false");
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT);
        chorSpec = models.getChorSpec();
    }

    @Test
    public void shouldEnactChoreography() throws Exception {

        ChoreographyDeployer ee = new ChoreographyDeployerImpl();

        String chorId = ee.createChoreography(chorSpec);
        Choreography chor = ee.enactChoreography(chorId);

        DeployableService airlineService = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);
        DeployableService travelService = chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY);
        ServiceInstance airlineInstance = airlineService.getInstances().get(0);
        ServiceInstance travelInstance = travelService.getInstances().get(0);
        String airlineProxifiedUri = airlineInstance.getProxification().getBusUri(ServiceType.SOAP);
        String travelProxifiedUri = travelInstance.getProxification().getBusUri(ServiceType.SOAP);
        System.out.println("airline proxified: " + airlineProxifiedUri);
        System.out.println("travel agency proxified: " + travelProxifiedUri);
        assertNotNull(airlineProxifiedUri);
        assertNotNull(travelProxifiedUri);
        assertTrue(airlineProxifiedUri.contains(":8180/services/AirlineServicePortClientProxyEndpoint"));
        assertTrue(travelProxifiedUri.contains(":8180/services/TravelAgencyServicePortClientProxyEndpoint"));

        WSClient client = new WSClient(travelProxifiedUri + "?wsdl");
        client.setEndpoint(travelProxifiedUri);
        Item response = client.request("buyTrip");
        String codes = response.getChild("return").getContent();
        assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
    }
}
