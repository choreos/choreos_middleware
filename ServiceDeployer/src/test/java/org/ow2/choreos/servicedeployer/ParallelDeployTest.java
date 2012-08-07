package org.ow2.choreos.servicedeployer;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.npm.NPMImpl;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * Verify if the system works with 
 * multiple parallel requests.
 * 
 * This test works only if the node is already bootstrapped
 * 
 * @author leonardo
 *
 */
public class ParallelDeployTest {
	
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	private ServiceDeployer deployer = new ServiceDeployerImpl(npm);

	private ServiceSpec[] specs = new ServiceSpec[2];
	
	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() throws Exception {
		
		specs[0] = new ServiceSpec();
		specs[0].setCodeUri(JARDeployTest.JAR_LOCATION);
		specs[0].setType(ServiceType.JAR);
		specs[0].setEndpointName("");
		specs[0].setPort(8042);

		specs[1] = new ServiceSpec();
		specs[1].setCodeUri(WARDeployTest.WAR_LOCATION);
		specs[1].setType(ServiceType.WAR);
	}
	
	@Test
	public void shouldMakeParallelDeploys() throws InterruptedException {
		
		Thread[] ts = new Thread[2];
		TestDeployer[] tds = new TestDeployer[2];
		for (int i=0; i<2; i++) {
			tds[i] = new TestDeployer(specs[i]);
			ts[i] = new Thread(tds[i]);
			ts[i].start();
		}
		
		// wait for other threads to finish
		for (Thread t: ts) {
			t.join();
		}
		
		npm.upgradeNodes();
		Thread.sleep(1000);
		
		for (TestDeployer td: tds) {
			WebClient client = WebClient.create(td.url);
			Response response = client.get();
			assertEquals(200, response.getStatus());
		}
	}

	private class TestDeployer implements Runnable {

		ServiceSpec spec;
		String url;
		
		public TestDeployer(ServiceSpec spec) {
			this.spec = spec;
		}
		
		@Override
		public void run() {
			System.out.println("Deploying " + spec);
			Service service = deployer.deploy(spec);
			url = service.getUri();
			System.out.println("Service deployed at " + url);
		}
	}
}
