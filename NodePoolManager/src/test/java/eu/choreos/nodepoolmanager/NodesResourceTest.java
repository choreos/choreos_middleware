package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Test;

import eu.choreos.nodepoolmanager.datamodel.Config;
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

    private static NodeRestRepresentation getNodeFromResponse(Response response) {
        String location = (String) response.getMetadata().get("Location").get(0);
        WebClient webClient = WebClient.create(location);
        return webClient.get(NodeRestRepresentation.class);
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
    
    /**
     * This test supposes the "getting-started2" recipe is already available on the chef server 
     */
    @Test
    public void shouldApplyValidCookbook(){
    	client.path("configs");   	
    	
    	Config config = new Config();
    	config.setName("getting-started2");
        Response response = client.post(config);
        NodeRestRepresentation responseNode = getNodeFromResponse(response);

        assertEquals(EXPECTED_IMAGE, responseNode.getImage());
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

    }
    
    @Test
    public void shouldNotApplyInValidCookbook(){
    	client.path("configs");   	
    	
    	Config config = new Config();
    	config.setName("xyz");
        Response response = client.post(config);

        assertEquals(Status.INTERNAL_SERVER_ERROR, response.getStatus());

    }
}