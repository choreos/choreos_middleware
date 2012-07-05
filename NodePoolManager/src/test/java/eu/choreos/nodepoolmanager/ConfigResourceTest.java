package eu.choreos.nodepoolmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.utils.SshUtil;

public class ConfigResourceTest extends BaseTest {
	
	private String IP = Configuration.get("FIXED_VM_IP");
	private String SSH_USER = Configuration.get("FIXED_VM_USER");
	private String SSH_KEY = Configuration.get("FIXED_VM_PRIVATE_SSH_KEY");
	
    /**
     * This test supposes the "getting-started2" recipe is already available on the chef server 
     * This recipe must create the getting-started2.txt file on home directory
     * 
     * @throws Exception 
     */
    @Test
    public void shouldApplyValidCookbook() throws Exception{
    	
    	String RECIPE_NAME = "getting-started2";
    	
    	client.path("nodes/configs");   	
    	
    	Config config = new Config();
    	config.setName(RECIPE_NAME);
        Response responseApplyConfig = client.post(config);
        // TODO: retrieve responseNode from response body (representation on body not implemented yet)
        //NodeRestRepresentation responseNode = getNodeFromResponse(response);
        
        client.back(true);
        client.path("nodes/upgrade");
        Response responseUpgrade = client.post(null);
        
        assertEquals(Status.CREATED.getStatusCode(), responseApplyConfig.getStatus());
        assertEquals(Status.OK.getStatusCode(), responseUpgrade.getStatus());
        
        // verify resource location
        List<Object> list =  responseApplyConfig.getMetadata().get("Location");
        assertTrue(list != null && !list.isEmpty());
        String location = list.get(0).toString();
        assertTrue(isNodeLocation(location));
        
        // verify if the file getting-started2 is actually there
        SshUtil ssh = new SshUtil(IP, SSH_USER, SSH_KEY);
    	String createdFile = "chef-getting-started2.txt";
    	String returnText = null;
		returnText = ssh.runCommand("ls " + createdFile, true);
    	assertTrue(returnText.trim().equals(createdFile));
    }
    
    /**
     * This test is not passing!
     * The server is not returning the NOT FOUND error at the second invocation
     * See TODO on ConfigurationManager.installRecipe()
     */
    //@Test
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
