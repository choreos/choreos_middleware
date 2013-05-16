package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.NodeDestroyer;
import org.ow2.choreos.deployment.nodes.chef.ConfigToChef;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.NodeCreator;
import org.ow2.choreos.deployment.nodes.cm.NodeUpgrader;
import org.ow2.choreos.deployment.nodes.cm.RecipeApplier;
import org.ow2.choreos.deployment.nodes.selector.NodeSelector;
import org.ow2.choreos.deployment.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.ConfigNotAppliedException;
import org.ow2.choreos.nodes.NPMException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpgradedException;
import org.ow2.choreos.nodes.NodePoolManager;
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
	private NodeRegistry nodeRegistry;

	public NPMImpl(CloudProvider provider) {
		cloudProvider = provider;
		nodeRegistry = NodeRegistry.getInstance();
	}

	@Override
	public Node createNode(Node node, ResourceImpact resourceImpact)
			throws NodeNotCreatedException {

		NodeCreator creator = new NodeCreator(node, resourceImpact, cloudProvider, nodeRegistry, true);
		try {
			node = creator.call();
		} catch (NPMException e) {
			throw new NodeNotCreatedException(node.getId());
		}

		return node;
	}

	@Override
	public List<Node> getNodes() {
		
		if (this.cloudProvider.getProviderName() == FixedCloudProvider.FIXED_CLOUD_PROVIDER) {
			return this.cloudProvider.getNodes();
		} else {
			return nodeRegistry.getNodes();
		}
	}

	@Override
	public Node getNode(String nodeId) throws NodeNotFoundException {
		if (this.cloudProvider.getProviderName() == FixedCloudProvider.FIXED_CLOUD_PROVIDER) {
			return this.cloudProvider.getNode(nodeId);
		} else {
			return nodeRegistry.getNode(nodeId);
		}
	}

	@Override
	public List<Node> applyConfig(Config config)
			throws ConfigNotAppliedException {

		NodeSelector selector = NodeSelectorFactory
				.getInstance(this.cloudProvider);
		List<Node> nodes = selector.selectNodes(config);

		if (nodes == null) {
			throw new ConfigNotAppliedException(config.getName());
		}

		String cookbook = ConfigToChef.getCookbookNameFromConfigName(config
				.getName());
		
		String recipe = ConfigToChef.getRecipeNameFromConfigName(config
				.getName());

		for (Node node : nodes) {
			applyConfig(config, node, cookbook, recipe);
		}

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
	public void upgradeNode(String nodeId) throws NodeNotUpgradedException,
			NodeNotFoundException {

		Node node = this.getNode(nodeId);
		NodeUpgrader upgrader = new NodeUpgrader();

		try {
			upgrader.upgradeNodeConfiguration(node);
		} catch (JSchException e) {
			throw new NodeNotUpgradedException(node.getId(),
					"Could not connect through ssh");
		}
	}

	@Override
	public void destroyNode(String nodeId) throws NodeNotDestroyed,
			NodeNotFoundException {

		this.cloudProvider.destroyNode(nodeId);
	}

	@Override
	public void destroyNodes() throws NodeNotDestroyed {

		List<Thread> trds = new ArrayList<Thread>();
		List<NodeDestroyer> destroyers = new ArrayList<NodeDestroyer>();

		for (Node node : this.getNodes()) {
			NodeDestroyer destroyer = new NodeDestroyer(node, this.cloudProvider, this.nodeRegistry);
			Thread trd = new Thread(destroyer);
			destroyers.add(destroyer);
			trds.add(trd);
			trd.start();
		}

		waitThreads(trds);

		for (NodeDestroyer destroyer : destroyers) {
			if (!destroyer.isOK()) {
				throw new NodeNotDestroyed(destroyer.getNode().getId());
			}
		}
	}

	private void waitThreads(List<Thread> trds) {

		for (Thread t : trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				logger.error("Error while waiting thread", e);
			}
		}
	}


}
