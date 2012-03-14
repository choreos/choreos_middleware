package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jclouds.compute.RunNodesException;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.ConfigurationManager;
import eu.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.SshUtil;


public class NodeInitializerTest {
    
	private final static AWSCloudProvider infra = new AWSCloudProvider();
    private static Node node = new Node();

    @BeforeClass
    public static void createNode() throws RunNodesException {
        node.setImage("us-east-1/ami-ccf405a5");
        Configuration.set("DEFAULT_PROVIDER", "");
        node = infra.createNode(node);
    }
    
    @Test
    public void initializeNode() throws Exception {
        
    	// Waiting ssh to start
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (!ssh.isAccessible())
            ;

        ConfigurationManager configurationManager = new ConfigurationManager();
        assertFalse(configurationManager.isInitialized(node));
        
        configurationManager.initializeNode(node);
		assertTrue(configurationManager.isInitialized(node));
    }
    
        
    
}
