package eu.choreos.storagefactory;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;
import eu.choreos.storagefactory.recipe.Recipe;
import eu.choreos.storagefactory.recipe.RecipeDeployer;
import eu.choreos.storagefactory.recipe.RecipeFactory;
import eu.choreos.storagefactory.registry.StorageNodeRegistryFacade;
import eu.choreos.storagefactory.utils.NodePoolManagerHandler;
import eu.choreos.storagefactory.utils.SimpleNodePoolManagerHandler;

public class StorageNodeManager {
	public StorageNodeRegistryFacade registry;
	private NodePoolManagerHandler npm;

	public StorageNodeManager(NodePoolManagerHandler nodepoolmanager) {
		this.registry = new StorageNodeRegistryFacade();
		this.npm=nodepoolmanager;
	}
	
	public StorageNodeManager() {
		this.registry = new StorageNodeRegistryFacade();
		this.npm=new SimpleNodePoolManagerHandler();
	}

	
	private Recipe createRecipe(StorageNode node) {
		RecipeFactory factory = new RecipeFactory();

		return factory.createRecipe(node);

	}

	public StorageNode deployNode(StorageNode node) {

		prepareNodeForDatabase(node);

		Recipe recipe = createRecipe(node);

		sendRecipeToNode(node, recipe);
		
		if(node.getUri()==null)//Node was not deployed
			return null;

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

	/**
	 * Create a node according to specifications (StorageNodeSpec)
	 * @param specifications
	 * @return
	 */
	public StorageNode createNewStorageNode(StorageNodeSpec specifications) {
		StorageNode node;

		// Check if it exists
		System.out.println("Registry: Check if it exists ...");

		node = registry.getNode(specifications.getUuid());

		// If it does not
		if (node == null) {
			// Create a node from specifications
			System.out.println("Node don't exist, then deploy a new Node...");
			node = new StorageNode(specifications);
			// And deploy it
			return deployNode(node);
		}

		return node;
	}

	/**
	 * Remove a storage node by Id 
	 * @param storageNodeId
	 */
	public void destroyNode(String storageNodeId) {
		StorageNode storageNode;

		try {
			storageNode = registry.getNode(storageNodeId);

			String id = storageNode.getUuid();

			this.npm.destroyNode(id);

		} catch (Exception e) {
			System.out
					.println("[Storage Manager] Error: Could not find node with ID >"
							+ storageNodeId + "<");
			e.printStackTrace();
		}
	}
	
	
}
