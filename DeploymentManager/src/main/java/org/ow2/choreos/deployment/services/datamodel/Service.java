package org.ow2.choreos.deployment.services.datamodel;

import java.util.List;

public abstract class Service {

	private ServiceSpec spec;

	public Service(ServiceSpec serviceSpec) {
		if(serviceSpec == null)
			throw new IllegalArgumentException();
		setSpec(serviceSpec);
	}
	
	public ServiceSpec getSpec() {
		return spec;
	}

	public void setSpec(ServiceSpec spec) {
		this.spec = spec;
	}

	public abstract List<String> getUris();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = spec.hashCode();
		result = prime * result + ((getUris() == null) ? 0 : getUris().hashCode());
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
		
		Service other = (Service) obj;
		
		if (spec == null) {
			if (other.spec != null)
				return false;
		} else if (!spec.equals(other.spec))
			return false;

		return true;
	}

	@Override
	public String toString() {
		String repr = "Service [uuid=" + spec.getUUID();
		repr += ", spec={"+spec+"}";
		repr += (getUris() != null) ? repr +=	", uri=" + getUris().toString() + "]" : "]";
		return repr;
	}
}
