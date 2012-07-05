package org.ow2.choreos.npm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.npm.chef.ConfigToChef;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.selector.NodeSelector;
import org.ow2.choreos.npm.selector.NodeSelectorFactory;


public class Controller {

	private Logger logger = Logger.getLogger(Controller.class);;
    private final CloudProvider infrastructure;
    private ConfigurationManager configurationManager = new ConfigurationManager();

    public void upgradeNodes() throws Exception {
        final List<Node> nodes = infrastructure.getNodes();

        List<Thread> threads = new ArrayList<Thread>();
        Thread t;
        for (Node node : nodes) {
            t = new Thread(new NodeUpgrader(node));
            t.start();
            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

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
            configurationManager = new ConfigurationManager();// Don't need a hostname and could be
                                                              // a Static Class
            configurationManager.initializeNode(node);
        } catch (RunNodesException e) {
            logger.error("Could not create node " + node, e);
        } catch (Exception e) {
            logger.error("Could not create node " + node, e);
        }

        return node.getId();
    }

    public List<Node> getNodes() {
        return infrastructure.getNodes();
    }
    
    public Node getNode(String id) throws NodeNotFoundException {
    	
    	List<Node> nodes = getNodes();
    	if (nodes != null) {
    		for (Node node: nodes) {
    			if (node.getId().equals(id))
    				return node;
    		}
    	} 
		throw new NodeNotFoundException("Node" + id + " not found");
    }

    /**
     * Applies the received configuration in a node that will be selected by the Node Pool Manager
     * 
     * @param config
     *            the <code>name</code> in the <code>config</code> corresponds to a chef recipe name
     * @return the node where the <code>config</code> was applied or null if not applied
     */
    public Node applyConfig(Config config) {

        NodeSelector selector = NodeSelectorFactory.getInstance(this.infrastructure);
        Node node = selector.selectNode(config);

        if (node == null)
            return null;

        String cookbook = ConfigToChef.getCookbookNameFromConfigName(config.getName());
        String recipe = ConfigToChef.getRecipeNameFromConfigName(config.getName());

        try {
            this.configurationManager.installRecipe(node, cookbook, recipe);
        } catch (Exception e) {
            node = null;
        }

        return node;
    }

    private class NodeUpgrader implements Runnable {

        private Node node;

        public NodeUpgrader(Node node) {
            this.node = node;
        }

        @Override
        public void run() {
            try {
                configurationManager.updateNodeConfiguration(node);
            } catch (Exception e) {
                logger.error("Error on Controller while upgrading node " + node, e);
            }
        }
    }
}
