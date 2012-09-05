package org.ow2.choreos.servicedeployer;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.npm.NPMImpl;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;

public class JARDeployTest {

	// a known jar file
	public static String JAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/simplews.jar";

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
		
		spec.setCodeUri(JAR_LOCATION);
		spec.setType(ServiceType.COMMAND_LINE);
		spec.setEndpointName("");
		spec.setPort(8042);
	}

	@Test
	public void shouldDeployAJarServiceInANode() throws Exception {

		Service service = deployer.deploy(spec);
		String url = service.getUri();
		System.out.println("Service at " + url);
		npm.upgradeNodes();
		Thread.sleep(1000);
		client = WebClient.create(url);
		String body = client.get(String.class);
		String excerpt = "hello, world";
		assertTrue(body.contains(excerpt));
	}
}
