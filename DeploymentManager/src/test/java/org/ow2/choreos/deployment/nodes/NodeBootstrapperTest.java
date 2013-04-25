package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.cm.BootstrapChecker;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.ee.api.ResourceImpact;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * Before run this test, restore your VM to a snapshot before the bootstrap
 * 
 * If the machine is already bootstrapped the test must still pass,
 * but it will not properly test the system
 * 
 * @author leonardo
 *
 */
@Category(IntegrationTest.class)
public class NodeBootstrapperTest {
    
	protected Node node;
	
	@Before
	public void setUp() {
		LogConfigurator.configLog();
	}
	
	/**
	 * Beware, this test will leave the node bootstrapped
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldLeaveNodeBootstraped() throws Exception {

		CloudProvider cp = CloudProviderFactory.getInstance(Configuration.get("CLOUD_PROVIDER"));
		node = cp.createOrUseExistingNode(new Node(), new ResourceImpact());
		System.out.println(node);

		BootstrapChecker checker = new BootstrapChecker(); 
		if (!checker.isBootstrapped(node)) {
			System.out.println("Going to bootstrap the node");
			NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
			bootstrapper.bootstrapNode();
			System.out.println("Checking if bootstrap was OK");
			assertTrue(checker.isBootstrapped(node));
		} else {
			System.out.println("Node was already bootstrapped");
		}
	}
        
    
}
