package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NodeNotDestroyed;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import com.jcraft.jsch.JSchException;


@Category(IntegrationTest.class)
public class AWSCloudProviderTest {
	
	private final CloudProvider infra = new AWSCloudProvider();
    private Node node = new Node();
    private ResourceImpact resourceImpact = new ResourceImpact();

    @Before
    public void SetUp() {
    	LogConfigurator.configLog();
        node.setImage("us-east-1/ami-ccf405a5");
        Configuration.set("DEFAULT_PROVIDER", "");
    }

	@Test
    public void shouldCreateAndDeleteNode() throws RunNodesException, JSchException, NodeNotDestroyed, NodeNotFoundException {
        
        Node created = infra.createNode(node, resourceImpact);
        System.out.println("created " + created);
        assertTrue(created != null);
        
        infra.destroyNode(created.getId());
        
        try {
			node = infra.getNode(created.getId());
		} catch (NodeNotFoundException e) {
			System.out.println(e.getMessage());
			return; // OK
		}
        fail(node + " should not be retrieved"); // exception above not caught
    }

}