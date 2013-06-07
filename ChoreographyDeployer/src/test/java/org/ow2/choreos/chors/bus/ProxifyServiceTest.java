/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.bus.selector.ESBNodeFactory;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpgradedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.services.ServiceNotDeployedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.client.ServicesClient;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import esstar.petalslink.com.service.management._1_0.ManagementException;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

/**
 * Deploys a service and a EasyESB node and proxify the service
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class ProxifyServiceTest {

    private static final String DEPLOYMENT_MANAGER_URI_PROPERTY = "DEPLOYMENT_MANAGER_URI";

    private ModelsForTest models;
    private NodePoolManager npm;
    private ServicesManager sd;
    private ServiceInstance serviceInstance;
    private EasyESBNode esbNode;
    private String proxifiedUrl;

    @BeforeClass
    public static void configureLog() {
	LogConfigurator.configLog();
    }

    @Before
    public void setup() {
	models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	String host = ChoreographyDeployerConfiguration.get(DEPLOYMENT_MANAGER_URI_PROPERTY);
	npm = new NodesClient(host);
	sd = new ServicesClient(host);
    }

    @Test
    public void shouldProxifyAService() throws Exception {

	deployService();
	deployEsbNode();
	proxifyService();

	checkWSDLIsOnline();
	invokeService();
    }

    private void deployService() throws ServiceNotDeployedException, NodeNotUpgradedException, NodeNotFoundException {
	ServiceSpec airlineSpec = models.getAirlineSpec();
	serviceInstance = sd.createService((DeployableServiceSpec) airlineSpec).getInstances().get(0);
	Node node = serviceInstance.getNode();
	npm.updateNode(node.getId());
    }

    private void deployEsbNode() throws ObjectCreationException {
	ESBNodeFactory factory = new ESBNodeFactory(npm);
	esbNode = factory.createNewInstance();
    }

    private void proxifyService() {
	ServiceInstanceProxifier proxifier = new ServiceInstanceProxifier();
	try {
	    proxifiedUrl = proxifier.proxify(serviceInstance, esbNode);
	} catch (ManagementException e) {
	    System.out.println(e);
	    fail();
	}
    }

    private void checkWSDLIsOnline() {
	String wsdl = proxifiedUrl + "?wsdl";
	System.out.println("Accessing " + wsdl);
	WebClient client = WebClient.create(wsdl);
	Response response = client.get();
	assertEquals(200, response.getStatus());
    }

    private void invokeService() throws XmlException, IOException, FrameworkException, WSDLException,
	    InvalidOperationNameException, NoSuchFieldException {
	String wsdl = proxifiedUrl + "?wsdl";
	WSClient wsClient = new WSClient(wsdl);
	wsClient.setEndpoint(proxifiedUrl);
	System.out.println("Accessing buyFlight of " + wsdl);
	Item responseItem = wsClient.request("buyFlight");
	String ticketNumber = responseItem.getChild("return").getContent();
	assertTrue(ticketNumber.startsWith("33"));
    }

}
