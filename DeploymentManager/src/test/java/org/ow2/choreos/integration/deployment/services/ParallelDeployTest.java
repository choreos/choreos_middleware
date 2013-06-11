/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.deployment.services;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.services.ServicesManagerImpl;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * Verify if the system works with multiple parallel requests.
 * 
 * This test works only if the node is already bootstrapped
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class ParallelDeployTest {

    private String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
    private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
    private ServicesManager deployer = new ServicesManagerImpl(npm);

    private DeployableServiceSpec[] specs = new DeployableServiceSpec[2];

    @BeforeClass
    public static void configureLog() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() throws Exception {

	specs[0] = new DeployableServiceSpec();
	specs[0].setPackageUri(JARDeployTest.JAR_LOCATION);
	specs[0].setPackageType(PackageType.COMMAND_LINE);
	specs[0].setEndpointName("");
	specs[0].setPort(8042);

	specs[1] = new DeployableServiceSpec();
	specs[1].setPackageUri(WARDeployTest.WAR_LOCATION);
	specs[1].setPackageType(PackageType.TOMCAT);
    }

    @Test
    public void shouldMakeParallelDeploys() throws InterruptedException, NodeNotUpdatedException,
	    NodeNotFoundException {

	Thread[] ts = new Thread[2];
	TestDeployer[] tds = new TestDeployer[2];
	for (int i = 0; i < 2; i++) {
	    tds[i] = new TestDeployer(specs[i]);
	    ts[i] = new Thread(tds[i]);
	    ts[i].start();
	}

	waitThreads(ts);

	ts = new Thread[2];
	for (int i = 0; i < 2; i++) {
	    ts[i] = new Thread(new TestUpgrader(tds[i]));
	    ts[i].start();
	    Thread.sleep(1000);
	}

	waitThreads(ts);

	for (TestDeployer td : tds) {
	    WebClient client = WebClient.create(td.url);
	    Response response = client.get();
	    assertEquals(200, response.getStatus());
	}
    }

    private void waitThreads(Thread[] ts) throws InterruptedException {
	for (Thread t : ts) {
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
		npm.updateNode(this.testDeployer.nodeId);
	    } catch (NodeNotUpdatedException e) {
		e.printStackTrace();
	    } catch (NodeNotFoundException e) {
		e.printStackTrace();
	    }
	}
    }

    private class TestDeployer implements Runnable {

	DeployableServiceSpec spec;
	String url;
	String nodeId;

	public TestDeployer(DeployableServiceSpec spec) {
	    this.spec = spec;
	}

	@Override
	public void run() {
	    System.out.println("Deploying " + spec);
	    DeployableService service = null;
	    try {
		service = deployer.createService(spec);
	    } catch (ServiceNotCreatedException e) {
		e.printStackTrace();
	    }
	    ServiceInstance instance = service.getInstances().get(0);
	    url = instance.getNativeUri();
	    nodeId = instance.getNode().getId();
	    System.out.println("Service deployed at " + url);
	}
    }
}
