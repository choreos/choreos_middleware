package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodeResourceTest {

    private static final WebClient client = WebClient.create("http://localhost:8080/");
    private static Node sampleNode;

    private static final String IMAGE = "us-east-1/ami-ccf405a5";

    @BeforeClass
    public static void startServer() throws InterruptedException, RunNodesException {
        NodePoolManagerStandaloneServer.start();
        createSampleNode();
    }

    public static void createSampleNode() throws RunNodesException {
        sampleNode = new Node();
        sampleNode.setImage(IMAGE);

        InfrastructureService infrastructure = new InfrastructureService();
        infrastructure.createNode(sampleNode);
    }

    @AfterClass
    public static void stopServer() {
        NodePoolManagerStandaloneServer.stop();
    }

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
        assertEquals(IMAGE, node.getImage());
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