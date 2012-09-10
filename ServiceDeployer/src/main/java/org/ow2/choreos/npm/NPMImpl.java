package org.ow2.choreos.npm;

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

/**
 * 
 * @author leonardo, furtado
 *
 */
public class NPMImpl implements NodePoolManager {

	private Logger logger = Logger.getLogger(NPMImpl.class);
	
    private CloudProvider cloudProvider;
    private ConfigurationManager configurationManager = new ConfigurationManager();

    public NPMImpl(CloudProvider provider) {
        cloudProvider = provider;
    }

    @Override
    public Node createNode(Node node) throws NodeNotCreatedException {

        try {
            cloudProvider.createNode(node);
        } catch (RunNodesException e) {
        	throw new NodeNotCreatedException(node.getId(), "Could not create VM to node " + node);
        }
        
        try {
        	configurationManager.initializeNode(node);
        } catch (KnifeException e) {
        	throw new NodeNotCreatedException(node.getId(), "Could not initialize node " + node);
        } catch (JSchException e) {
        	throw new NodeNotCreatedException(node.getId(), "Could not connect to the node " + node);
        }
        
        return node;
    }

    @Override
    public List<Node> getNodes() {
        return cloudProvider.getNodes();
    }
    
    @Override
    public Node getNode(String nodeId) throws NodeNotFoundException {
    	return cloudProvider.getNode(nodeId);
    }

    @Override
    public Node applyConfig(Config config) throws ConfigNotAppliedException {

        NodeSelector selector = NodeSelectorFactory.getInstance(this.cloudProvider);
        Node node = selector.selectNode(config);

        if (node == null) {
        	throw new ConfigNotAppliedException(config.getName());
        }

        String cookbook = ConfigToChef.getCookbookNameFromConfigName(config.getName());
        String recipe = ConfigToChef.getRecipeNameFromConfigName(config.getName());

        final int TRIALS = 3;
        final int SLEEP_TIME = 1000;
        int step = 0;
        boolean ok = false;
        
        while (!ok) {
        	try {
				this.configurationManager.applyRecipe(node, cookbook, recipe);
				ok = true;
			} catch (ConfigNotAppliedException e) {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e1) {
					logger.error("Exception at sleeping!", e1);
				}
				step++;
				if (step > TRIALS)
					throw new ConfigNotAppliedException(config.getName()); 
			}
        }

    	return node;
    }
    
	@Override
	public void upgradeNode(String nodeId) throws NodeNotUpgradedException, NodeNotFoundException {

		Node node = this.getNode(nodeId);
		
		try {
			configurationManager.updateNodeConfiguration(node);
		} catch (JSchException e) {
			throw new NodeNotUpgradedException(node.getId(), "Could not connect through ssh");
		}
	}

	@Override
	public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException {

		this.cloudProvider.destroyNode(nodeId);
	}

}
