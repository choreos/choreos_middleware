package org.ow2.choreos.enactment.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;

/**
 * The specification of a service to be deployed within a choreography.
 * 
 * @author leonardo
 *
 */
public class ChorService extends ServiceSpec {

	private String name;
	private String group;
	private List<String> roles = new ArrayList<String>();
	private List<ServiceDependence> dependences = new ArrayList<ServiceDependence>();
	
	public ServiceSpec getServiceSpec() {

		ServiceSpec spec = new ServiceSpec();
		spec.setCodeUri(super.codeUri);
		spec.setEndpointName(super.endpointName);
		spec.setPort(super.port);
		spec.setResourceImpact(super.resourceImpact);
		spec.setType(super.type);
		return spec;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<ServiceDependence> getDependences() {
		return dependences;
	}
	public void setDependences(List<ServiceDependence> dependences) {
		this.dependences = dependences;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		ChorService other = (ChorService) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ChorService [name=" + name + ", group=" + group + ", roles="
				+ roles + ", dependences=" + dependences + ", type=" + type
				+ ", codeUri=" + codeUri + ", port=" + port + ", endpointName="
				+ endpointName + "]";
	}
}
