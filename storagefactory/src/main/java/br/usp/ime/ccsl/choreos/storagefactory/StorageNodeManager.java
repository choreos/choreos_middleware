package br.usp.ime.ccsl.choreos.storagefactory;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jclouds.compute.RunNodesException;

import br.usp.ime.choreos.nodepoolmanager.Configuration;
import br.usp.ime.choreos.nodepoolmanager.Infrastructure;
import br.usp.ime.choreos.nodepoolmanager.Node;

public class StorageNodeManager {
	private Infrastructure infrastructure;
	private Map<Long, StorageNode> instantiatedStorages;
	
	public StorageNodeManager(Infrastructure infrastructure){
		this.infrastructure = infrastructure;
		this.instantiatedStorages = new HashMap();
	}
	
	public StorageNode createNode(StorageNodeSpec nodeSpec) throws Exception {
		Node infraNode = createInfrastructureNode();

		// Use NodePoolManager for creating a node
		// Wrap with a storage node

		StorageNode storageNode = new StorageNode();
		storageNode.setStorageNodeSpecs(nodeSpec);
		storageNode.setNode(infraNode);
		
		setupStorageNode(storageNode);

		registerNode(storageNode);
		
		System.out.println("Node created");
		return storageNode;
	}


	private void registerNode(StorageNode storageNode) {
		instantiatedStorages.put(storageNode.getStorageNodeSpecs().getStorageId(), storageNode);
	}

	private Node createInfrastructureNode() throws RunNodesException {
		Node infraNode;

		// interact with the node pool manager instance
		System.out.println("Creating storage node...");

		// set the node specs for the new storage node
		infraNode = createSampleNode();
		infraNode.setCpus(1);
		infraNode.setRam(1024);
		infraNode.setSo("linux");
		infraNode.setStorage(10000);
		
		// create a node according to features required
		return infrastructure.createNode(infraNode);
		//return infraNode;
	}

	
	private void setupStorageNode(StorageNode storageNode) {
		// Invoke Configuration Manager to add appropriate recipe to the infra node
		
		// 
		
	}

	public StorageNode getNode(Long nodeId) throws Exception {
		return instantiatedStorages.get(nodeId);
	}

	public Collection<StorageNode> getNodes() throws UnsupportedOperationException {
		return instantiatedStorages.values();
	}

	//TODO: Create and define the thrown exception
	
	public void destroyNode(Long storageNodeId) {
		StorageNode storageNode;
		
		try {
			storageNode = getNode(storageNodeId);
			
			String infrastructureNodeID = storageNode.getNode().getId();
			infrastructure.destroyNode(infrastructureNodeID);
			
			instantiatedStorages.remove(storageNodeId);
		
		} catch (Exception e) {
			System.out.println("[Storage Manager] Error: Could not find node with ID "+ storageNodeId.intValue());
			e.printStackTrace();
		}
	}

	private Node createSampleNode() throws RunNodesException {
		Node sampleNode = new Node();
		sampleNode.setImage("1");

		return infrastructure.createNode(sampleNode);
	}
}