package eu.choreos.servicedeployer;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class NodePoolManagerHandler {

	private static List<String> deployedRecipe = new ArrayList<String>();

	public Service createNode(String recipe, Service service) {
		deployedRecipe.add(recipe);
		service.setPort(8080);
		service.setUri("choreos-node");
		return service;
	}

	public String getNode(String nodeId) {
		return "choreos-node";
	}

	public List<String> getNodes() {
		return deployedRecipe;
	}

	public void destroyNode(String id) {
	}

	public void initializeNode() {
		System.out.println((new CommandLineInterfaceHelper())
				.runLocalCommand("ssh root@192.168.56.101 chef-client"));
	}

}
