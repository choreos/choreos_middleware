package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
    public void createNode() throws Exception {
        WebClient client = WebClient.create("http://localhost:8080/");
        client.path("nodes");
        Node n = new Node();
        n.setCpus(2);
        Response response = client.post(n);

        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        String url = (String) response.getMetadata().get("Location").get(0);
        client = WebClient.create(url);
        Node node = client.get(Node.class);

        assertEquals(2, node.getCpus().intValue());
        assertEquals("fakeIp", node.getIp());

        client.delete();
    }
    
    @Test
    public void listNodes() throws Exception {
        WebClient client = WebClient.create("http://localhost:8080/");
        client.path("nodes");
        Node n = new Node();
        n.setCpus(2);
        client.post(n);

        client.back(true);
        client.path("nodes");
        n.setSo("Linux");
        client.post(n);

        client.back(true);
        client.path("nodes");

        Iterator<? extends Node> i = client.getCollection(Node.class).iterator();

        assertEquals(2, i.next().getCpus().intValue());
        assertEquals("Linux", i.next().getSo());
    }

    
}
