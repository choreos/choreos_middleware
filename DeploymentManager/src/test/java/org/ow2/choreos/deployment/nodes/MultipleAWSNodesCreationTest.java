package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodeNotCreatedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.AWSCloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * Test the creation of a bunch of VMs using Node Pool Manager with AWS cloud provider.
 * 
 * The purpose is to verify if NPM properly handles 
 * the failures of underlying AWS cloud provider.
 * The test is successful if all created VMs are properly bootstrapped.
 * 
 * @author leonardo
 *
 */
@Category(IntegrationTest.class)
public class MultipleAWSNodesCreationTest {
	
	public static final int BUNCH_SIZE = 25;
	private CloudProvider cp = new AWSCloudProvider();
	private NodePoolManager npm = new NPMImpl(cp);
    
	@BeforeClass
	public static void setUpClass() {
		LogConfigurator.configLog();
	}
	
	@Test
	public void shouldLeaveNodesBootstraped() throws Exception {

		List<Thread> trds = new ArrayList<Thread>();
		List<SingleTest> tests = new ArrayList<SingleTest>();
		for (int i=0; i<BUNCH_SIZE; i++) {
			SingleTest singleTest = new SingleTest(i);
			Thread t = new Thread(singleTest);
			trds.add(t);
			tests.add(singleTest);
			t.start();
		}
		
		for (Thread t: trds) {
			t.join();
		}
		
		for (SingleTest test: tests) {
			assertTrue("Node not created in test " + test.index, test.created);
			assertTrue("Node not bootstrapped " + test.index, test.bootstrapped);
			System.out.println("Test " + test.index + " OK.");
		}
	}
	
	private class SingleTest implements Runnable {
		
		int index;
		boolean created;
		boolean bootstrapped;
		
		public SingleTest(int index) {
			this.index = index;
		}

		@Override
		public void run() {

			try {
				Node node = npm.createNode(new Node());
				created = true;
				NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
				bootstrapped = bootstrapper.isNodeAlreadyBootstrapped();
			} catch (NodeNotCreatedException e) {
				System.out.println("Node not created in " + index + " because " + e.getMessage());
			}
		}
		
	}
        
    
}
