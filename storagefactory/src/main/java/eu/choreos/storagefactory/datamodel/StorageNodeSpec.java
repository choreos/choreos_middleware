package eu.choreos.storagefactory.datamodel;


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
	
}
