package org.ow2.choreos.deployment.services.integration;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.Locations;
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
public class CDDeployTest {
	
	// a known CD configuration file
	public static String CD_LOCATION = Locations.get("CD_WEATHER_LOCATION");
	
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
		
		spec.setName("CDWeather");
		spec.setDeployableUri(CD_LOCATION);
		spec.setArtifactType(ArtifactType.EASY_ESB);
		spec.setEndpointName("CDWeatherForecastServicePort"); // configured in the config.xml
	}

	// should display each instance of the service
	@Test
	public void shouldDeployCDInEasyESBNode() throws Exception {

		Service service = deployer.deploy(spec);
		
		assertNotNull(service);
		System.out.println(">>>> " + service.toString());
		
		ServiceInstance instance = service.getInstances().get(0);
		
		String url = instance.getUri();
		System.out.println("Instance at " + url);
		npm.upgradeNode(instance.getNodeId());
		Thread.sleep(5000);
		String wsdl = url.substring(0, url.length()-1) + "?wsdl";
		System.out.println("Checking " + wsdl);
		client = WebClient.create(wsdl);
		Response response = client.get();
		assertEquals(200, response.getStatus());
	}



}
