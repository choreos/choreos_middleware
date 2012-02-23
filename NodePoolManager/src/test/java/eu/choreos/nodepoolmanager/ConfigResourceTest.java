package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;

public class ConfigResourceTest extends BaseTest {
	
    public void resetPath() {
        client.back(true);
    }
    
    /**
     * This test supposes the "getting-started2" recipe is already available on the chef server 
     */
    @Test
    public void shouldApplyValidCookbook(){
    	
    	String RECIPE_NAME = "getting-started2";
    	
    	client.path("nodes/configs");   	
    	
    	Config config = new Config();
    	config.setName(RECIPE_NAME);
        Response response = client.post(config);
        NodeRestRepresentation responseNode = getNodeFromResponse(response);

        // verify cloud node 
        assertEquals(EXPECTED_IMAGE, responseNode.getImage());
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        
        // verify resource location
        List<Object> list =  response.getMetadata().get("Location");
        assertTrue(list != null && !list.isEmpty());
        String location = list.get(0).toString();
        System.out.println("location= " + location);
        assertTrue(isNodeLocation(location));
        
        // verify if knife lists the recipe to the node
        
        // verify if the file getting-started2 is actually there

    }
    
    //@Test
    public void shouldNotApplyInValidCookbook(){
    	
    	String INVALID_RECIPE = "xyz";
    	
        
    	resetPath();
    	client.path("configs");   	
    	
    	Config config = new Config();
    	config.setName(INVALID_RECIPE);
        Response response = client.post(config);

        assertEquals(Status.NOT_FOUND, response.getStatus());
        
    	Config config2 = new Config();
    	
        response = client.post(config2);

        assertEquals(Status.BAD_REQUEST, response.getStatus());

    }
}
