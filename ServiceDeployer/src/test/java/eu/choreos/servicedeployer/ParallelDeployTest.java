package eu.choreos.servicedeployer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;
import eu.choreos.servicedeployer.npm.NodePoolManagerClient;

/**
 * Verify if the system works with 
 * multiple parallel requests
 * 
 * @author leonardo
 *
 */
public class ParallelDeployTest {
	
	private ServiceDeployer deployer;
	private ServiceSpec[] specs = new ServiceSpec[2];
	private Service[] services = new Service[2];
	
	@Before
	public void setUp() throws Exception {
		
		deployer = new ServiceDeployer(new NodePoolManagerClient());

		specs[0] = new ServiceSpec();
		specs[0].setCodeUri(JARDeployTest.JAR_LOCATION);
		specs[0].setType("JAR");
		specs[0].setEndpointName("");
		specs[0].setPort("8042");

		specs[1] = new ServiceSpec();
		specs[1].setCodeUri(WARDeployTest.WAR_LOCATION);
		specs[1].setType("WAR");
	}
	
	@Test
	public void shouldMakeParallelDeploys() throws InterruptedException {
		
		services[0] = new Service(specs[0]);
		services[1] = new Service(specs[1]);
		
		Thread[] ts = new Thread[2];
		for (int i=0; i<2; i++) {
			 ts[i] = new Thread(new TestDeployer(services[i]));
			 ts[i].start();
		}
		
		// wait for other threads to finish
		for (Thread t: ts) {
			t.join();
		}
	}

	private class TestDeployer implements Runnable {

		Service service;
		
		public TestDeployer(Service service) {
			this.service = service;
		}
		
		@Override
		public void run() {

			String url = null;
			try {
				System.out.println("Deploying " + service.getId());
				url = deployer.deploy(service);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				fail();
			}
			System.out.println("Service deployed at " + url);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				fail();
			}
			WebClient client = WebClient.create(url);
			Response response = client.get();
			assertEquals(200, response.getStatus());			
		}
	}
}
