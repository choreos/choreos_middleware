package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.cloudprovider.FixedCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.LogConfigurator;

/**
 * Before run this test, restore your VM to a snapshot before the bootstrap
 * 
 * If the machine is already bootstrapped the test must still pass,
 * but it will not properly test the system
 * 
 * @author leonardo
 *
 */
public class NodeInitializerTest {
    
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

		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(new Node());
		System.out.println(node);

		ConfigurationManager configurationManager = new ConfigurationManager();
		if (!configurationManager.isInitialized(node)) {
			System.out.println("Going to bootstrap the node");
			configurationManager.initializeNode(node);
			System.out.println("Checking if bootstrap was OK");
			assertTrue(configurationManager.isInitialized(node));
		} else {
			System.out.println("Node was already bootstrapped");
		}
	}
        
    
}
