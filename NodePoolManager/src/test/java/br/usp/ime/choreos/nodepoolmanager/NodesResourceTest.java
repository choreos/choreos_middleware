package br.usp.ime.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Test;

public class NodesResourceTest extends BaseTest {

	private static String EXPECTED_IMAGE = "us-east-1/ami-ccf405a5";
	
    @After
    public void resetPath() {
        client.back(true);
    }

    @Test
    public void createNode() throws Exception {
        client.path("nodes");

        sampleNode = new Node();
        sampleNode.setImage(TEST_IMAGE);

        Response response = client.post(sampleNode);
        sampleNode = getNodeFromResponse(response);

        assertEquals(EXPECTED_IMAGE, sampleNode.getImage());
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    }

    private static Node getNodeFromResponse(Response response) {
        String location = (String) response.getMetadata().get("Location").get(0);
        WebClient webClient = WebClient.create(location);
        return webClient.get(Node.class);
    }

    @Test
    public void listNodes() throws Exception {
        final String image2 = TEST_IMAGE;

        Node node = new Node();
        node.setImage(image2);

        client.path("nodes");
        client.post(node);

        Collection<? extends Node> nodeCollection = client.getCollection(Node.class);
        int nodeAmount = nodeCollection.size();
        Node[] nodes = nodeCollection.toArray(new Node[nodeAmount]);

        assertTrue(nodeAmount >= 2);
        assertEquals(EXPECTED_IMAGE, nodes[nodeAmount - 2].getImage());
        assertEquals(EXPECTED_IMAGE, nodes[nodeAmount - 1].getImage());

        resetPath();
        destroyNode(nodes[nodeAmount - 1]);
    }
}