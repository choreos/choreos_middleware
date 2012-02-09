package br.usp.ime.ccsl.choreos.storagefactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.usp.ime.ccsl.choreos.storagefactory.datatypes.StorageNode;

public class StorageNodeRegistryFacade {
	public Map<Long, StorageNode> instantiatedStorages;

	public StorageNodeRegistryFacade() {
		instantiatedStorages = new HashMap<Long, StorageNode>();
	}

	public void registerNode(StorageNode storageNode) {
		instantiatedStorages.put(storageNode.getStorageNodeSpecs()
				.getStorageId(), storageNode);
	}

	public void unregisterNode(Long storageNodeId) {
		instantiatedStorages.remove(storageNodeId);
	}

	public StorageNode getNode(Long nodeId) throws Exception {
		return instantiatedStorages.get(nodeId);
	}

	public Collection<StorageNode> getNodes()
			throws UnsupportedOperationException {
		return instantiatedStorages.values();
	}
}