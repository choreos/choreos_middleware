package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.ServiceDeployerImpl;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class WARDeployTest {

	private Logger logger = Logger.getLogger(WARDeployTest.class);
	
	// a known war file
	public static String WAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war";
	
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	private ServiceDeployer deployer = new ServiceDeployerImpl(npm);

	private WebClient client;
	private ServiceSpec specWar = new ServiceSpec();
	private ResourceImpact resourceImpact = new ResourceImpact();

	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() throws Exception {
		
		specWar.setName("myServletWAR");
		specWar.setCodeUri(WAR_LOCATION);
		specWar.setArtifactType(ArtifactType.TOMCAT);
		specWar.setResourceImpact(resourceImpact);
	}

	@Test
	public void shouldDeployAWarServiceInANode() throws Exception {

		Service service = deployer.deploy(specWar);
		String url = service.getUri();
		logger.info("Service at " + url);
		npm.upgradeNode(service.getNodeId());
		Thread.sleep(1000);
		client = WebClient.create(url); 
		String body = client.get(String.class);
		String excerpt = "myServletWAR Web Application";
		assertTrue(body.contains(excerpt));
	}

}
