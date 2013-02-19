package org.ow2.choreos.deployment.services.bus;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class BusHandlerTest {

	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}

	@Test
	public void sholdRetrieveBusEndpoint() throws NoBusAvailableException {
		
		String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
		NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
		BusHandler busHandler = new SimpleBusHandler(npm);
		
		String endpoint = busHandler.retrieveBusNode().getAdminEndpoint();
		String url = endpoint + "?wsdl";
		System.out.println("Acessando " + url);
		WebClient client = WebClient.create(url);
		Response response = client.get();
		assertEquals(200, response.getStatus());
	}

}
