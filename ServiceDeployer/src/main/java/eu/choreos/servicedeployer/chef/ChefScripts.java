package eu.choreos.servicedeployer.chef;

import eu.choreos.servicedeployer.Configuration;

/**
 * Provide access to Chef functionalities using scripts with chef commands
 * 
 *
 */
public class ChefScripts {
	
	private static final String CHEF_UPLOAD_COOKBOOK = "knife cookbook upload $cookbookName -o $cookbookParentFolder -c $knifeFile";
	private static final String CHEF_DELETE_COOKBOOK = "knife cookbook delete $cookbookName -y -c $knifeFile";
	private static final String CHEF_LIST_COOKBOOKS = "knife cookbook list -c $knifeFile";
	
    public static String getUploadCookbook(String cookbookName, String cookbookParentFolder) {
    	
    	String command = CHEF_UPLOAD_COOKBOOK;
        String config = Configuration.get("CHEF_CONFIG_FILE");
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$cookbookName", cookbookName);
    	command = command.replace("$cookbookParentFolder", cookbookParentFolder);
    	
    	return command;
    }
    
    public static String getDeleteCookbook(String cookbookName) {
    	
    	String command = CHEF_DELETE_COOKBOOK;
        String config = Configuration.get("CHEF_CONFIG_FILE");
    	command = command.replace("$knifeFile", config);
    	command = command.replace("$cookbookName", cookbookName);
    	
    	return command;
    }
    
    public static String getListCookbooks() {
    	
    	String command = CHEF_LIST_COOKBOOKS;
        String config = Configuration.get("CHEF_CONFIG_FILE");
    	command = command.replace("$knifeFile", config);
    	
    	return command;
    }
}