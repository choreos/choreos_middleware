package org.ow2.choreos.deployment.services.bus;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.ServiceDeployerImpl;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import esstar.petalslink.com.service.management._1_0.ManagementException;

/**
 * Deploys a service and a EasyESB node and proxify the service 
 * @author leonardo
 *
 */
@Category(IntegrationTest.class)
public class ProxifyServiceTest {
	
	private static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/chordeployer/samples/v1-2/airline-service.jar";

	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}
	
	private ServiceSpec getSpec() {
		
		ServiceSpec airlineSpec = new ServiceSpec();
		airlineSpec.setName("airline");
		airlineSpec.setPackageUri(AIRLINE_JAR);
		airlineSpec.setType(ServiceType.SOAP);
		airlineSpec.setEndpointName("airline");
		airlineSpec.setPort(1234);
		airlineSpec.setPackageType(PackageType.COMMAND_LINE);
		
		return airlineSpec;
	}

	@Test
	public void shouldProxifyAService() throws ServiceNotDeployedException, NodeNotUpgradedException, NodeNotFoundException, NoBusAvailableException, ManagementException {
		
		String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
		NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
		ServiceDeployer sd = new ServiceDeployerImpl(npm);
		
		ServiceSpec airlineSpec = this.getSpec();
		ServiceInstance service = sd.deploy(airlineSpec).getInstances().get(0);
		Node node = service.getNode();
		npm.upgradeNode(node.getId());
		
		BusHandler busHandler = new SimpleBusHandler(npm);
		EasyESBNode esbNode = busHandler.retrieveBusNode();

		ServiceProxifier proxifier = new ServiceProxifier();
		String url = proxifier.proxify(service, esbNode);
		
		String wsdl = url + "?wsdl";
		System.out.println("Acessando " + wsdl);
		WebClient client = WebClient.create(wsdl);
		Response response = client.get();
		assertEquals(200, response.getStatus());
	}
}
