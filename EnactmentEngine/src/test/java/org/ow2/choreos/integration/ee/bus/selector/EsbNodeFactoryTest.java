/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.ee.bus.selector;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.ee.bus.EasyESBNode;
import org.ow2.choreos.ee.bus.selector.ESBNodeFactory;
import org.ow2.choreos.ee.config.CloudConfiguration;
import org.ow2.choreos.ee.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This test will create an EasyESB node on the cloud. Before run the test,
 * start the deployment manager.
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class EsbNodeFactoryTest {

    private static final String CLOUD_ACCOUNT = CloudConfiguration.DEFAULT;

    @BeforeClass
    public static void configureLog() {
	LogConfigurator.configLog();
    }

    @Test
    public void sholdCreateEasyESBNode() throws ObjectCreationException {
	NodePoolManager npm = NPMFactory.getNewNPMInstance(CLOUD_ACCOUNT);
	ESBNodeFactory factory = new ESBNodeFactory(npm);
	EasyESBNode esbNode = factory.createNewInstance(new ResourceImpact());
	String endpoint = esbNode.getAdminEndpoint();
	String url = endpoint + "?wsdl";
	System.out.println("Acessando " + url);
	WebClient client = WebClient.create(url);
	Response response = client.get();
	assertEquals(200, response.getStatus());
    }

}
