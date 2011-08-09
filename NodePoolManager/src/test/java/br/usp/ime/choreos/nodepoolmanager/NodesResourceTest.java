package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodesResourceTest {
    private static final WebClient client = WebClient.create("http://localhost:8080/");
    private static Node sampleNode;

    @BeforeClass
    public static void startServer() throws Exception {
        NodePoolManagerStandaloneServer.start();
        Configuration.set("DEFAULT_PROVIDER", "stub");
    }

    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
        NodePoolManagerStandaloneServer.stop();
        destroyNode(sampleNode);
    }

    @After
    public void resetPath() {
        client.back(true);
    }

    @Test
    public void createNode() throws Exception {
        client.path("nodes");

        sampleNode = new Node();
        sampleNode.setImage("1");

        Response response = client.post(sampleNode);
        sampleNode = getNodeFromResponse(response);

        assertEquals("1", sampleNode.getImage());
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    }

    private static void destroyNode(Node node) throws UnsupportedEncodingException {
        client.path("nodes/" + node.getId());
        client.delete();
    }

    private static Node getNodeFromResponse(Response response) {
        String location = (String) response.getMetadata().get("Location").get(0);
        WebClient webClient = WebClient.create(location);
        return webClient.get(Node.class);
    }

    @Test
    public void listNodes() throws Exception {
        final String image2 = "1";

        Node node = new Node();
        node.setImage(image2);

        client.path("nodes");
        client.post(node);

        Collection<? extends Node> nodeCollection = client.getCollection(Node.class);
        int nodeAmount = nodeCollection.size();
        Node[] nodes = nodeCollection.toArray(new Node[nodeAmount]);

        assertTrue(nodeAmount >= 2);
        assertEquals("1", nodes[nodeAmount - 2].getImage());
        assertEquals(image2, nodes[nodeAmount - 1].getImage());

        resetPath();
        destroyNode(nodes[nodeAmount - 1]);
    }
}