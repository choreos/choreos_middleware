package eu.choreos.nodepoolmanager.chef;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.chef.ScriptsProvider;

public class ScriptsProviderTest {

    @Test
    public void chefScripts() throws Exception {
        
    	String config = Configuration.get("CHEF_CONFIG_FILE");
    	String user = "myUser";
    	String ip = "127.0.0.1";
    	String hostname = "myHost";
    	String cookbook = "cook:recipe";
    	String key = Configuration.get("PRIVATE_SSH_KEY");
    	
        String command = ScriptsProvider.getChefBootstrapScript(key, ip, user);
        String expected = "knife bootstrap 127.0.0.1 -x myUser -i " + key + " --sudo -c " + config;
        assertEquals(expected.trim(), command.trim());

        command = ScriptsProvider.getChefAddCookbook(hostname, cookbook);
        expected = "knife node run_list add myHost cook:recipe -c " + config;
        assertEquals(expected.trim(), command.trim());
    }

}
