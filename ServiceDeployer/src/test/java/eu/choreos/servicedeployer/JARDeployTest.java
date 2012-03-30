package eu.choreos.servicedeployer;

import static org.junit.Assert.assertTrue;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;
import eu.choreos.servicedeployer.npm.NodePoolManagerClient;

public class JARDeployTest {

	// a known jar file
	private static String JAR_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/simplews.jar";
	
	private WebClient client;
	private ServiceDeployer deployer;
	private ServiceSpec spec = new ServiceSpec();
	private Service service;

	@Before
	public void setUp() throws Exception {
		
		spec.setCodeUri(JAR_LOCATION);
		spec.setType("JAR");
		spec.setName("");
		spec.setPort("8042");
		deployer = new ServiceDeployer(new NodePoolManagerClient());
	}

	@Test
	public void shouldDeployAJarServiceInANode() throws Exception {

		service = new Service(spec);

		String url = deployer.deploy(service);
		
		Thread.sleep(15000);
		client = WebClient.create(url);

		String body = client.get(String.class);
		
		String excerpt = "hello, world";
		assertTrue(body.contains(excerpt));
	}

}
