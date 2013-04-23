package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class JARDeployTest {

	public static final String JAR_LOCATION = "http://valinhos.ime.usp.br:54080/services/airline-service.jar";
	
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	private ServicesManager deployer = new ServicesManagerImpl(npm);

	private WebClient client;
	private DeployedServiceSpec spec = new DeployedServiceSpec();
	
	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() throws Exception {
		
		spec.setPackageUri(JAR_LOCATION);
		spec.setPackageType(PackageType.COMMAND_LINE);
		spec.setEndpointName("airline");
		spec.setPort(1234);
	}

	@Test
	public void shouldDeployAJarServiceInANode() throws Exception {

		DeployedService service = deployer.createService(spec);
		ServiceInstance instance = service.getInstances().get(0);
		npm.upgradeNode(instance.getNode().getId());
		Thread.sleep(1000);

		String url = instance.getNativeUri();
		assertNotNull(url);
		System.out.println("Service at " + url);
		String wsdl = url.replaceAll("/$", "").concat("?wsdl");
		client = WebClient.create(wsdl);
		Response response = client.get();
		assertEquals(200, response.getStatus());
	}
}
