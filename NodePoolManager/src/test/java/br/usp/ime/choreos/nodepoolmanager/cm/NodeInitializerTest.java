package br.usp.ime.choreos.nodepoolmanager.cm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jclouds.compute.RunNodesException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.InfrastructureService;
import br.usp.ime.choreos.nodepoolmanager.Node;

public class NodeInitializerTest {
    private static InfrastructureService infra;
    private static Node node;

    @BeforeClass
    public static void createNode() throws RunNodesException {
        node = new Node();
        node.setImage("us-east-1/ami-ccf405a5");

        infra = new InfrastructureService();
        infra.createNode(node);
    }

    @AfterClass
    public static void destroyNode() {
        infra.destroyNode(node.getId());
    }

    @Test
    public void initializeNode() throws Exception {
        // Wait for sshd
        Thread.sleep(30000);

        NodeInitializer ni = new NodeInitializer(node.getIp());
        ni.cleanPetals();

        assertFalse(ni.isInitialized());

        ni.initialize();

        assertTrue(ni.isInitialized());
    }
}