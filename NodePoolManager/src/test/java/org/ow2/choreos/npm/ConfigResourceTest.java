package org.ow2.choreos.npm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.npm.client.NodePoolManager;
import org.ow2.choreos.npm.client.NodePoolManagerClient;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;
import org.ow2.choreos.npm.utils.SshUtil;


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
    	
    	NodePoolManager npm = new NodePoolManagerClient(nodePoolManagerHost);
    	NodeRestRepresentation noder = npm.applyConfig(RECIPE_NAME);
    	assertTrue(noder != null);
        assertTrue(!noder.getId().isEmpty());
        assertTrue(isIp(noder.getIp()));
        assertEquals(node.getIp(), noder.getIp());

        boolean upgraded = npm.upgradeNodes();
        assertTrue(upgraded);
        
        // verify if the file getting-started2 is actually there
        SshUtil ssh = new SshUtil(noder.getIp(), node.getUser(), node.getPrivateKeyFile());
    	String returnText = ssh.runCommand("ls " + CREATED_FILE, true);
    	assertTrue(returnText.trim().equals(CREATED_FILE));
    }
    
    /**
     * This test is not passing!
     * The server is not returning the NOT FOUND error at the second invocation
     * See TODO on ConfigurationManager.installRecipe()
     */
    //@Test
    public void shouldNotApplyInValidCookbook(){
    	
    	String INVALID_RECIPE = "xyz";
    	
    	NodePoolManager npm = new NodePoolManagerClient(nodePoolManagerHost);
    	NodeRestRepresentation noder1 = npm.applyConfig("");
    	NodeRestRepresentation noder2 = npm.applyConfig(INVALID_RECIPE);
    	
        assertTrue(noder1 == null);
        assertTrue(noder2 == null);
    }
}
