package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.deployment.rest.DeploymentManagerServer;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.rest.ServicesClient;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This is the same that JARDeployTest,
 * but now we using the REST API.
 * 
 * @author leonardo
 *
 */
@Category(IntegrationTest.class)
public class ClientTest {

	public static final String JAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/simplews.jar";
	
	private static String deploymentManagerHost;
	
	private NodePoolManager npm;
	private ServiceDeployer deployer;

	private WebClient client;
	private ServiceSpec spec = new ServiceSpec();

	@BeforeClass
	public static void configureLog() throws InterruptedException {
		
		LogConfigurator.configLog();
		DeploymentManagerServer.start();
        deploymentManagerHost = DeploymentManagerServer.URL;
	}
	
	@Before
	public void setUp() throws Exception {
		
		Configuration.set("BUS", "false");
		
		npm = new NodesClient(deploymentManagerHost);
		deployer = new ServicesClient(deploymentManagerHost);
		
		spec.setName("simplews");
		spec.setPackageUri(JAR_LOCATION);
		spec.setPackageType(PackageType.COMMAND_LINE);
		spec.setEndpointName("");
		spec.setPort(8042);
	}
	
	@AfterClass
	public static void stopServer() {
		DeploymentManagerServer.stop();
	}

	@Test
	public void shouldDeployAWarServiceInANode() throws Exception {

		Service service = deployer.deploy(spec);
		
		// now get the first instance
		ServiceInstance instance = service.getInstances().get(0);
		
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
