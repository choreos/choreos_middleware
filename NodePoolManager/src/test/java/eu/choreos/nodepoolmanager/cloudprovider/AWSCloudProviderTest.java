package eu.choreos.nodepoolmanager.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.nodepoolmanager.BaseTest;
import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.SshUtil;


public class AWSCloudProviderTest extends BaseTest {
	
	private final static AWSCloudProvider infra = new AWSCloudProvider();
    private Node node = new Node();

    @Before
    public void SetUp() throws RunNodesException {
        node.setImage("us-east-1/ami-ccf405a5");
        Configuration.set("DEFAULT_PROVIDER", "");
        node = infra.createNode(node);
    }

	@Test
    public void shouldCreateNodeFromPool() throws Exception {
        
    	// Waiting ssh to start
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (!ssh.isAccessible())
            ;

        assertTrue(infra.createOrUseExistingNode(node)!=null);
    }

}