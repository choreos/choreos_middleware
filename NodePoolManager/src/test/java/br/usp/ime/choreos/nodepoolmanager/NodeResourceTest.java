package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jclouds.compute.domain.NodeState;
import org.junit.After;
import org.junit.Test;

public class NodeResourceTest extends BaseTest {

    @After
    public void resetPath() {
        client.back(true);
    }

    @Test
    public void testGetInvalidNode() throws Exception {
        client.path("nodes/696969696969");
        Response response = client.get();
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetNode() throws Exception {
        client.path("nodes/" + sampleNode.getId());
        Node node = client.get(Node.class);

        assertEquals(sampleNode.getId(), node.getId());
        assertEquals(sampleNode.getHostname(), node.getHostname());
        assertEquals("1", node.getImage());
    }

    @Test
    public void deleteNode() {
        client.path("nodes/" + sampleNode.getId());
        Response response = client.delete();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        resetPath();
        client.path("nodes/" + sampleNode.getId());
        Node node = client.get(Node.class);
        assertTrue(node == null || node.getState() != NodeState.RUNNING.ordinal());
    }
}