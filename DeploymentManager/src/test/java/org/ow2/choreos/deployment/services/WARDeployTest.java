/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.Locations;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class WARDeployTest {

    private Logger logger = Logger.getLogger(WARDeployTest.class);

    // a known war file
    public static String WAR_LOCATION = Locations.get("AIRLINE_WAR");
    public static String ENDPOINT_NAME = "airline";

    private String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
    private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
    private ServicesManager deployer = new ServicesManagerImpl(npm);

    private WebClient client;
    private DeployableServiceSpec specWar = new DeployableServiceSpec();
    private ResourceImpact resourceImpact = new ResourceImpact();

    @BeforeClass
    public static void configureLog() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() throws Exception {

	specWar.setPackageUri(WAR_LOCATION);
	specWar.setEndpointName(ENDPOINT_NAME);
	specWar.setPackageType(PackageType.TOMCAT);
	specWar.setResourceImpact(resourceImpact);
    }

    @Test
    public void shouldDeployAWarServiceInANode() throws Exception {

	DeployableService service = deployer.createService(specWar);

	ServiceInstance instance = service.getInstances().get(0);

	String url = instance.getNativeUri();
	logger.info("Service at " + url);
	npm.updateNode(instance.getNode().getId());
	Thread.sleep(1000);

	if (url.trim().endsWith("/"))
	    url = url.trim().substring(0, url.length() - 1);

	client = WebClient.create(url + "?wsdl");
	String body = client.get(String.class);
	String excerpt = "buyFlight";
	assertTrue(body.contains(excerpt));
    }

}
