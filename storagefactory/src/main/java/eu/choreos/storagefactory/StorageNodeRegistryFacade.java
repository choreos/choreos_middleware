package eu.choreos.storagefactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import eu.choreos.storagefactory.datamodel.StorageNode;


public class StorageNodeRegistryFacade {
	public Map<String, StorageNode> instantiatedStorages;

	public StorageNodeRegistryFacade() {
		instantiatedStorages = new HashMap<String, StorageNode>();
	}

	public void registerNode(StorageNode storageNode) {
		instantiatedStorages.put(storageNode.getUuid(), storageNode);
	}

	public void unregisterNode(String storageNodeId) {
		instantiatedStorages.remove(storageNodeId);
	}

	public StorageNode getNode(String nodeId) throws Exception {
		return instantiatedStorages.get(nodeId);
	}

	public Collection<StorageNode> getNodes()
			throws UnsupportedOperationException {
		return instantiatedStorages.values();
	}
}