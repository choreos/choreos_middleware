package org.ow2.choreos.enactment.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Choreography {

	private List<ChorService> services = new ArrayList<ChorService>();

	public List<ChorService> getServices() {
		return services;
	}

	public void setServices(List<ChorService> services) {
		this.services = services;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((services == null) ? 0 : services.hashCode());
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
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Choreography [services=" + services + "]";
	}
	
}
