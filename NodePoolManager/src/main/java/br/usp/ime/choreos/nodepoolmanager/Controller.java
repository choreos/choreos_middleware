package br.usp.ime.choreos.nodepoolmanager;

import java.util.List;

import org.jclouds.compute.RunNodesException;

import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import br.usp.ime.choreos.nodepoolmanager.configmanager.NodeInitializer;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class Controller {
	
	private final CloudProvider infrastructure;
	
	public Controller(CloudProvider provider) {
		
		infrastructure = provider;
	}
	
	/**
	 * 
	 * @param node
	 * @return the node id
	 */
	public String createNode(Node node) {

        try {
			infrastructure.createNode(node);
			initializeNode(node);
		} catch (RunNodesException e) {
			e.printStackTrace();
		}
        
        return node.getId();
	}
	
	// this method shouldn't belong to this class
	private void initializeNode(Node node) {
		System.out.println("Waiting for SSH...");
		SshUtil ssh = new SshUtil(node.getIp());
		while (!ssh.isAccessible())
			;

		NodeInitializer ni = new NodeInitializer(node.getIp());
		try {
			ni.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Node> getNodes() {
		return infrastructure.getNodes();
	}
}
