/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.rest.DeploymentManagerServer;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.client.ServicesClient;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This is the same that JARDeployTest, but now using the REST API.
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class ClientTest {

    public static final String JAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/simplews.jar";

    private static String deploymentManagerHost;
    private static DeploymentManagerServer server;

    private NodePoolManager npm;
    private ServicesManager servicesManager;

    private WebClient client;
    private DeployableServiceSpec spec = new DeployableServiceSpec();

    @BeforeClass
    public static void configureLog() throws InterruptedException {

	LogConfigurator.configLog();
	server = new DeploymentManagerServer();
	server.start();
	deploymentManagerHost = DeploymentManagerServer.URL;
    }

    @Before
    public void setUp() throws Exception {

	DeploymentManagerConfiguration.set("BUS", "false");

	npm = new NodesClient(deploymentManagerHost);
	servicesManager = new ServicesClient(deploymentManagerHost);

	spec.setPackageUri(JAR_LOCATION);
	spec.setPackageType(PackageType.COMMAND_LINE);
	spec.setEndpointName("");
	spec.setPort(8042);
    }

    @AfterClass
    public static void stopServer() {
	server.stop();
    }

    @Test
    public void shouldDeployAWarServiceInANode() throws Exception {

	Service service = servicesManager.createService(spec);

	// now get the first instance
	ServiceInstance instance = ((DeployableService) service).getInstances().get(0);

	String url = instance.getNativeUri();
	System.out.println("Service at " + url);
	npm.upgradeNode(instance.getNode().getId());
	Thread.sleep(1000);
	client = WebClient.create(url);
	String body = client.get(String.class);
	String excerpt = "hello, world";
	assertTrue(body.contains(excerpt));
    }

}
