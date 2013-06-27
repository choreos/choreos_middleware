package org.ow2.choreos.deployment.nodes.cm;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NodeUpdaterFactoryTest {

    @Test
    public void shouldRetriveTheSameInstance() {
        String nodeId = "1";
        NodeUpdater updater1 = NodeUpdaters.getUpdaterFor(nodeId); 
        NodeUpdater updater2 = NodeUpdaters.getUpdaterFor(nodeId);
        assertTrue(updater1 == updater2);
    }
    
    @Test
    public void shouldRetriveDifferentInstances() {
        String nodeId1 = "1";
        String nodeId2 = "2";
        NodeUpdater updater1 = NodeUpdaters.getUpdaterFor(nodeId1); 
        NodeUpdater updater2 = NodeUpdaters.getUpdaterFor(nodeId2);
        assertTrue(updater1 != updater2);
    }

}
