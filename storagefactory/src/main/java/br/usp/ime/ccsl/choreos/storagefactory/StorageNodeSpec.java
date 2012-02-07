package br.usp.ime.ccsl.choreos.storagefactory;

public class StorageNodeSpec {
	private String storageType;

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	private Long storageId;
}
