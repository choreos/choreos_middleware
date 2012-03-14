package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jclouds.compute.domain.NodeState;
import org.junit.After;
import org.junit.Test;

import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;


public class NodeResourceTest extends BaseTest {

    @After
    public void resetPath() {
        client.back(true);
    }

    @Test
    public void testGetInvalidNode()  {
    	String NO_EXISTING_NODE = "nodes/696969696969";
        client.path(NO_EXISTING_NODE);
        Response response = client.get();
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetNode() throws Exception {
        client.path("nodes/" + sampleNode.getId());
        NodeRestRepresentation node = client.get(NodeRestRepresentation.class);

        assertEquals(sampleNode.getId(), node.getId());
        assertEquals(sampleNode.getHostname(), node.getHostname());
        assertEquals(EXPECTED_IMAGE, node.getImage());
    }

    @Test
    public void deleteNode() {
        client.path("nodes/" + sampleNode.getId());
        Response response = client.delete();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        resetPath();
        client.path("nodes/" + sampleNode.getId());

        try {
        	NodeRestRepresentation node = client.get(NodeRestRepresentation.class);
            assertTrue(node == null || node.getState() != NodeState.RUNNING.ordinal());
        } catch (Exception e) {
            response = client.get();
            assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        }
    }
}