package eu.choreos.nodepoolmanager.configmanager;

import java.io.IOException;

import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.ScriptsProvider;
import eu.choreos.nodepoolmanager.utils.SshUtil;


public class NodeConfigurationManager {

	private static String INITIAL_RECIPE = "getting-started";
	
    private String updateNodeConfiguration(Node node) throws Exception {
    	return new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile()).runCommand("chef-client\n");
    }
    
    public void initializeNode(Node node) {
 		
    	System.out.println("Waiting for SSH...");
 		SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
 		while (!ssh.isAccessible())
 			;

 		String command;
		try {
			// bootstrap node
			command = ScriptsProvider.getChefBootstrapScript(node.getPrivateKeyFile(), node.getIp(), node.getUser());
			System.out.println(command);
			Runtime.getRuntime().exec(command);
			
			// get chef name
			command = ScriptsProvider.getChefName();
			String chefClientName = ssh.runCommand(command);
			node.setChefName(chefClientName);
			
			// install cookbook
			this.installCookbook(node, INITIAL_RECIPE);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
    
    public boolean isInitialized(Node node) throws Exception {
        
    	String createdFile = "chef-getting-started.txt";
    	String returnText = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile()).runCommand("ls " + createdFile);
        System.out.println(">>"+returnText+"<<");
    	return returnText.equals(createdFile);
    }
    
	public String installCookbook(Node node, String cookbook) throws IOException, Exception{
		
		String command = ScriptsProvider.getChefAddCookbook(node.getChefName(), node.getIp(), cookbook);
		System.out.println(command);
        Runtime.getRuntime().exec(command);
        
        return this.updateNodeConfiguration(node);
	}

}
