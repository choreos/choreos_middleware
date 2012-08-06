package org.ow2.choreos.enactment.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Choreography {

	private String id;
	private List<ChorService> services = new ArrayList<ChorService>();

	public ChorService getServiceByName(String serviceName) {
		
		for (ChorService svc: services) {
			if (serviceName.equals(svc.getName()))
				return svc;
		}
		return null;
	}
	
	public void addService(ChorService service) {
		this.services.add(service);
	}
	
	public List<ChorService> getServices() {
		return services;
	}

	public void setServices(List<ChorService> services) {
		this.services = services;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		return "Choreography [id=" + id + ", services=" + services + "]";
	}

}
