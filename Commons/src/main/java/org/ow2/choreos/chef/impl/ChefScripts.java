package org.ow2.choreos.chef.impl;

import java.util.List;

/**
 * Provide access to Chef functionalities using scripts with knife commands
 * 
 * Projected to Chef version 10.12.0
 *
 */
public class ChefScripts {
	
	private static final String KNIFE_BOOTSTRAP = "knife bootstrap $ip -x $user -i $privateKeyFile --no-host-key-verify --sudo -c $knifeFile";
	private static final String KNIFE_RUN_LIST_ADD = "knife node run_list add $nodeName $recipeFullName -c $knifeFile";
	private static final String KNIFE_NODE_LIST = "knife node list -c $knifeFile";
	private static final String KNIFE_NODE_SHOW = "knife node show $nodeName -c $knifeFile";
	private static final String KNIFE_NODE_DELETE = "knife node delete $nodeName -c $knifeFile -y";
	private static final String KNIFE_NODE_CREATE = "knife node create -d $nodeName -c $knifeFile";
	private static final String KNIFE_CLIENT_DELETE = "knife client delete $clientName -c $knifeFile -y";
	private static final String KNIFE_COOKBOOK_UPLOAD = "knife cookbook upload $cookbookName -o $cookbookParentFolder -c $knifeFile";
	private static final String KNIFE_COOKBOOK_DELETE = "knife cookbook delete $cookbookName -a -y -c $knifeFile";
	private static final String KNIFE_COOKBOOK_LIST = "knife cookbook list -c $knifeFile";

	private String config;
	
	/**
	 * 
	 * @param chefConfigFile The knife.rb file
	 */
	public ChefScripts(String chefConfigFile) {
		
		this.config = chefConfigFile;
	}
    
    public String getKnifeBootstrap(String pKeyFile, String ip, String user, List<String> defaultRecipes) {
    	
        String command = KNIFE_BOOTSTRAP;
        command = command.replace("$privateKeyFile", pKeyFile);
        command = command.replace("$ip", ip);
        command = command.replace("$user", user);
        command = command.replace("$knifeFile", config);
        if (defaultRecipes != null && !defaultRecipes.isEmpty()) {
	        command += " --run-list ";
        	for (int i=0; i<defaultRecipes.size(); i++) {
	        	command += defaultRecipes.get(i);
	        	if (i != defaultRecipes.size()-1) {
	        		command += ", ";
	        	}
	        }
        }
        return command;
    }
    
    public String getKnifeRunListAdd(String nodeName, String recipeFullName) {
    	
    	String command = KNIFE_RUN_LIST_ADD;
    	command = command.replace("$nodeName", nodeName);
    	command = command.replace("$recipeFullName", recipeFullName);
    	command = command.replace("$knifeFile", config);
    	return command;
    }
    
    public String getKnifeNodeList() {
    	
    	String command = KNIFE_NODE_LIST;
    	command = command.replace("$knifeFile", config);
    	return command;
    }
    
    public String getKnifeNodeShow(String nodeName) {
    	
    	String command = KNIFE_NODE_SHOW;
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$nodeName", nodeName);
    	return command;
    }
    
    public String getKnifeNodeDelete(String nodeName) {
    	
    	String command = KNIFE_NODE_DELETE;
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$nodeName", nodeName);
    	return command;
    }
    
    public String getKnifeNodeCreate(String nodeName) {
    	
    	String command = KNIFE_NODE_CREATE;
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$nodeName", nodeName);
    	return command;
    }

    public String getKnifeClientDelete(String clientName) {
    	
    	String command = KNIFE_CLIENT_DELETE;
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$clientName", clientName);
    	return command;
    }
    
    public String getKnifeCookbookUpload(String cookbookName, String cookbookParentFolder) {
    	
    	String command = KNIFE_COOKBOOK_UPLOAD;
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$cookbookName", cookbookName);
    	command = command.replace("$cookbookParentFolder", cookbookParentFolder);
    	return command;
    }
    
    public String getKnifeCookbookDelete(String cookbookName) {
    	
    	String command = KNIFE_COOKBOOK_DELETE;
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$cookbookName", cookbookName);
    	return command;
    }
    
    public String getKnifeCookbooksList() {
    	
    	String command = KNIFE_COOKBOOK_LIST;
    	command = command.replace("$knifeFile", config);
    	return command;
    }

}