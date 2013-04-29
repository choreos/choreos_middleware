package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.nodes.datamodel.Node;
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

/**
 * Deploys a service and a EasyESB node and proxify the service
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class ProxifyServiceTest {

	ModelsForTest models = new ModelsForTest(ServiceType.SOAP,
			PackageType.COMMAND_LINE);

	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}

	@Test
	public void shouldProxifyAService() throws Exception {

		String host = Configuration.get(Option.DEPLOYMENT_MANAGER_URI);
		NodePoolManager npm = new NodesClient(host);
		ServicesManager sd = new ServicesClient(host);

		ServiceSpec airlineSpec = models.getAirlineSpec();
		ServiceInstance service = sd
				.createService((DeployableServiceSpec) airlineSpec)
				.getInstances().get(0);
		Node node = service.getNode();
		npm.upgradeNode(node.getId());

		BusHandler busHandler = new SingleBusHandler(npm);
		EasyESBNode esbNode = busHandler.retrieveBusNode();

		ServiceInstanceProxifier proxifier = new ServiceInstanceProxifier();
		String url = null;
		try {
			url = proxifier.proxify(service, esbNode);
		} catch (ManagementException e) {
			System.out.println(e);
			fail();
		}

		// check WSDL is online
		String wsdl = url + "?wsdl";
		System.out.println("Accessing " + wsdl);
		WebClient client = WebClient.create(wsdl);
		Response response = client.get();
		assertEquals(200, response.getStatus());

		// invoke the service
		WSClient wsClient = new WSClient(wsdl);
		wsClient.setEndpoint(url);
		System.out.println("Accessing buyFlight of " + wsdl);
		Item responseItem = wsClient.request("buyFlight");
		String ticketNumber = responseItem.getChild("return").getContent();
		assertTrue(ticketNumber.startsWith("33"));
	}
}
