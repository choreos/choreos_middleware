package eu.choreos.storagefactory;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;
import eu.choreos.storagefactory.recipe.Recipe;
import eu.choreos.storagefactory.recipe.RecipeDeployer;
import eu.choreos.storagefactory.recipe.RecipeFactory;
import eu.choreos.storagefactory.registry.StorageNodeRegistryFacade;

public class StorageNodeManager {
	public StorageNodeRegistryFacade registry;
	private NodePoolManagerHandler npm;

	public StorageNodeManager() {
		this.registry = new StorageNodeRegistryFacade();
		this.setNodePoolManagerHandler(new NodePoolManagerHandler());
	}

	public NodePoolManagerHandler getNodePoolManagerHandler() {
		return npm;
	}

	public void setNodePoolManagerHandler(NodePoolManagerHandler npm) {
		this.npm = npm;
	}

	private Recipe createRecipe(StorageNode node) {
		RecipeFactory factory = new RecipeFactory();

		return factory.createRecipe(node);

	}

	public StorageNode deployNode(StorageNode node) {

		prepareNodeForDatabase(node);

		Recipe recipe = createRecipe(node);

		sendRecipeToNode(node, recipe);

		return node;
	}

	private void prepareNodeForDatabase(StorageNode node) {
		// Currently works only on MYSQL
		node.setSchema(node.getUuid());
		node.setUser(node.getUuid());
		node.setPassword(node.getUuid());
	}

	private void sendRecipeToNode(StorageNode node, Recipe recipe) {
		RecipeDeployer deployer = new RecipeDeployer(npm);

		String deployedHostname;
		deployedHostname = deployer.deployRecipe(recipe);

		node.setUri(deployedHostname);
	}

	public StorageNode createNewStorageNode(StorageNodeSpec specifications) {
		StorageNode node = new StorageNode(specifications);
		deployNode(node);

		return node;
	}

	public void destroyNode(String storageNodeId) {
		StorageNode storageNode;

		try {
			storageNode = registry.getNode(storageNodeId);

			String id = storageNode.getUuid();

			getNodePoolManagerHandler().destroyNode(id);

		} catch (Exception e) {
			System.out
					.println("[Storage Manager] Error: Could not find node with ID >"
							+ storageNodeId + "<");
			e.printStackTrace();
		}
	}
}
