package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodeResourceTest {

    private final WebClient client = WebClient.create("http://localhost:8080/");

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
        n.setId(1);
        Node c = client.post(n, Node.class);
        client.back(true);
    }

    @Test
    public void testGetNode() throws Exception {
        client.path("nodes/1");
        Node c = client.get(Node.class);
        assertEquals("myZone", c.getZone());
    }

    @Test
    public void testGetInvalidNode() throws Exception {
        client.path("nodes/6969");
        Node n = client.get(Node.class);
        assertNull(n);
    }

    @Test
    public void deleteNode() throws Exception {
        client.path("nodes/1");
        client.delete();

        client.back(true);
        client.path("nodes/1");
        Node n = client.get(Node.class);
        assertNull(n);
    }

}
