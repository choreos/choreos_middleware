package br.usp.ime.choreos.nodepoolmanager.cm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jclouds.compute.RunNodesException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.Configuration;
import br.usp.ime.choreos.nodepoolmanager.Node;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import br.usp.ime.choreos.nodepoolmanager.configmanager.NodeInitializer;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class NodeInitializerTest {
    private final static AWSCloudProvider infra = new AWSCloudProvider();
    private static Node node = new Node();

    @BeforeClass
    public static void createNode() throws RunNodesException {
        node.setImage("us-east-1/ami-ccf405a5");
        Configuration.set("DEFAULT_PROVIDER", "");
        node = infra.createOrUseExistingNode(node);
    }

    @Test
    public void initializeNode() throws Exception {
        // Waiting sshd to start
        SshUtil ssh = new SshUtil(node.getIp());
        while (!ssh.isAccessible())
            ;

        NodeInitializer ni = new NodeInitializer(node.getIp());
        ni.cleanPetals();

        assertFalse(ni.isInitialized());

        ni.initialize();

        assertTrue(ni.isInitialized());
    }
}
