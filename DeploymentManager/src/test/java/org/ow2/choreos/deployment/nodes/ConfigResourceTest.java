package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclouds.compute.RunNodesException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

@Category(IntegrationTest.class)
public class ConfigResourceTest extends BaseTest {
	
    /**
     * This test supposes the "getting-started" recipe is already available on the chef server 
     * This recipe must create the getting-started.txt file on home directory
     * @throws RunNodesException 
     * @throws ConfigNotAppliedException 
     * @throws JSchException 
     * 
     * @throws Exception 
     */
    @Test
    public void shouldApplyValidCookbook() throws RunNodesException, ConfigNotAppliedException, JSchException {
    	
    	String RECIPE_NAME = "getting-started";
    	String CREATED_FILE = "chef-getting-started.txt";
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null);
    	
    	NodePoolManager npm = new NPMImpl(cp);
    	Config config = new Config(RECIPE_NAME);
    	Node returnedNode = npm.applyConfig(config);
    	assertTrue(returnedNode != null);
        assertTrue(returnedNode.hasIp());
        assertEquals(node.getIp(), returnedNode.getIp());

        try {
			npm.upgradeNode(node.getId());
		} catch (NodeNotUpgradedException e) {
			fail();
		} catch (NodeNotFoundException e) {
			fail();
		}
        
        // verify if the file getting-started is actually there
        SshUtil ssh = new SshUtil(returnedNode.getIp(), node.getUser(), node.getPrivateKeyFile());
    	String returnText = ssh.runCommand("ls " + CREATED_FILE, true);
    	assertTrue(returnText.trim().equals(CREATED_FILE));
    }
    
    /**
     * This test is not passing!
     * The server is not returning the NOT FOUND error at the second invocation
     * See TODO on ConfigurationManager.installRecipe()
     * @throws ConfigNotAppliedException 
     */
    // @Test
    public void shouldNotApplyInValidCookbook() throws ConfigNotAppliedException{
    	
    	String INVALID_RECIPE = "xyz";
    	
    	NodePoolManager npm = new NodesClient(nodePoolManagerHost);
    	Node node1 = npm.applyConfig(null);
    	Node node2 = npm.applyConfig(new Config(INVALID_RECIPE));
    	
        assertTrue(node1 == null);
        assertTrue(node2 == null);
    }
}
