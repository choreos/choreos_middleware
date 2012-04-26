package eu.choreos.servicedeployer.chef;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import eu.choreos.servicedeployer.Configuration;

/**
 * Provide access to Chef functionalities using scripts with chef commands
 * 
 *
 */
public class ChefScripts {
	
	private static final String CHEF_CLEAN_AWS = "clean_aws.sh";
	private static final String CHEF_UPLOAD_COOKBOOK = "knife cookbook upload $cookbookName -o $cookbookParentFolder -c $knifeFile";
	private static final String CHEF_DELETE_COOKBOOK = "knife cookbook delete $cookbookName -y -c $knifeFile";
	private static final String CHEF_LIST_COOKBOOKS = "knife cookbook list -c $knifeFile";

    public static String getCleanAwsNodesAndClients() {

        String config = Configuration.get("CHEF_CONFIG_FILE");
    	URL scriptFile = ClassLoader.getSystemResource(CHEF_CLEAN_AWS);
    	String command = null;
		try {
			command = FileUtils.readFileToString(new File(scriptFile.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Should not happen");
		}
    	command = command.replace("$knifeFile", config);
		return command;
    }
    
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