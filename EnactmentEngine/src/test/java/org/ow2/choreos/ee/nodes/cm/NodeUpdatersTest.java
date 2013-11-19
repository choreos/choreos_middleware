package org.ow2.choreos.ee.nodes.cm;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.ee.nodes.cm.NodeUpdater;
import org.ow2.choreos.ee.nodes.cm.NodeUpdaters;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class NodeUpdatersTest {

    private CloudNode node1;
    private CloudNode node2;
    
    @Before
    public void setNodes() {
        node1 = new CloudNode();
        node1.setId("1");
        node2 = new CloudNode();
        node2.setId("2");
    }
    
    @Test
    public void shouldRetriveTheSameInstance() {
        NodeUpdater updater1 = NodeUpdaters.getUpdaterFor(node1); 
        NodeUpdater updater2 = NodeUpdaters.getUpdaterFor(node1);
        assertTrue(updater1 == updater2);
    }
    
    @Test
    public void shouldRetriveDifferentInstances() {
        NodeUpdater updater1 = NodeUpdaters.getUpdaterFor(node1); 
        NodeUpdater updater2 = NodeUpdaters.getUpdaterFor(node2);
        assertTrue(updater1 != updater2);
    }

}
