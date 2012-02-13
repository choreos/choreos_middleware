package eu.choreos.storagefactory.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "storageNodeSpec")
public class StorageNodeSpec {

	private String type; // MySQL etc
	private String correlationID;
	private String userID;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((correlationID == null) ? 0 : correlationID.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		StorageNodeSpec other = (StorageNodeSpec) obj;
		if (correlationID == null) {
			if (other.correlationID != null)
				return false;
		} else if (!correlationID.equals(other.correlationID))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "StorageNodeSpec [type=" + type + ", correlationID="
				+ correlationID + ", userID=" + userID + "]";
	} 
	
	
}
