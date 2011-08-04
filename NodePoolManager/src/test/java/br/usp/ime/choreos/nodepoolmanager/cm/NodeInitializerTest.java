package br.usp.ime.choreos.nodepoolmanager.cm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NodeInitializerTest {

    @Test
    public void initializeNode() throws Exception {
        NodeInitializer ni = new NodeInitializer("ec2-50-16-158-242.compute-1.amazonaws.com");

        assertFalse(ni.isInitialized());

        ni.initialize();

        assertTrue(ni.isInitialized());

    }
}
