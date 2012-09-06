package org.ow2.choreos.enactment.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.servicedeployer.datamodel.Service;

@XmlRootElement
public class Choreography {

	private String id;
	private ChorSpec chorSpec;
	private List<Service> deployedServices = new ArrayList<Service>();
	
	public Service getDeployedServiceByName(String serviceName) {
		
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
	public ChorSpec getChorSpec() {
		return chorSpec;
	}
	public void setChorSpec(ChorSpec chorSpec) {
		this.chorSpec = chorSpec;
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
		return "Choreography [id=" + id + ", chorSpec=" + chorSpec
				+ ", deployedServices=" + deployedServices + "]";
	}
	
}
