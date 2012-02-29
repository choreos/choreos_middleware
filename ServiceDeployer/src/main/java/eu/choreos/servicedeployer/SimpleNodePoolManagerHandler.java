package eu.choreos.servicedeployer;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.utils.CommandLineInterfaceHelper;


public class SimpleNodePoolManagerHandler extends NodePoolManagerHandler{

	public static String  KNIFE_CONFIG_FILE_OPTION = 
			Configuration.get("CHEF_CONFIG_FILE")!=null?" -c "+Configuration.get("CHEF_CONFIG_FILE"):"";
	private static List<String> deployedRecipe = new ArrayList<String>();

	public Service createNode(String recipe, Service service) {
		deployedRecipe.add(recipe);
		System.out.println("knife node run_list add choreos-node " + recipe+KNIFE_CONFIG_FILE_OPTION);
		(new CommandLineInterfaceHelper())
				.runLocalCommand("knife node run_list add choreos-node "
						+ recipe+KNIFE_CONFIG_FILE_OPTION);

		initializeNode();
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
