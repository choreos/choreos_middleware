package eu.choreos.storagefactory;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;
import eu.choreos.storagefactory.recipe.Recipe;
import eu.choreos.storagefactory.recipe.RecipeDeployer;
import eu.choreos.storagefactory.recipe.RecipeFactory;

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

	public StorageNode registerNewStorageNode(StorageNodeSpec nodeSpec) {

		StorageNode storageNode = new StorageNode();
		storageNode.setType(nodeSpec.getType());
		storageNode.setUuid(nodeSpec.getUuid());
		
		registry.registerNode(storageNode);

		System.out.println("Node created");
		return storageNode;
	}

	public void prepareForDeployment(StorageNodeSpec nodeSpec){
		RecipeFactory factory = new RecipeFactory();
		RecipeDeployer deployer = new RecipeDeployer(npm);
		
		Recipe recipe = factory.createRecipe();
		
		deployer.deployRecipe(recipe);
	}
	public void destroyNode(String storageNodeId) {
		StorageNode storageNode;

		try {
			storageNode = registry.getNode(storageNodeId);

			String id = storageNode.getUuid();
			
			getNodePoolManagerHandler().destroyNode(id);

			registry.unregisterNode(storageNodeId);

		} catch (Exception e) {
			System.out
					.println("[Storage Manager] Error: Could not find node with ID >"
							+ storageNodeId + "<");
			e.printStackTrace();
		}
	}
}
