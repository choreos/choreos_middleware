package br.usp.ime.choreos.nodepoolmanager.configmanager;

import java.io.IOException;
import java.util.List;

import br.usp.ime.choreos.nodepoolmanager.Node;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.ScriptsProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class NodeConfigurationManager {

    private final String hostname;//TODO remove
    //private final Node node;
    
    @Deprecated
    public NodeConfigurationManager(String hostname) {
        this.hostname = hostname;
    }
    public NodeConfigurationManager() {
        this.hostname = "";// TODO remove 
    }
    
    /*public NodeConfigurationManager(Node node) {
        this.node = node;
        this.hostname = node.getIp(); // ou node.getHostname(); 
    }*/

    @Deprecated
    public String updateNodeConfiguration() throws Exception {
        return new SshUtil(hostname).runCommand("chef-client\n");
    }
    
    
    private String updateNodeConfiguration(String hostname) throws Exception {
        return new SshUtil(hostname).runCommand("chef-client\n");
    }
    
    private String updateNodeConfiguration(Node node) throws Exception {
        //return new SshUtil(hostname).runCommand("chef-client\n");
    	return new SshUtil(node.getIp()).runCommand("chef-client\n");
    }
    
    
  	//private void initializeNode(Node node) {
    public void initializeNode(Node node) {
 		System.out.println("Waiting for SSH...");
 		SshUtil ssh = new SshUtil(node.getIp());
 		while (!ssh.isAccessible())
 			;

 		NodeInitializer ni = new NodeInitializer(node.getIp());
 		try {
 			ni.initialize();
 			//this.installRecipe(node, "getting-started");
 			this.installCookbook(node, "petals");// 
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
    
    public boolean isInitialized(Node node) throws Exception {
        String returnText = "";
        returnText = new SshUtil(node.getIp()).runCommand("ls /opt");
        System.out.println(">>"+returnText+"<<");
        return !returnText.equals("");
    }
    
	public String installCookbook(Node node, String recipe) throws IOException, Exception{
		String command;
		//command = new ScriptsProvider().getChefServerManagerScript(node.getIp());
		command = ScriptsProvider.getChefServerManagerScript(node.getIp(), recipe);
		System.out.println(command);
        Runtime.getRuntime().exec(command);
        
        //return this.updateNodeConfiguration(node.getIp());
        return this.updateNodeConfiguration(node);
		
	}
    

    public void cleanPetals(Node node) throws Exception {
        new SshUtil(node.getIp()).runCommand("sudo rm -rf /opt/*\n");
    }


}
