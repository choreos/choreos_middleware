package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;

@XmlRootElement
public class ChorSpec {

	protected List<ServiceSpec> serviceSpecs = new ArrayList<ServiceSpec>();

	public ServiceSpec getServiceSpecByName(String serviceName) {
		
		for (ServiceSpec svc: serviceSpecs) {
			if (serviceName.equals(svc.getName()))
				return svc;
		}
		return null;
	}
	
	public void addServiceSpec(ServiceSpec spec) {
		this.serviceSpecs.add(spec);
	}
	
	public List<ServiceSpec> getServiceSpecs() {
		return serviceSpecs;
	}

	public void setServiceSpecs(List<ServiceSpec> spec) {
		this.serviceSpecs = spec;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serviceSpecs == null) ? 0 : serviceSpecs.hashCode());
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
		ChorSpec other = (ChorSpec) obj;
		if (serviceSpecs == null) {
			if (other.serviceSpecs != null)
				return false;
		} else if (!serviceSpecs.equals(other.serviceSpecs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChorSpec [chorServiceSpecs=" + serviceSpecs + "]";
	}
	
}
