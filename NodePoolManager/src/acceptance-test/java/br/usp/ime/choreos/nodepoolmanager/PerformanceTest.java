package br.usp.ime.choreos.nodepoolmanager;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.Test;

public class PerformanceTest extends BaseTest {

    private int total = 0;

    @Test
    public void createManyRealMachines() throws Exception {
        Configuration.set("DEFAULT_PROVIDER", "aws-ec2");

        testXNodesCreation(1);
        testXNodesCreation(4);
        testXNodesCreation(8);
        testXNodesCreation(16);
        testXNodesCreation(32);
        testXNodesCreation(64);
    }

    private void testXNodesCreation(int n) throws Exception {
        total = 0;
        long startTime = System.currentTimeMillis();
        createNodes(n);
        long endTime = System.currentTimeMillis();
        System.out.println("Total time to create " + n + " nodes: " + (endTime - startTime) + "ms");
    }

    private void createNodes(int quantity) throws Exception {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    createAndDeleteNode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    total += 1;
                }
            }
        };

        for (int i = 0; i < quantity; i++) {
            new Thread(r).start();
        }

        while (total < quantity) {
            Thread.sleep(100);
        }

    }

    private void createAndDeleteNode() throws Exception {
        WebClient client = WebClient.create("http://localhost:8080/");
        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(Long.MAX_VALUE);
        conduit.getClient().setConnectionTimeout(Long.MAX_VALUE);

        Node sampleNode = new Node();
        client.path("nodes");
        Response response = client.post(sampleNode);

        String location = (String) response.getMetadata().get("Location").get(0);
        WebClient webClient = WebClient.create(location);
        webClient.delete();

    }
}
