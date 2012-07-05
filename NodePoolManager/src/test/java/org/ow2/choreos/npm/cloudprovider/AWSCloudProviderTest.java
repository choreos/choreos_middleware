package org.ow2.choreos.npm.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.npm.Configuration;
import org.ow2.choreos.npm.NodeNotFoundException;
import org.ow2.choreos.npm.cloudprovider.AWSCloudProvider;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.utils.SshUtil;

import com.jcraft.jsch.JSchException;



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