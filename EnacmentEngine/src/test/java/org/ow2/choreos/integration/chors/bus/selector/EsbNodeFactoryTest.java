/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chors.bus.selector;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.chors.bus.selector.ESBNodeFactory;
import org.ow2.choreos.chors.rest.Owners;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
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

    @BeforeClass
    public static void configureLog() {
        LogConfigurator.configLog();
    }

    @Test
    public void sholdCreateEasyESBNode() throws ObjectCreationException {
        String npmUri = Owners.getDefault();
        NodePoolManager npm = new NodesClient(npmUri);
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