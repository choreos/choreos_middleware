package org.ow2.choreos.servicedeployer;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.Locations;
import org.ow2.choreos.npm.NPMImpl;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
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
		spec.setCodeUri(CD_LOCATION);
		spec.setType(ServiceType.EASY_ESB);
		spec.setEndpointName("CDWeatherForecastServicePort"); // configured in the config.xml
	}

	@Test
	public void shouldDeployCDInEasyESBNode() throws Exception {

		Service service = deployer.deploy(spec);
		String url = service.getUri();
		System.out.println("Service at " + url);
		npm.upgradeNode(service.getNodeId());
		Thread.sleep(1000);
		client = WebClient.create(url + "?wsdl");
		Response response = client.get();
		assertEquals(200, response.getStatus());
	}



}
