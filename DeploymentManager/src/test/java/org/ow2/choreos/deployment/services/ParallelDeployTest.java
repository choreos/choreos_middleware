package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.ServicesManagerImpl;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.tests.IntegrationTest;
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
@Category(IntegrationTest.class)
public class ParallelDeployTest {
	
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	private ServicesManager deployer = new ServicesManagerImpl(npm);

	private ServiceSpec[] specs = new ServiceSpec[2];
	
	@BeforeClass
	public static void configureLog() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() throws Exception {
		
		Configuration.set("BUS", "false");
		
		specs[0] = new ServiceSpec();
		specs[0].setPackageUri(JARDeployTest.JAR_LOCATION);
		specs[0].setPackageType(PackageType.COMMAND_LINE);
		specs[0].setEndpointName("");
		specs[0].setPort(8042);

		specs[1] = new ServiceSpec();
		specs[1].setPackageUri(WARDeployTest.WAR_LOCATION);
		specs[1].setPackageType(PackageType.TOMCAT);
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
				service = deployer.createService(spec);
			} catch (ServiceNotDeployedException e) {
				e.printStackTrace();
			}
			ServiceInstance instance = service.getInstances().get(0);
			url = instance.getNativeUri();
			nodeId = instance.getNode().getId();
			System.out.println("Service deployed at " + url);
		}
	}
}
