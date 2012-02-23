package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Test;

import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;


public class NodesResourceTest extends BaseTest {
	
    @After
    public void resetPath() {
        client.back(true);
    }

    @Test
    public void createNode() throws Exception {
        client.path("nodes");

        NodeRestRepresentation requestNode = new NodeRestRepresentation();
        Response response = client.post(requestNode);
        NodeRestRepresentation responseNode = getNodeFromResponse(response);

        assertEquals(EXPECTED_IMAGE, responseNode.getImage());
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void listNodes() throws Exception {
        final String image2 = TEST_IMAGE;

        NodeRestRepresentation node = new NodeRestRepresentation();
        node.setImage(image2);

        client.path("nodes");
        client.post(node);

        Collection<? extends NodeRestRepresentation> nodeCollection = client.getCollection(NodeRestRepresentation.class);
        int nodeAmount = nodeCollection.size();
        NodeRestRepresentation[] nodes = nodeCollection.toArray(new NodeRestRepresentation[nodeAmount]);

        assertTrue(nodeAmount >= 2);
        assertEquals(EXPECTED_IMAGE, nodes[nodeAmount - 2].getImage());
        assertEquals(EXPECTED_IMAGE, nodes[nodeAmount - 1].getImage());

        resetPath();
        String id = (nodes[nodeAmount - 1]).getId();
        infrastructure.destroyNode(id);
    }
    

}