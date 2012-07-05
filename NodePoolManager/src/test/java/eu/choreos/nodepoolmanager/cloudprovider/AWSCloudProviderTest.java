package eu.choreos.nodepoolmanager.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;

import com.jcraft.jsch.JSchException;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.NodeNotFoundException;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.SshUtil;


public class AWSCloudProviderTest {
	
	private final CloudProvider infra = new AWSCloudProvider();
    private Node node = new Node();

    @Before
    public void SetUp() throws RunNodesException {
        node.setImage("us-east-1/ami-ccf405a5");
        Configuration.set("DEFAULT_PROVIDER", "");
    }

	@Test
    public void shouldCreateAndDeleteNode() throws RunNodesException, JSchException {
        
    	// Waiting ssh to start
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (!ssh.isAccessible())
            ;

        Node created = infra.createNode(node);
        assertTrue(created != null);
        
        infra.destroyNode(created.getId());
        
        try {
			infra.getNode(created.getId());
		} catch (NodeNotFoundException e) {
			; // OK
		}
    }

}