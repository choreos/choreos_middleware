package br.usp.ime.choreos.nodepoolmanager;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

public class PerformanceTest extends BaseTest {

    private int total;
    List<String> nodes = new ArrayList<String>();
    private Node node;

    public PerformanceTest() {
        Configuration.set("DEFAULT_PROVIDER", "aws-ec2");

        client.path("nodes");

        node = new Node();
    }

    @Test
    public void createAndDestroyManyVMs() throws Exception {
        testNodeCreationAndDestructionTime(1);
        testNodeCreationAndDestructionTime(2);
        testNodeCreationAndDestructionTime(4);
        testNodeCreationAndDestructionTime(8);
        testNodeCreationAndDestructionTime(16);
        // Our amazon limit is 20
    }

    private void testNodeCreationAndDestructionTime(int numberOfNodes) throws Exception {
        total = 0;

        long creationTime = measureCreationTime(numberOfNodes);
        System.out.println("Total time to create " + numberOfNodes + " nodes: " + creationTime + "ms");

        // Deletion time
        long deletionTime = measureDestructionTime();
        System.out.println("Total time to destroy " + numberOfNodes + " nodes: " + (deletionTime) + "ms");

        // Total time
        System.out.println("Total time " + numberOfNodes + " nodes: " + (creationTime + deletionTime) + "ms");
    }

    private long measureDestructionTime() throws Exception {
        long startTime = System.currentTimeMillis();
        deleteNodes();
        long endTime = System.currentTimeMillis();

        return (endTime - startTime);
    }

    private long measureCreationTime(int numberOfNodes) throws Exception {
        long startTime = System.currentTimeMillis();
        createNodes(numberOfNodes);
        long endTime = System.currentTimeMillis();

        return (endTime - startTime);
    }

    private void createNodes(int quantity) throws Exception {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    createNode();
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

    // TODO It differs from createNodes in only one line. How to use a method as
    // a parameter inside the Runnable?
    private void deleteNodes() throws Exception {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    destroyNode(getNextNode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    total -= 1;
                }
            }
        };

        for (int i = total; i > 0; i--) {
            new Thread(r).start();
        }

        while (total > 0) {
            Thread.sleep(100);
        }
    }

    private String getNextNode() {
        return nodes.remove(0);
    }

    private void createNode() throws Exception {
        Response response = client.post(node);

        String location = (String) response.getMetadata().get("Location").get(0);
        nodes.add(location);
    }

    private void destroyNode(String location) throws Exception {
        WebClient webClient = WebClient.create(location);
        webClient.delete();
    }
}