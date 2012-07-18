package org.ow2.choreos.enactment.datamodel;

/**
 * Represents the dependence of a service acting with a role 
 * 
 * @author leonardo
 *
 */
public class ServiceDependence {
	
	private String serviceName;
	private String serviceRole;

	public ServiceDependence() {

	}

	public ServiceDependence(String serviceName, String serviceRole) {
		this.serviceName = serviceName;
		this.serviceRole = serviceRole;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceRole() {
		return serviceRole;
	}

	public void setServiceRole(String serviceRole) {
		this.serviceRole = serviceRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serviceName == null) ? 0 : serviceName.hashCode());
		result = prime * result
				+ ((serviceRole == null) ? 0 : serviceRole.hashCode());
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
		ServiceDependence other = (ServiceDependence) obj;
		if (serviceName == null) {
			if (other.serviceName != null)
				return false;
		} else if (!serviceName.equals(other.serviceName))
			return false;
		if (serviceRole == null) {
			if (other.serviceRole != null)
				return false;
		} else if (!serviceRole.equals(other.serviceRole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceDependence [serviceName=" + serviceName
				+ ", serviceRole=" + serviceRole + "]";
	}

}
