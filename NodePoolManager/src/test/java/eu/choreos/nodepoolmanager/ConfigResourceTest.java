package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;
import eu.choreos.nodepoolmanager.utils.SshUtil;

public class ConfigResourceTest extends BaseTest {
	
	private String SSH_USER = "ubuntu";
	private String SSH_KEY = Configuration.get("AMAZON_PRIVATE_SSH_KEY");
	
    public void resetPath() {
        client.back(true);
    }
    
    /**
     * This test supposes the "getting-started2" recipe is already available on the chef server 
     * This recipe must create the getting-started2.txt file on home directory
     * 
     * @throws Exception 
     */
    //@Test
    public void shouldApplyValidCookbook() throws Exception{
    	
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
        assertTrue(isNodeLocation(location));
        
        // verify if the file getting-started2 is actually there
    	SshUtil ssh = new SshUtil(responseNode.getIp(), SSH_USER, SSH_KEY);
    	while (!ssh.isAccessible()){
				System.out.println("Could not connect; Trying again in 5 seconds");
				try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
		}
    	String createdFile = "chef-getting-started2.txt";
    	String returnText = null;
		returnText = ssh.runCommand("ls " + createdFile);
    	assertTrue(returnText.trim().equals(createdFile));
    }
    
    /**
     * This test is not passing!
     * The server is not returning the NOT FOUND error at the second invocation
     * See TODO on ConfigurationManager.installRecipe()
     */
    @Test
    public void shouldNotApplyInValidCookbook(){
    	
    	String INVALID_RECIPE = "xyz";
    	
    	resetPath();
    	client.path("nodes/configs");   	
    	
    	Config config2 = new Config();
        Response response = client.post(config2);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
    	Config config = new Config();
    	config.setName(INVALID_RECIPE);
        response = client.post(config);
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
