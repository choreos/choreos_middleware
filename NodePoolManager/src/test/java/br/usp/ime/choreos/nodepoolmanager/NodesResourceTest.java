package br.usp.ime.choreos.nodepoolmanager;

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

        System.out.println(client.get().getEntity());

    }
}
