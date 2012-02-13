package eu.choreos.storagefactory.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "storageNode")
public class StorageNode {

	private String id;
	private Database database;
	private StorageNodeSpec storageNodeSpecs;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Database getDatabase() {
		return database;
	}
	public void setDatabase(Database database) {
		this.database = database;
	}
	public StorageNodeSpec getStorageNodeSpecs() {
		return storageNodeSpecs;
	}
	public void setStorageNodeSpecs(StorageNodeSpec storageNodeSpecs) {
		this.storageNodeSpecs = storageNodeSpecs;
	}
}
