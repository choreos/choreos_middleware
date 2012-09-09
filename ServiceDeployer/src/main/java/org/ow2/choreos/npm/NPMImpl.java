package org.ow2.choreos.npm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.npm.chef.ConfigToChef;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.selector.NodeSelector;
import org.ow2.choreos.npm.selector.NodeSelectorFactory;

import com.jcraft.jsch.JSchException;


public class NPMImpl implements NodePoolManager {

	private Logger logger = Logger.getLogger(NPMImpl.class);
    private CloudProvider cloudProvider;
    private ConfigurationManager configurationManager = new ConfigurationManager();

    public NPMImpl(CloudProvider provider) {
        cloudProvider = provider;
    }

    @Override
    public Node createNode(Node node) {

        try {
            cloudProvider.createNode(node);
            configurationManager.initializeNode(node);
        } catch (RunNodesException e) {
        	logger.error("Could not create node " + node, e);
        } catch (KnifeException e) {
            logger.error("Could not initialize node " + node, e);
        } catch (JSchException e) {
        	logger.error("Could not connect to the node " + node, e);
        }
        
        return node;
    }

    @Override
    public List<Node> getNodes() {
        return cloudProvider.getNodes();
    }
    
    @Override
    public Node getNode(String id) {
    	
    	List<Node> nodes = getNodes();
    	if (nodes != null) {
    		for (Node node: nodes) {
    			if (node.getId().equals(id))
    				return node;
    		}
    	} 
		return null;
    }

    @Override
    public Node applyConfig(Config config) {

        NodeSelector selector = NodeSelectorFactory.getInstance(this.cloudProvider);
        Node node = selector.selectNode(config);

        if (node == null)
            return null;

        String cookbook = ConfigToChef.getCookbookNameFromConfigName(config.getName());
        String recipe = ConfigToChef.getRecipeNameFromConfigName(config.getName());

        try {
			this.configurationManager.applyRecipe(node, cookbook, recipe);
		} catch (NotAppliedRecipe e) {
			logger.error(e.getMessage(), e);
			return null;
		}

    	return node;
    }
    
    @Override
    public boolean upgradeNodes() {
    	
        List<Node> nodes = cloudProvider.getNodes();
        List<Thread> threads = new ArrayList<Thread>();
        List<NodeUpgrader> upgraders = new ArrayList<NodeUpgrader>();

        for (Node node : nodes) {
        	NodeUpgrader upgrader = new NodeUpgrader(node);
        	upgraders.add(upgrader);
            Thread t = new Thread(upgrader);
            threads.add(t);
            t.start();
        }

        for (int i=0; i<threads.size(); i++) {
            try {
				threads.get(i).join();
				if (!upgraders.get(i).isOk())
					return false;
			} catch (InterruptedException e) {
				logger.error("Could not join thread", e);
				return false;
			}
        }
        return true;
    }

    private class NodeUpgrader implements Runnable {

        private Node node;
        private boolean ok = true;

        public NodeUpgrader(Node node) {
            this.node = node;
        }
        
        public boolean isOk() {
        	return ok;
        }

        @Override
        public void run() {
            try {
				configurationManager.updateNodeConfiguration(node);
			} catch (JSchException e) {
                logger.error("Could not connect to node " + node, e);
                ok = false;
			}
        }
    }

	@Override
	public boolean upgradeNode(String nodeId) {

		Node node;
		try {
			node = cloudProvider.getNode(nodeId);
		} catch (NodeNotFoundException e1) {
			logger.error("Node " + nodeId + " not found");
			return false;
		}
		
		try {
			configurationManager.updateNodeConfiguration(node);
			return true;
		} catch (JSchException e) {
            logger.error("Could not connect to node " + node, e);
            return false;
		}
	}
}
