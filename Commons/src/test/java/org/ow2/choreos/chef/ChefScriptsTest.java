package org.ow2.choreos.chef;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ow2.choreos.chef.impl.ChefScripts;

public class ChefScriptsTest {

    @Test
    public void chefScripts() throws Exception {
        
    	String config = "knife.rb";
    	String user = "myUser";
    	String ip = "127.0.0.1";
    	String hostname = "myHost";
    	String cookbook = "cook";
    	String recipe = "recipe";
    	String key = "myKey";
    	
    	ChefScripts cs = new ChefScripts(config);
    	
        String command = cs.getKnifeBootstrap(key, ip, user);
        String expected = "knife bootstrap 127.0.0.1 -x myUser -i " + key + " --sudo -c " + config;
        assertEquals(expected.trim(), command.trim());

        command = cs.getKnifeRunListAdd(hostname, cookbook, recipe);
        expected = "knife node run_list add myHost cook::recipe -c " + config;
        assertEquals(expected.trim(), command.trim());
    }

}
