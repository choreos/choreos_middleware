/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.rest.Owners;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This test will enact a choreography with two services, with a service
 * depending on the other.
 * 
 * Before the test, start two DeploymentManager servers: one in the port 9100
 * and the other in the port 9101
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class TwoCloudsEnactmentTest extends SimpleChorEnactmentTest {
    
    private static final String BUS_PROPERTY = "BUS";

    private static final String DEPLOYER1 = "Deployer1";
    private static final String DEPLOYER2 = "Deployer2";
    private static final String DM_URI_1 = "http://localhost:9100/deploymentmanager";
    private static final String DM_URI_2 = "http://localhost:9101/deploymentmanager";

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    @Override
    public void setUp() {

	ChoreographyDeployerConfiguration.set(BUS_PROPERTY, "false");
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	DeployableServiceSpec airlineSpec = models.getAirlineSpec();
	DeployableServiceSpec travelSpec = models.getTravelSpec();

	travelSpec.setOwner(DEPLOYER2);
	airlineSpec.setOwner(DEPLOYER1);
	Owners.set(DEPLOYER1, DM_URI_1);
	Owners.set(DEPLOYER2, DM_URI_2);
    }
}
