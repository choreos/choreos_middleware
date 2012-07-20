package org.ow2.choreos.npm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.npm.client.NPMClient;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.utils.SshUtil;


public class ConfigResourceTest extends BaseTest {
	
    /**
     * This test supposes the "getting-started2" recipe is already available on the chef server 
     * This recipe must create the getting-started2.txt file on home directory
     * 
     * @throws Exception 
     */
    @Test
    public void shouldApplyValidCookbook() throws Exception{
    	
    	String RECIPE_NAME = "getting-started2";
    	String CREATED_FILE = "chef-getting-started2.txt";
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null);
    	
    	NodePoolManager npm = new NPMClient(nodePoolManagerHost);
    	Config config = new Config(RECIPE_NAME);
    	Node returnedNode = npm.applyConfig(config);
    	assertTrue(returnedNode != null);
        assertTrue(returnedNode.hasIp());
        assertEquals(node.getIp(), returnedNode.getIp());

        boolean upgraded = npm.upgradeNodes();
        assertTrue(upgraded);
        
        // verify if the file getting-started2 is actually there
        SshUtil ssh = new SshUtil(returnedNode.getIp(), node.getUser(), node.getPrivateKeyFile());
    	String returnText = ssh.runCommand("ls " + CREATED_FILE, true);
    	assertTrue(returnText.trim().equals(CREATED_FILE));
    }
    
    /**
     * This test is not passing!
     * The server is not returning the NOT FOUND error at the second invocation
     * See TODO on ConfigurationManager.installRecipe()
     */
    // @Test
    public void shouldNotApplyInValidCookbook(){
    	
    	String INVALID_RECIPE = "xyz";
    	
    	NodePoolManager npm = new NPMClient(nodePoolManagerHost);
    	Node node1 = npm.applyConfig(null);
    	Node node2 = npm.applyConfig(new Config(INVALID_RECIPE));
    	
        assertTrue(node1 == null);
        assertTrue(node2 == null);
    }
}
