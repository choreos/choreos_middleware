package org.ow2.choreos.deployment.services.integration;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.ServiceDeployerImpl;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class JARDeployTest {

	// a known jar file
	public static final String JAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/simplews.jar";
	
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	private ServiceDeployer deployer = new ServiceDeployerImpl(npm);

	private WebClient client;
	private ServiceSpec spec = new ServiceSpec();
	
	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() throws Exception {
		
		spec.setName("simplews");
		spec.setDeployableUri(JAR_LOCATION);
		spec.setArtifactType(ArtifactType.COMMAND_LINE);
		spec.setEndpointName("");
		spec.setPort(8042);
	}

	@Test
	public void shouldDeployAJarServiceInANode() throws Exception {

		Service service = deployer.deploy(spec);
		
		ServiceInstance instance = service.getInstances().get(0);
		
		String url = instance.getUri();
		System.out.println("Service at " + url);
		npm.upgradeNode(instance.getNodeId());
		Thread.sleep(1000);
		client = WebClient.create(url);
		String body = client.get(String.class);
		String excerpt = "hello, world";
		assertTrue(body.contains(excerpt));
	}
}
