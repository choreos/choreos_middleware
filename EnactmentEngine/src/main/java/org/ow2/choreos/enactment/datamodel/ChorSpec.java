package org.ow2.choreos.enactment.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChorSpec {

	protected List<ChorService> serviceSpecs = new ArrayList<ChorService>();

	public ChorService getServiceSpecByName(String serviceName) {
		
		for (ChorService svc: serviceSpecs) {
			if (serviceName.equals(svc.getName()))
				return svc;
		}
		return null;
	}
	
	public void addServiceSpec(ChorService serviceSpec) {
		this.serviceSpecs.add(serviceSpec);
	}
	
	public List<ChorService> getServiceSpecs() {
		return serviceSpecs;
	}

	public void setServiceSpecs(List<ChorService> serviceSpec) {
		this.serviceSpecs = serviceSpec;
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
		return "ChorSpec [serviceSpecs=" + serviceSpecs + "]";
	}
	
}
