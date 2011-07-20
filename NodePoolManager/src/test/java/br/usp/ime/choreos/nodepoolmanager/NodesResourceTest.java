package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NodesResourceTest {

    @BeforeClass
    public static void startServer() throws InterruptedException {
        NodePoolManagerStandaloneServer.start();
    }

    @AfterClass
    public static void stopServer() {
        NodePoolManagerStandaloneServer.stop();
    }

    @Test
    public void testGetNodes() throws Exception {
        WebClient client = WebClient.create("http://localhost:8080/");
        client.path("nodes");
        Node n = new Node();
        n.setCpus(2);
        n.setId(1);
        Node c = client.post(n, Node.class);

        assertEquals(2, c.getCpus());
        assertEquals("fakeIp", c.getIp());

        client.back(true);
        client.path("nodes/1");
        c = client.get(Node.class);
        assertEquals(2, c.getCpus());

        System.out.println(client.get().getEntity());

    }
}
