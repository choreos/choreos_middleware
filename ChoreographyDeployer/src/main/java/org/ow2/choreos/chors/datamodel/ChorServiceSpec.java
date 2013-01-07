package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;

/**
 * The specification of a service to be deployed within a choreography.
 * 
 * @author leonardo
 *
 */
public class ChorServiceSpec extends ServiceSpec {

	private String owner;
	private String group;
	private List<String> roles = new ArrayList<String>();
	private List<ServiceDependency> dependencies = new ArrayList<ServiceDependency>();
	
	public ServiceSpec getServiceSpec() {

		// this method seems to be stupid, but it is necessary
		// since CXF WebClient will not handle an 
		// ChorServiceSpec instance like a ServiceSpec instance
		
		ServiceSpec spec = new ServiceSpec();
		spec.setName(super.name);
		spec.setCodeUri(super.codeUri);
		spec.setEndpointName(super.endpointName);
		spec.setPort(super.port);
		spec.setResourceImpact(super.resourceImpact);
		spec.setType(super.type);
		spec.setArtifactType(super.artifactType);
		spec.setVersion(super.getVersion());
		return spec;
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<ServiceDependency> getDependencies() {
		return dependencies;
	}
	public void setDependencies(List<ServiceDependency> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChorServiceSpec other = (ChorServiceSpec) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChorServiceSpec [owner=" + owner + ", group=" + group
				+ ", roles=" + roles + ", dependencies=" + dependencies
				+ ", name=" + name + ", type=" + type + ", artifactType="
				+ artifactType + ", codeUri=" + codeUri + ", port=" + port
				+ ", endpointName=" + endpointName + ", version=" + version
				+ "]";
	}
	
}
