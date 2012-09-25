package org.ow2.choreos.servicedeployer;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.client.NPMClient;
import org.ow2.choreos.npm.datamodel.ResourceImpact;
import org.ow2.choreos.npm.rest.NPMServer;
import org.ow2.choreos.servicedeployer.client.ServiceDeployerClient;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
import org.ow2.choreos.servicedeployer.rest.ServiceDeployerServer;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This is the same that WARDeployTest,
 * but now we are testing the integration between
 * ServiceDeployer client and the Service Deployer REST API.
 * 
 * Condition: node must not have already the WAR recipe installed
 * 
 * @author leonardo
 *
 */
@Category(IntegrationTest.class)
public class ClientTest {

	// a known war file
	public static String WAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war";
	
	private static String nodePoolManagerHost;
	private static String serviceDeployerHost;
	
	private NodePoolManager npm;
	private ServiceDeployer deployer;

	private WebClient client;
	private ServiceSpec specWar = new ServiceSpec();
	private ResourceImpact resourceImpact = new ResourceImpact();

	@BeforeClass
	public static void configureLog() throws InterruptedException {
		
		LogConfigurator.configLog();
        NPMServer.start();
        nodePoolManagerHost = NPMServer.URL;
        ServiceDeployerServer.start();
        serviceDeployerHost = ServiceDeployerServer.URL;
	}
	
	@Before
	public void setUp() throws Exception {
		
		npm = new NPMClient(nodePoolManagerHost);
		deployer = new ServiceDeployerClient(serviceDeployerHost);
		
		specWar.setCodeUri(WAR_LOCATION);
		specWar.setType(ServiceType.TOMCAT);
		specWar.setResourceImpact(resourceImpact);
	}
	
	@AfterClass
	public static void stopServer() {
		NPMServer.stop();
		ServiceDeployerServer.stop();
	}

	@Test
	public void shouldDeployAWarServiceInANode() throws Exception {

		Service service = deployer.deploy(specWar);
		String url = service.getUri();
		System.out.println("Service at " + url);
		npm.upgradeNode(service.getNodeId());
		Thread.sleep(1000);
		client = WebClient.create(url); 
		String body = client.get(String.class);
		String excerpt = "myServletWAR Web Application";
		assertTrue(body.contains(excerpt));
	}

}
