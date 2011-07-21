package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodeResourceTest {

    private final WebClient client = WebClient.create("http://localhost:8080/");

    private Node sample;

    @BeforeClass
    public static void startServer() throws InterruptedException {
        NodePoolManagerStandaloneServer.start();
    }

    @AfterClass
    public static void stopServer() {
        NodePoolManagerStandaloneServer.stop();
    }

    @Before
    public void createSampleNode() {
        client.path("nodes");
        Node n = new Node();
        n.setZone("myZone");
        Response r = client.post(n);
        sample = WebClient.create((String) r.getMetadata().get("Location").get(0)).get(Node.class);
        client.back(true);
    }

    @Test
    public void testGetNode() throws Exception {
        client.path("nodes/" + sample.getId());
        Node c = client.get(Node.class);
        assertEquals("myZone", c.getZone());
    }

    @Test
    public void testGetInvalidNode() throws Exception {
        client.path("nodes/696969696969");
        Response response = client.get();
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteNode() throws Exception {
        client.path("nodes/" + sample.getId());
        Response response = client.delete();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        client.back(true);
        client.path("nodes/" + sample.getId());
        response = client.get();
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

}
