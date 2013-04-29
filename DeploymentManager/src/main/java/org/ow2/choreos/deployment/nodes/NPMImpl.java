package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.deployment.nodes.chef.ConfigToChef;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.cm.NodeNotBootstrappedException;
import org.ow2.choreos.deployment.nodes.cm.NodeUpgrader;
import org.ow2.choreos.deployment.nodes.cm.RecipeApplier;
import org.ow2.choreos.deployment.nodes.selector.NodeSelector;
import org.ow2.choreos.deployment.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, furtado
 *
 */
public class NPMImpl implements NodePoolManager {

	private Logger logger = Logger.getLogger(NPMImpl.class);
	
    private CloudProvider cloudProvider;

    public NPMImpl(CloudProvider provider) {
        cloudProvider = provider;
    }

    @Override
    public Node createNode(Node node, ResourceImpact resourceImpact) throws NodeNotCreatedException {

    	return this.createNode(node, resourceImpact, true);
    }
    
    private Node createNode(Node node, ResourceImpact resourceImpact, boolean retry) throws NodeNotCreatedException {
    	
        try {
            cloudProvider.createNode(node, resourceImpact);
        } catch (RunNodesException e) {
        	if (retry) {
        		logger.warn("Could not create VM. Going to try again!");
        		this.createNode(node, resourceImpact, false);        		
        	} else {
        		throw new NodeNotCreatedException(node.getId(), "Could not create VM");
        	}
        }
        
        try {
        	NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
        	bootstrapper.bootstrapNode();
        } catch (KnifeException e) {
        	throw new NodeNotCreatedException(node.getId(), "Could not initialize node " + node);
        } catch (NodeNotBootstrappedException e) {
        	throw new NodeNotCreatedException(node.getId(), "Could not initialize node " + node);
        } catch (NodeNotAccessibleException e) {
        	if (retry) {
        		logger.warn("Could not connect to the node " + node +  ". We will forget this node and try a new one.");
        		this.createNode(node, resourceImpact, false);
        	} else {
        		throw new NodeNotCreatedException(node.getId(), "Could not connect to the node " + node);
        	}
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
    public List<Node> applyConfig(Config config) throws ConfigNotAppliedException {

        NodeSelector selector = NodeSelectorFactory.getInstance(this.cloudProvider);
        List<Node> nodes = selector.selectNodes(config);

        if (nodes == null) {
        	throw new ConfigNotAppliedException(config.getName());
        }

        String cookbook = ConfigToChef.getCookbookNameFromConfigName(config.getName());
        String recipe = ConfigToChef.getRecipeNameFromConfigName(config.getName());

        for(Node node : nodes)
        	applyConfig(config, node, cookbook, recipe);

    	return nodes;
    }

	private void applyConfig(Config config, Node node, String cookbook,
			String recipe) throws ConfigNotAppliedException {
		final int TRIALS = 3;
        final int SLEEP_TIME = 1000;
        int step = 0;
        boolean ok = false;
        
        while (!ok) {
        	try {
        	    RecipeApplier recipeApplyer = new RecipeApplier();
       	    	recipeApplyer.applyRecipe(node, cookbook, recipe);
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
	}
    
	@Override
	public void upgradeNode(String nodeId) throws NodeNotUpgradedException, NodeNotFoundException {

		Node node = this.getNode(nodeId);
		NodeUpgrader upgrader = new NodeUpgrader();
		
		try {
			upgrader.upgradeNodeConfiguration(node);
		} catch (JSchException e) {
			throw new NodeNotUpgradedException(node.getId(), "Could not connect through ssh");
		}
	}

	@Override
	public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException {

		this.cloudProvider.destroyNode(nodeId);
	}

	@Override
	public void destroyNodes() throws NodeNotDestroyed {
		
		List<Thread> trds = new ArrayList<Thread>();
		List<NodeDestroyer> destroyers = new ArrayList<NodeDestroyer>();

		for (Node node: this.getNodes()) {
			NodeDestroyer destroyer = new NodeDestroyer(node); 
			Thread trd = new Thread(destroyer);
			destroyers.add(destroyer);
			trds.add(trd);
			trd.start();
		}		
		
		waitThreads(trds);
		
		for (NodeDestroyer destroyer: destroyers) {
			if (!destroyer.ok) {
				throw new NodeNotDestroyed(destroyer.node.getId());
			}
		}
	}
	
	private void waitThreads(List<Thread> trds) {

		for (Thread t: trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				logger.error("Error while waiting thread", e);
			}
		}
	}

	private class NodeDestroyer implements Runnable {

		Node node;
		boolean ok;
		
		public NodeDestroyer(Node node) {
			this.node = node;
		}
		
		@Override
		public void run() {
			try {
				destroyNode(node.getId());
			} catch (NodeNotDestroyed e) {
				ok = false;
				logger.error("Node not destroyed", e);
			} catch (NodeNotFoundException e) {
				ok = false;
				logger.error("Impossible!", e);
			}
			logger.info("Node " + node.getId() + " destroyed");
			ok = true;
		}

		
	}

}
