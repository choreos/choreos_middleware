package eu.choreos.servicedeployer;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.servicedeployer.datamodel.ResourceImpact;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;
import eu.choreos.servicedeployer.npm.NodePoolManagerClient;

public class PetalsDeployTest {

	// a known sa file
	public static String SA_LOCATION = "https://github.com/downloads/choreos/choreos_middleware/sa-HelloService-provide.zip";
	
	private WebClient client;
	private ServiceDeployer deployer;
	private ServiceSpec spec = new ServiceSpec();
	private ResourceImpact resourceImpact = new ResourceImpact();
	private Service service;

	@Before
	public void setUp() throws Exception {
		
		spec.setCodeUri(SA_LOCATION);
		spec.setType("PETALS");
		spec.setEndpointName("HelloImplPort"); // information inside JBI
		spec.setResourceImpact(resourceImpact);
		deployer = new ServiceDeployer(new NodePoolManagerClient());
	}

	@Test
	public void shouldDeployASaServiceInANode() throws Exception {

		service = new Service(spec);
		String url = deployer.deploy(service);
		System.out.println("Service at " + url);
		
		Thread.sleep(10000);
		url = url.substring(0, url.length()-1).concat("?wsdl"); // removes the ending slash
		client = WebClient.create(url);

		Response response = client.get();
		assertEquals(200, response.getStatus());
	}

}
