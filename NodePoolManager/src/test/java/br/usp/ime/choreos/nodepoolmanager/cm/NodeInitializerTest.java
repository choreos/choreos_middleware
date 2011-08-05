package br.usp.ime.choreos.nodepoolmanager.cm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NodeInitializerTest {

    @Test
    public void initializeNode() throws Exception {
        NodeInitializer ni = new NodeInitializer("ec2-50-16-165-94.compute-1.amazonaws.com");
        ni.cleanPetals();

        assertFalse(ni.isInitialized());

        ni.initialize();

        assertTrue(ni.isInitialized());

    }
}
