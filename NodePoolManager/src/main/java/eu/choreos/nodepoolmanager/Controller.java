package eu.choreos.nodepoolmanager;

import java.util.List;

import org.jclouds.compute.RunNodesException;

import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.configmanager.NodeConfigurationManager;
import eu.choreos.nodepoolmanager.datamodel.Node;


public class Controller {

	private final CloudProvider infrastructure;
	private NodeConfigurationManager configurationManager = new NodeConfigurationManager();

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
			configurationManager = new NodeConfigurationManager();//Don't need a hostname and could be a Static Class  
			configurationManager.initializeNode(node);
			configurationManager.installCookbook(node, "petals");//must be getting started
		} catch (RunNodesException e) {
			e.printStackTrace();
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}

		return node.getId();
	}

	/**
	 * 
	 * @param node
	 * @param cookbook
	 * @return the node id
	 */
	public String createNode(Node node, Cookbook cookbook) {

		//TODO
		return null;
	}

	public List<Node> getNodes() {
		return infrastructure.getNodes();
	}

}
