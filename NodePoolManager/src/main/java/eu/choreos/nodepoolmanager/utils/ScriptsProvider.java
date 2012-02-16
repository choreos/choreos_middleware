package eu.choreos.nodepoolmanager.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import eu.choreos.nodepoolmanager.Configuration;


public class ScriptsProvider {
	
	private static String CHEF_BOOTSTRAP_SCRIPT = "chef/chef_bootstrap.sh";
	private static String CHEF_ADD_COOKBOOK_SCRIPT = "chef/chef_add_cookbook.sh";
	private static String CHEF_NAME_SCRIPT = "chef/my_chef_name.sh";
	
    public static String getChefBootstrapScript(String pKeyFile, String ip, String user) throws IOException {
    	
    	URL scriptFile = ClassLoader.getSystemResource(CHEF_BOOTSTRAP_SCRIPT);
        String command = FileUtils.readFileToString(new File(scriptFile.getFile()));

        String config = Configuration.get("CHEF_CONFIG_FILE");

        command = command.replace("$privateKeyFile", pKeyFile);
        command = command.replace("$ip", ip);
        command = command.replace("$user", user);
        command = command.replace("$knifeFile", config);
        
        return command;
    }

    public static String getChefAddCookbook(String nodeName, String ip, String cookbook) throws IOException {
    	
    	URL scriptFile = ClassLoader.getSystemResource(CHEF_ADD_COOKBOOK_SCRIPT);
    	String command = FileUtils.readFileToString(new File(scriptFile.getFile()));

        String config = Configuration.get("CHEF_CONFIG_FILE");

        System.out.println("<<"+command);
        System.out.println("nodeName= " + nodeName);
    	command = command.replace("$nodeName", nodeName);
    	command = command.replace("$ip", ip);
    	command = command.replace("$recipe", "default");
    	command = command.replace("$cookbook", cookbook);
    	command = command.replace("$knifeFile", config);

    	return command;
    }
    
    public static String getChefName() throws IOException {
    	
    	URL scriptFile = ClassLoader.getSystemResource(CHEF_NAME_SCRIPT);
    	String command = FileUtils.readFileToString(new File(scriptFile.getFile()));
    	return command;
    }

}