package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * Before run the test, start the deployment manager
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class BusHandlerTest {

    @BeforeClass
    public static void configureLog() {
	LogConfigurator.configLog();
    }

    @Test
    public void sholdRetrieveBusEndpoint() throws NoBusAvailableException {

	String host = Configuration.get(Option.DEPLOYMENT_MANAGER_URI);
	NodePoolManager npm = new NodesClient(host);
	BusHandler busHandler = new SingleBusHandler(npm);

	String endpoint = busHandler.retrieveBusNode().getAdminEndpoint();
	String url = endpoint + "?wsdl";
	System.out.println("Acessando " + url);
	WebClient client = WebClient.create(url);
	Response response = client.get();
	assertEquals(200, response.getStatus());
    }

}
