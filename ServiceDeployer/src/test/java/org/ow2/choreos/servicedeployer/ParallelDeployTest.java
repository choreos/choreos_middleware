package org.ow2.choreos.servicedeployer;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.npm.NPMImpl;
import org.ow2.choreos.npm.NodeNotFoundException;
import org.ow2.choreos.npm.NodeNotUpgradedException;
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
		specs[0].setType(ServiceType.COMMAND_LINE);
		specs[0].setEndpointName("");
		specs[0].setPort(8042);

		specs[1] = new ServiceSpec();
		specs[1].setCodeUri(WARDeployTest.WAR_LOCATION);
		specs[1].setType(ServiceType.TOMCAT);
	}
	
	@Test
	public void shouldMakeParallelDeploys() throws InterruptedException, NodeNotUpgradedException, NodeNotFoundException {
		
		Thread[] ts = new Thread[2];
		TestDeployer[] tds = new TestDeployer[2];
		for (int i=0; i<2; i++) {
			tds[i] = new TestDeployer(specs[i]);
			ts[i] = new Thread(tds[i]);
			ts[i].start();
		}
		
		waitThreads(ts);
		
		ts = new Thread[2];
		for (int i=0; i<2; i++) {
			ts[i] = new Thread(new TestUpgrader(tds[i]));
			ts[i].start();
			Thread.sleep(1000);
		}
		
		waitThreads(ts);
		
		for (TestDeployer td: tds) {
			WebClient client = WebClient.create(td.url);
			Response response = client.get();
			assertEquals(200, response.getStatus());
		}
	}

	private void waitThreads(Thread[] ts) throws InterruptedException{
		for (Thread t: ts) {
			t.join();
		}
	}

	private class TestUpgrader implements Runnable {

		TestDeployer testDeployer;
		
		public TestUpgrader(TestDeployer testDeployer) {
			this.testDeployer = testDeployer;
		}
		
		@Override
		public void run() {
			try {
				npm.upgradeNode(this.testDeployer.nodeId);
			} catch (NodeNotUpgradedException e) {
				e.printStackTrace();
			} catch (NodeNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class TestDeployer implements Runnable {

		ServiceSpec spec;
		String url;
		String nodeId;
		
		public TestDeployer(ServiceSpec spec) {
			this.spec = spec;
		}
		
		@Override
		public void run() {
			System.out.println("Deploying " + spec);
			Service service = null;
			try {
				service = deployer.deploy(spec);
			} catch (ServiceNotDeployedException e) {
				e.printStackTrace();
			}
			url = service.getUri();
			nodeId = service.getNodeId();
			System.out.println("Service deployed at " + url);
		}
	}
}
