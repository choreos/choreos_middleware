package eu.choreos.nodepoolmanager.chef;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import eu.choreos.nodepoolmanager.Configuration;

/**
 * Provide access to Chef functionalities using scripts with chef commands
 * 
 *
 */
public class ChefScripts {
	
	private static final String CHEF_NAME_SCRIPT = "chef/my_chef_name.sh";
	private static final String CHEF_BOOTSTRAP_SCRIPT = "knife bootstrap $ip -x $user -i $privateKeyFile --sudo -c $knifeFile";
	private static final String CHEF_ADD_COOKBOOK_SCRIPT = "knife node run_list add $nodeName $cookbook::$recipe -c $knifeFile";
	private static final String CHEF_NODE_LIST = "knife node list -c $knifeFile";
	private static final String CHEF_NODE_SHOW = "knife node show $nodeName -c $knifeFile";
	private static final String CHEF_NODE_DELETE = "knife node delete $nodeName -c $knifeFile -y";
	private static final String CHEF_CLIENT_DELETE = "knife client delete $clientName -c $knifeFile -y";
	
    public static String getChefName() {
    	
    	URL scriptFile = ClassLoader.getSystemResource(CHEF_NAME_SCRIPT);
    	String command = null;
		try {
			command = FileUtils.readFileToString(new File(scriptFile.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Should not happen");
		}
    	return command;
    }
    
    public static String getChefBootstrapScript(String pKeyFile, String ip, String user) {
    	
        String command = CHEF_BOOTSTRAP_SCRIPT;

        String config = Configuration.get("CHEF_CONFIG_FILE");

        command = command.replace("$privateKeyFile", pKeyFile);
        command = command.replace("$ip", ip);
        command = command.replace("$user", user);
        command = command.replace("$knifeFile", config);
        
        return command;
    }
    
    public static String getChefAddCookbook(String nodeName, String cookbook, String recipe) {
    	
    	String command = CHEF_ADD_COOKBOOK_SCRIPT;

        String config = Configuration.get("CHEF_CONFIG_FILE");

    	command = command.replace("$nodeName", nodeName);
    	command = command.replace("$cookbook", cookbook);
    	command = command.replace("$recipe", recipe);
    	command = command.replace("$knifeFile", config);

    	return command;
    }
    
    public static String getChefNodeList() {
    	
    	String command = CHEF_NODE_LIST;

        String config = Configuration.get("CHEF_CONFIG_FILE");
    	command = command.replace("$knifeFile", config);

    	return command;
    }
    
    public static String getChefNodeShow(String nodeName) {
    	
    	String command = CHEF_NODE_SHOW;

        String config = Configuration.get("CHEF_CONFIG_FILE");
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$nodeName", nodeName);

    	return command;
    }
    
    public static String getDeleteNode(String nodeName) {
    	
    	String command = CHEF_NODE_DELETE;

        String config = Configuration.get("CHEF_CONFIG_FILE");
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$nodeName", nodeName);

    	return command;
    }

    public static String getDeleteClient(String clientName) {
    	
    	String command = CHEF_CLIENT_DELETE;

        String config = Configuration.get("CHEF_CONFIG_FILE");
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$clientName", clientName);

    	return command;
    }
    
}