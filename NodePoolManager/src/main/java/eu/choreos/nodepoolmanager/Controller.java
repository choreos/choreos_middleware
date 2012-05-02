package eu.choreos.nodepoolmanager;

import java.util.List;

import org.jclouds.compute.RunNodesException;

import eu.choreos.nodepoolmanager.chef.ConfigToChef;
import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.selector.NodeSelector;
import eu.choreos.nodepoolmanager.selector.NodeSelectorFactory;

public class Controller {

	private final CloudProvider infrastructure;
	private ConfigurationManager configurationManager = new ConfigurationManager();

	public Controller(CloudProvider provider) {
		infrastructure = provider;
	}

	/**
	 * 
	 * @param nodeRest
	 * @return the node id
	 */
	public String createNode(Node node) {

		try {
			infrastructure.createNode(node);
			configurationManager = new ConfigurationManager();//Don't need a hostname and could be a Static Class  
			configurationManager.initializeNode(node);
		} catch (RunNodesException e) {
			e.printStackTrace();
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}

		return node.getId();
	}


	public List<Node> getNodes() {
		return infrastructure.getNodes();
	}

	/**
	 * Applies the received configuration in a node that will be selected by the Node Pool Manager
	 * @param config the <code>name</code> in the <code>config</code> corresponds to a chef recipe name
	 * @return the node where the <code>config</code> was applied or null if not applied
	 */
	public Node applyConfig(Config config) {
		
		NodeSelector selector = NodeSelectorFactory.getInstance(this.infrastructure);
		Node node = selector.selectNode(config);
		if (node == null)
			return null;
		
		String cookbook = ConfigToChef.getCookbookNameFromConfigName(config.getName());
		String recipe = ConfigToChef.getRecipeNameFromConfigName(config.getName());
		System.out.println("Controller: Installing Recipe ...");
		boolean status = this.configurationManager.installRecipe(node, cookbook, recipe);
		
		if (!status)
			return null;
		return node;
	}
}
