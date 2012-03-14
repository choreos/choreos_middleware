package eu.choreos.nodepoolmanager;

import java.io.IOException;

import eu.choreos.nodepoolmanager.chef.ScriptsProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.CommandLine;
import eu.choreos.nodepoolmanager.utils.SshUtil;


public class ConfigurationManager {

	private static String INITIAL_RECIPE = "getting-started";
	private static String CHEF_REPO = Configuration.get("CHEF_REPO");
	
    private String updateNodeConfiguration(Node node) throws Exception {
    	return new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile()).runCommand("sudo chef-client\n");
    }
    
    public void initializeNode(Node node) {
 		
    	System.out.println("Waiting for SSH...");
 		SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
 		while (!ssh.isAccessible()){
 				System.out.println("Could not connect to " + node.getHostname() +  " using username " + node.getUser() + " yet");
 				System.out.println("Trying again in 5 seconds");
 				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {}
 		}
 		System.out.println("Connected");

 		String command;
		try {
			// bootstrap node
			System.out.println("Bootstraping " + node.getHostname());
			command = ScriptsProvider.getChefBootstrapScript(node.getPrivateKeyFile(), node.getIp(), node.getUser());
			System.out.println(command);
			CommandLine.runLocalCommand(command, CHEF_REPO);
			System.out.println("Bootstrap completed");
			
			// get chef name
			command = ScriptsProvider.getChefName();
			String chefClientName = ssh.runCommand(command);
			node.setChefName(chefClientName);
			
			// install cookbook
			this.installInitialRecipe(node);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
    
    public boolean isInitialized(Node node) throws NodeNotAccessible, Exception {
        
    	SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
    	
    	//if (!ssh.isAccessible())
    		//throw new NodeNotAccessible(node);
    	
    	while (!ssh.isAccessible()){
				System.out.println("Could not connect to " + node.getIp() +  " using username " + node.getUser() + " yet");
				System.out.println("Trying again in 5 seconds");
				try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
		}
    	
    	String createdFile = "chef-getting-started.txt";
    	String returnText = null;
		returnText = ssh.runCommand("ls " + createdFile);
    	System.out.println(">>"+returnText.trim()+"<<");
    	return returnText.trim().equals(createdFile);
    }
    
	public void installRecipe(Node node, String cookbook) throws IOException, Exception{
		
        this.installRecipe(node, cookbook, "default");
	}

	/**
	 * 
	 * @param node
	 * @param cookbook
	 * @param recipe
	 * @return false if recipe not applied
	 * @throws IOException
	 */
	public boolean installRecipe(Node node, String cookbook, String recipe) {
		
		try {
			if (!isInitialized(node)) {
				System.out.println("Node not initialized yet. Going to initialize it");
				this.initializeNode(node);
			}
		} catch (NodeNotAccessible e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		
		String command = ScriptsProvider.getChefAddCookbook(node.getChefName(), cookbook, recipe);
		System.out.println("Install recipe command = [" + command+"]");
        CommandLine.runLocalCommand(command);
        // TODO we should verify if the recipe install was OK
        // but it is very awkward make this without using the chef API!
        
        try {
			this.updateNodeConfiguration(node);
		} catch (Exception e) {
			return false;
		}
        return true;
	}
	
	private void installInitialRecipe(Node node) {
		
		String command = ScriptsProvider.getChefAddCookbook(node.getChefName(), INITIAL_RECIPE, "default");
		System.out.println("Install recipe command = [" + command+"]");
        CommandLine.runLocalCommand(command);
        
		try {
			this.updateNodeConfiguration(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
