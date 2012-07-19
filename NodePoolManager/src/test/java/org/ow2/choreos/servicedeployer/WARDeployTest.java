package org.ow2.choreos.servicedeployer;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.npm.Configuration;
import org.ow2.choreos.npm.NPMImpl;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.npm.datamodel.ResourceImpact;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;

public class WARDeployTest {

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
		
		specWar.setCodeUri(WAR_LOCATION);
		specWar.setType(ServiceType.WAR);
		specWar.setResourceImpact(resourceImpact);
	}

	@Test
	public void shouldDeployAWarServiceInANode() throws Exception {

		Service service = deployer.deploy(specWar);
		String url = service.getUri();
		System.out.println("Service at " + url);
		npm.upgradeNodes();
		Thread.sleep(1000);
		client = WebClient.create(url); 
		String body = client.get(String.class);
		String excerpt = "myServletWAR Web Application";
		assertTrue(body.contains(excerpt));
	}

}