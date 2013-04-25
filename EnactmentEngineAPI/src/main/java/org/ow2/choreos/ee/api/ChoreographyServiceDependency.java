package org.ow2.choreos.ee.api;

/**
 * Represents the dependence of a service acting with a role
 * 
 * @author leonardo
 * 
 */
public class ChoreographyServiceDependency {

	private String choreographyServiceUID;
	private String choreographyServiceRole;

	public ChoreographyServiceDependency() {

	}

	public ChoreographyServiceDependency(String choreographyServiceUID,
			String choreographyServiceRole) {
		this.choreographyServiceUID = choreographyServiceUID;
		this.choreographyServiceRole = choreographyServiceRole;
	}

	public String getChoreographyServiceUID() {
		return choreographyServiceUID;
	}

	public void setChoreographyServiceUID(String choreographyServiceUID) {
		this.choreographyServiceUID = choreographyServiceUID;
	}

	public String getChoreographyServiceRole() {
		return choreographyServiceRole;
	}

	public void setChoreographyServiceRole(String choreographyServiceRole) {
		this.choreographyServiceRole = choreographyServiceRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((choreographyServiceUID == null) ? 0
						: choreographyServiceUID.hashCode());
		result = prime
				* result
				+ ((choreographyServiceRole == null) ? 0
						: choreographyServiceRole.hashCode());
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
		ChoreographyServiceDependency other = (ChoreographyServiceDependency) obj;
		if (choreographyServiceUID == null) {
			if (other.choreographyServiceUID != null)
				return false;
		} else if (!choreographyServiceUID.equals(other.choreographyServiceUID))
			return false;
		if (choreographyServiceRole == null) {
			if (other.choreographyServiceRole != null)
				return false;
		} else if (!choreographyServiceRole
				.equals(other.choreographyServiceRole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceDependency [serviceName=" + choreographyServiceUID
				+ ", serviceRole=" + choreographyServiceRole + "]";
	}

}
