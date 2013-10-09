/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.deployment.services;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.LocationsTest;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.services.ServicesManagerImpl;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class CDDeployTest {

    // a known CD configuration file
    public static String CD_LOCATION = LocationsTest.get("CD_LOCATION");

    private String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
    private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
    private ServicesManager deployer = new ServicesManagerImpl();

    private WebClient client;
    private DeployableServiceSpec spec = new DeployableServiceSpec();

    @BeforeClass
    public static void configureLog() {
        LogConfigurator.configLog();
    }

    @Before
    public void setUp() throws Exception {
        spec.setName("MyCD");
        spec.setPackageUri(CD_LOCATION);
        spec.setPackageType(PackageType.EASY_ESB);
        spec.setEndpointName("CD-client-selfcheckoutmachine"); 
        // endpointName must match with the port name defined on the WSDL
        // and the EndpointName defined on the config.xml
    }

    @Test
    public void shouldDeployCDInEasyESBNode() throws Exception {

        DeployableService service = deployer.createService(spec);
        CloudNode node = service.getSelectedNodes().iterator().next();
        npm.updateNode(node.getId());
        Thread.sleep(5000);

        ServiceInstance instance = service.getInstances().get(0);
        String url = instance.getNativeUri();
        System.out.println("Instance at " + url);
        String wsdl = url.substring(0, url.length() - 1) + "?wsdl";
        System.out.println("Checking " + wsdl);
        client = WebClient.create(wsdl);
        Response response = client.get();
        assertEquals(200, response.getStatus());
    }

}
