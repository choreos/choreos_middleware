package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.deployment.services.datamodel.Service;

@XmlRootElement
public class Choreography {

	private String id;
	private ChorSpec currentChorSpec = null;
	private ChorSpec requestedChorSpec = null;
	private List<Service> deployedServices = new ArrayList<Service>();
	
	public Service getDeployedServiceByName(String serviceName) {
		
		System.out.println(deployedServices);
		for (Service svc: deployedServices) {
			if (serviceName.equals(svc.getName()))
				return svc;
		}
		return null;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public ChorSpec getCurrentChorSpec() {
		return currentChorSpec;
	}
	
	public void setCurrentChorSpec(ChorSpec chorSpec) {
		this.currentChorSpec = chorSpec;
	}
	
	public ChorSpec getRequestedChorSpec() {
		return requestedChorSpec;
	}

	public void setRequestedChorSpec(ChorSpec requestedChorSpec) {
		this.requestedChorSpec = requestedChorSpec;
	}
	
	public List<Service> getDeployedServices() {
		return deployedServices;
	}
	
	public void setDeployedServices(List<Service> deployedServices) {
		this.deployedServices = deployedServices;
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
		return "Choreography [id=" + id + ", chorSpec=" + currentChorSpec
				+ ", deployedServices=" + deployedServices + "]";
	}	
}
