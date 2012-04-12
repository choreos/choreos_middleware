package eu.choreos.servicedeployer;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.servicedeployer.datamodel.ResourceImpact;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;
import eu.choreos.servicedeployer.npm.NodePoolManagerClient;

public class WARDeployTest {

	// a known war file
	private static String WAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war";
	
	private WebClient client;
	private ServiceDeployer deployer;
	private ServiceSpec specWar = new ServiceSpec();
	private ResourceImpact resourceImpact = new ResourceImpact();
	private Service service;

	@Before
	public void setUp() throws Exception {
		
		specWar.setCodeUri(WAR_LOCATION);
		specWar.setType("WAR");
		specWar.setResourceImpact(resourceImpact);
		deployer = new ServiceDeployer(new NodePoolManagerClient());
	}

	@Test
	public void shouldDeployAWarServiceInANode() throws Exception {

		service = new Service(specWar);
		//service.setId("myServletWAR");

		String url = deployer.deploy(service);
		System.out.println("Service at " + url);
		
		Thread.sleep(1000);
		client = WebClient.create(url); 
		String body = client.get(String.class);
		String excerpt = "myServletWAR Web Application";
		assertTrue(body.contains(excerpt));
	}

}
