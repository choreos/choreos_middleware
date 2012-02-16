package br.usp.ime.choreos.nodepoolmanager.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import br.usp.ime.choreos.nodepoolmanager.Configuration;

public class ScriptsProvider {

    public String getChefBootstrapScript(String keyFileName) throws IOException {
    	URL scriptFile = ClassLoader.getSystemResource("chef/bootstrap_script.sh");
        String command = FileUtils.readFileToString(new File(scriptFile.getFile()));

        String pkey = FileUtils.readFileToString(new File(keyFileName));

        command = command.replace("$pkey", pkey);
        command = command.replace("$chefserver", Configuration.get("CHEF_SERVER_URL"));
        return command.replace("$validator", Configuration.get("VALIDATION_CLIENT_NAME"));
    }

    public String getChefServerManagerScript(String hostname) throws IOException {
    	URL scriptFile = ClassLoader.getSystemResource("chef/chef_server_manager.sh");
    	String command = FileUtils.readFileToString(new File(scriptFile.getFile()));

    	String user = Configuration.get("CHEF_USER");
    	String user_key_file = Configuration.get("CHEF_USER_KEY_FILE");
    	String chef_server_url = Configuration.get("CHEF_SERVER_URL");

    	command = command.replace("$userkeyfile", user_key_file);
    	command = command.replace("$chefserverurl", chef_server_url);
    	command = command.replace("$hostname", hostname);
    	command = command.replace("$recipe", "default");
    	command = command.replace("$cookbook", "petals");
    	return command.replace("$chefuser", user);
    }
 
    public static String getChefServerManagerScript(String hostname, String chefCookbook) throws IOException {
    	URL scriptFile = ClassLoader.getSystemResource("chef/chef_server_manager.sh");
    	String command = FileUtils.readFileToString(new File(scriptFile.getFile()));

    	String chefUser = Configuration.get("CHEF_USER");
    	String user_key_file = Configuration.get("CHEF_USER_KEY_FILE");
    	String chef_server_url = Configuration.get("CHEF_SERVER_URL");

    	command = command.replace("$userkeyfile", user_key_file);
    	command = command.replace("$chefserverurl", chef_server_url);
    	command = command.replace("$hostname", hostname);
    	command = command.replace("$recipe", "default");
    	//command = command.replace("$cookbook", "petals");
    	command = command.replace("$cookbook", chefCookbook);
    	return command.replace("$chefuser", chefUser);
    }

}