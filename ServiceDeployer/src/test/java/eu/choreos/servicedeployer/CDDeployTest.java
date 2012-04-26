package eu.choreos.servicedeployer;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;
import eu.choreos.servicedeployer.npm.NodePoolManagerClient;

/**
 * Tests Coordination Delegate deployment
 * @author leonardo
 *
 */
public class CDDeployTest {

	// a known CD SA file
	public static String CD_SA_LOCATION = "http://valinhos.ime.usp.br:54080/demo2/sa-CD-HotelService-provide.zip";
	// a known CD consume SA file
	public static String CONSUME_SA_LOCATION = "http://valinhos.ime.usp.br:54080/demo2/sa-SOAP-HotelService-consume.zip";
	
	private ServiceSpec specCD = new ServiceSpec();
	private ServiceSpec specConsume = new ServiceSpec();
	
	@Before
	public void setUp() throws Exception {

		specCD.setCodeUri(CD_SA_LOCATION);
		specCD.setType("PETALS");
		specCD.setEndpointName("CDHotelPort"); // information inside JBI

		specConsume.setCodeUri(CONSUME_SA_LOCATION);
		specConsume.setType("PETALS");
		specConsume.setEndpointName("hotel1234"); // information inside JBI
	}

	@Test
	public void shouldDeployCDInANode() throws Exception {

		this.deployCD();
		this.deployCDConsume();
		verifyCoordelWsdl("23.22.1.171");
	}
	
	
	public void deployCD() {
		ServiceDeployer deployer = new ServiceDeployer(new NodePoolManagerClient());
		Service service = new Service(specCD);
		String url = null;
		try {
			url = deployer.deploy(service);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("CD at " + url);
	}
		
	
	public void deployCDConsume() {
		ServiceDeployer deployer = new ServiceDeployer(new NodePoolManagerClient());
		Service service = new Service(specConsume);
		String url = null;
		try {
			url = deployer.deploy(service);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("Service at " + url);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		verifyCoordelWsdl(service.getHost());
	}
		
	
	private void verifyCoordelWsdl(String hostName) {
		
		String wsdl = "http://"+ hostName +":8084/petals/services/hotel1234?wsdl";
		System.out.println("Verifying " + wsdl);
		WebClient client = WebClient.create(wsdl);

		String xml = client.get(String.class);
		System.out.println(xml);
		String excerpt = "<w:portType name=\"Hotel\">";
		assertTrue(xml.contains(excerpt));
	}

}
