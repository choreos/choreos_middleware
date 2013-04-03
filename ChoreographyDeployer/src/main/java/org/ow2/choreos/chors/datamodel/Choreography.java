package org.ow2.choreos.chors.datamodel;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.ow2.choreos.deployment.services.datamodel.Service;

@XmlRootElement
public class Choreography {

	private String id;
	private ChorSpec spec = null;
	
	@XmlTransient
	private List<Service> services = new ArrayList<Service>();
	@XmlTransient
	private ChorSpec requestedSpec = null;
	
	public List<Service> getServices() {
		return services;
	}

	public Service getDeployedServiceByName(String serviceName) {

		List<Service> deployedServices = this.getDeployedServices();
		for (Service svc: deployedServices) {
			System.out.println("Searching for " + serviceName + ", found " + svc.toString());
			if (serviceName.equals(svc.getName()))
				return svc;
		}
		throw new IllegalArgumentException("Service named " + serviceName + " does not exist");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ChorSpec getSpec() {
		return spec;
	}

	public ChorSpec getRequestedSpec() {
		return requestedSpec;
	}

	public void setSpec(ChorSpec spec) {
		if(spec == null) {
			this.spec = spec;
		}
		this.requestedSpec = spec;		
	}
	
	public void choreographyEnacted() {
		this.spec = this.requestedSpec;
	}

	public List<Service> getDeployedServices() {
		return services;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Choreography other = (Choreography) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Choreography [id=" + id + ", chorSpec=" + requestedSpec
				+ ", deployedServices=" + getDeployedServices() + "]";
	}

	public void addService(Service service) {
		services.add(service);
	}

	public void setDeployedServices(List<Service> services) {
		this.services.clear();
		for(Service service : services) {
			this.services.add(service);
		}
		
	}
}
