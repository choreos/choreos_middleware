package org.ow2.choreos.deployment.services.datamodel;

import java.util.UUID;

public abstract class ServiceSpec {
	protected ServiceType type; 
	protected PackageType packageType;
	private String uuid;
	
	protected ServiceSpec(ServiceType serviceType, PackageType packageType) {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public PackageType getPackageType() {
		return packageType;
	}

	public void setPackageType(PackageType packageType) {
		this.packageType = packageType;
	}
	
	public abstract int getNumberOfInstances();
	public abstract void setNumberOfInstances(int numberOfInstances);

	@Override
	public boolean equals(Object obj) { 
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		ServiceSpec other = (ServiceSpec) obj;

		
	if (type == null) {
		if (other.type != null)
			return false;
	} else if (!type.equals(other.type))
		return false;
	
	if (packageType == null) {
		if (other.packageType != null)
			return false;
	} else if (!packageType.equals(other.packageType))
		return false;

	return true;
	}
}