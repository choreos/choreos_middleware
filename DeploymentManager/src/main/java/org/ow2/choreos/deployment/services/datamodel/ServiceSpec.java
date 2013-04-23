package org.ow2.choreos.deployment.services.datamodel;

import java.util.UUID;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ DeployedServiceSpec.class, LegacyServiceSpec.class })
public abstract class ServiceSpec {
	protected ServiceType serviceType; 
	protected PackageType packageType;
	private String uuid;
	
	public ServiceSpec() {
		
	}
	
	protected ServiceSpec(ServiceType serviceType, PackageType packageType) {
		uuid = UUID.randomUUID().toString();
		this.serviceType = serviceType;
		this.packageType = packageType;
	}
	
	public String getUUID() {
		return uuid;
	}
	
	/**
	 * Use it carefully. Should be used only when updating a existing service
	 * @return
	 */
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	public ServiceType getType() {
		return serviceType;
	}

	public void setType(ServiceType type) {
		this.serviceType = type;
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

		
	if (serviceType == null) {
		if (other.serviceType != null)
			return false;
	} else if (!serviceType.equals(other.serviceType))
		return false;
	
	if (packageType == null) {
		if (other.packageType != null)
			return false;
	} else if (!packageType.equals(other.packageType))
		return false;

	return true;
	}
}