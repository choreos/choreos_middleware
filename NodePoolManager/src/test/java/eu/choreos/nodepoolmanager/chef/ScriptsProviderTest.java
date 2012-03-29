package eu.choreos.nodepoolmanager.chef;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.chef.ChefScripts;

public class ScriptsProviderTest {

    @Test
    public void chefScripts() throws Exception {
        
    	String config = Configuration.get("CHEF_CONFIG_FILE");
    	String user = "myUser";
    	String ip = "127.0.0.1";
    	String hostname = "myHost";
    	String cookbook = "cook";
    	String recipe = "recipe";
    	String key = Configuration.get("FIXED_VM_PRIVATE_SSH_KEY");
    	
        String command = ChefScripts.getChefBootstrapScript(key, ip, user);
        String expected = "knife bootstrap 127.0.0.1 -x myUser -i " + key + " --sudo -c " + config;
        assertEquals(expected.trim(), command.trim());

        command = ChefScripts.getChefAddCookbook(hostname, cookbook, recipe);
        expected = "knife node run_list add myHost cook::recipe -c " + config;
        assertEquals(expected.trim(), command.trim());
    }

}
