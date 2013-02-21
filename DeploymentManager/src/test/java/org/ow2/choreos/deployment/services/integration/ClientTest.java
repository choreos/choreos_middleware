package org.ow2.choreos.deployment.services.integration;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.deployment.rest.DeploymentManagerServer;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.rest.ServicesClient;
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
	
	private static String deploymentManagerHost;
	
	private NodePoolManager npm;
	private ServiceDeployer deployer;

	private WebClient client;
	private ServiceSpec specWar = new ServiceSpec();
	private ResourceImpact resourceImpact = new ResourceImpact();

	@BeforeClass
	public static void configureLog() throws InterruptedException {
		
		LogConfigurator.configLog();
		DeploymentManagerServer.start();
        deploymentManagerHost = DeploymentManagerServer.URL;
	}
	
	@Before
	public void setUp() throws Exception {
		
		npm = new NodesClient(deploymentManagerHost);
		deployer = new ServicesClient(deploymentManagerHost);
		
		specWar.setName("myServletWAR");
		specWar.setDeployableUri(WAR_LOCATION);
		specWar.setArtifactType(ArtifactType.TOMCAT);
		specWar.setResourceImpact(resourceImpact);
	}
	
	@AfterClass
	public static void stopServer() {
		DeploymentManagerServer.stop();
	}

	@Test
	public void shouldDeployAWarServiceInANode() throws Exception {

		Service service = deployer.deploy(specWar);
		
		// now get the first instance
		ServiceInstance instance = service.getInstances().get(0);
		
		String url = instance.getUri();
		System.out.println("Service at " + url);
		npm.upgradeNode(instance.getNodeId());
		Thread.sleep(1000);
		client = WebClient.create(url); 
		String body = client.get(String.class);
		String excerpt = "myServletWAR Web Application";
		assertTrue(body.contains(excerpt));
	}

}
