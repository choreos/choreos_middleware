package eu.choreos.storagefactory.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "storageNode")
public class StorageNode {

	private String id;
	private Database database;
	private StorageNodeSpec storageNodeSpec;
	
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
	public StorageNodeSpec getStorageNodeSpec() {
		return storageNodeSpec;
	}
	public void setStorageNodeSpec(StorageNodeSpec storageNodeSpecs) {
		this.storageNodeSpec = storageNodeSpecs;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((database == null) ? 0 : database.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((storageNodeSpec == null) ? 0 : storageNodeSpec.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageNode other = (StorageNode) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (storageNodeSpec == null) {
			if (other.storageNodeSpec != null)
				return false;
		} else if (!storageNodeSpec.equals(other.storageNodeSpec))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "StorageNode [id=" + id + ", database=" + database
				+ ", storageNodeSpecs=" + storageNodeSpec + "]";
	}
	
	
}
