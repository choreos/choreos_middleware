package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.jclouds.compute.RunNodesException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.SshCommandFailed;
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
     * @throws SshCommandFailed 
     * 
     * @throws Exception 
     */
    @Test
    public void shouldApplyValidCookbook() throws RunNodesException, ConfigNotAppliedException, JSchException, SshCommandFailed {
    	
    	String RECIPE_NAME = "getting-started";
    	String CREATED_FILE = "chef-getting-started.txt";
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(null, null);
    	
    	NodePoolManager npm = new NPMImpl(cp);
    	int num_replicas = 1;
    	Config config = new Config(RECIPE_NAME, null, num_replicas);
    	
    	List<Node> returnedNodes = npm.applyConfig(config);
    	assertTrue(returnedNodes != null);
        assertTrue(returnedNodes.get(0).hasIp());
        assertEquals(node.getIp(), returnedNodes.get(0).getIp());

        try {
			npm.upgradeNode(node.getId());
		} catch (NodeNotUpgradedException e) {
			fail();
		} catch (NodeNotFoundException e) {
			fail();
		}
        
        // verify if the file getting-started is actually there
        SshUtil ssh = new SshUtil(returnedNodes.get(0).getIp(), node.getUser(), node.getPrivateKeyFile());
    	String returnText = ssh.runCommand("ls " + CREATED_FILE, true);
    	assertTrue(returnText.trim().equals(CREATED_FILE));
    }
    
}
