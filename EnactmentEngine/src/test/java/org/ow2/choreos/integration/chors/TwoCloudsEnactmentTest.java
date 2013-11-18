/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This test will enact a choreography with two services, with a service
 * depending on the other.
 * 
 * Before the test, make sure that you have two owners configured in
 * clouds.properties named according the values of the constants
 * OWNER_FOR_AIRLINE and OWNER_FOR_TRAVEL
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class TwoCloudsEnactmentTest extends SimpleChorEnactmentTest {

    private static final String OWNER_FOR_TRAVEL = "AWS_ACCOUNT";
    private static final String OWNER_FOR_AIRLINE = "AWS_ACCOUNT_COPY";

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    @Override
    public void setUp() {
	super.setUp();
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	DeployableServiceSpec airlineSpec = models.getAirlineSpec();
	DeployableServiceSpec travelSpec = models.getTravelSpec();

	travelSpec.setOwner(OWNER_FOR_TRAVEL);
	airlineSpec.setOwner(OWNER_FOR_AIRLINE);
    }
}
