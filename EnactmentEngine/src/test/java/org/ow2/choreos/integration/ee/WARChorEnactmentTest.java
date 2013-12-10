/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.ee;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.ee.config.ChoreographyDeployerConfiguration;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This test will enact a choreography with two services, with a service
 * depending on the other.
 * 
 * Before the test, start the DeploymentManager server
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class WARChorEnactmentTest extends SimpleChorEnactmentTest {

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    @Override
    public void setUp() {
	ChoreographyDeployerConfiguration.set("BUS", "false");
	ChoreographyDeployerConfiguration.set("IDLE_POOL", "false");
	// QoSManagementConfiguration.set(QoSManagementConfiguration.QOS_MGMT,
	// "False");
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT);
	super.chorSpec = models.getChorSpec();
    }

}
