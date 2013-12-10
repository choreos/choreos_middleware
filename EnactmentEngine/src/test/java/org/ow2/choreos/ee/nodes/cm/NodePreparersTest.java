package org.ow2.choreos.ee.nodes.cm;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.ee.nodes.cm.NodePreparer;
import org.ow2.choreos.ee.nodes.cm.NodePreparers;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class NodePreparersTest {

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
        NodePreparer preparer1 = NodePreparers.getPreparerFor(node1); 
        NodePreparer preparer2 = NodePreparers.getPreparerFor(node1);
        assertTrue(preparer1 == preparer2);
    }
    
    @Test
    public void shouldRetriveDifferentInstances() {
        NodePreparer preparer1 = NodePreparers.getPreparerFor(node1); 
        NodePreparer preparer2 = NodePreparers.getPreparerFor(node2);
        assertTrue(preparer1 != preparer2);
    }

}
