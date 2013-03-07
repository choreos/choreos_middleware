package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertEquals;

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
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class JARDeployTest {

	public static final String JAR_LOCATION = "http://valinhos.ime.usp.br:54080/services/airline-service.jar";
	
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
		
		Configuration.set("BUS", "false");
		
		spec.setName("AIRLINE");
		spec.setPackageUri(JAR_LOCATION);
		spec.setPackageType(PackageType.COMMAND_LINE);
		spec.setEndpointName("airline");
		spec.setPort(1234);
	}

	@Test
	public void shouldDeployAJarServiceInANode() throws Exception {

		Service service = deployer.deploy(spec);
		ServiceInstance instance = service.getInstances().get(0);
		npm.upgradeNode(instance.getNode().getId());
		Thread.sleep(1000);

		String url = instance.getNativeUri();
		System.out.println("Service at " + url);
		String wsdl = url.replaceAll("/$", "").concat("?wsdl");
		client = WebClient.create(wsdl);
		Response response = client.get();
		assertEquals(200, response.getStatus());
	}
}
