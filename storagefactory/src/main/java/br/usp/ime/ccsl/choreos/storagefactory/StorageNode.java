package br.usp.ime.ccsl.choreos.storagefactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.usp.ime.choreos.nodepoolmanager.Node;

@XmlRootElement(name = "storagenode")
public class StorageNode {//extends Node{
	
	private StorageNodeSpec storageNodeSpecs;
	private Node node;

	public StorageNodeSpec getStorageNodeSpecs() {
		return storageNodeSpecs;
	}

	public void setStorageNodeSpecs(StorageNodeSpec storageNodeSpecs) {
		this.storageNodeSpecs = storageNodeSpecs;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
}
