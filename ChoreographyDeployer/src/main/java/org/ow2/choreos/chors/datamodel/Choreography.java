package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Choreography {

	private String id;
	private ChoreographySpec choreographySpec = null;

	@XmlTransient
	private List<ChoreographyService> choreographyServices = new ArrayList<ChoreographyService>();
	@XmlTransient
	private ChoreographySpec requestedChoreographySpec = null;

	public List<ChoreographyService> getChoregraphyServices() {
		return choreographyServices;
	}

	public ChoreographyService getDeployedChoreographyServiceByChoreographyServiceUID(
			String choreographyServiceUID) {

		List<ChoreographyService> deployedServices = getDeployedChoreographyServices();
		
		System.out.println("...................\n\n\n\n\n");
		System.out.println("Existing choreography services: " + deployedServices);
		System.out.println("\n\n\n\n\n...................");
		for (ChoreographyService svc : deployedServices) {
			System.out.println("Searching for " + choreographyServiceUID
					+ ", found " + svc.toString());
			if (choreographyServiceUID.equals(svc.getChoreographyServiceSpec()
					.getChoreographyServiceUID()))
				return svc;
		}
		throw new IllegalArgumentException("Service named "
				+ choreographyServiceUID + " does not exist");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ChoreographySpec getChoreographySpec() {
		return choreographySpec;
	}

	public ChoreographySpec getRequestedChoreographySpec() {
		return requestedChoreographySpec;
	}

	public void setChoreographySpec(ChoreographySpec spec) {
		if (spec == null) {
			this.choreographySpec = spec;
		}
		this.requestedChoreographySpec = spec;
	}

	public void finishChoreographyEnactment() {
		this.choreographySpec = this.requestedChoreographySpec;
	}

	public List<ChoreographyService> getDeployedChoreographyServices() {
		return choreographyServices;
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
		return "Choreography [id=" + id + ", chorSpec="
				+ requestedChoreographySpec + ", deployedServices="
				+ getDeployedChoreographyServices() + "]";
	}

	public void addChoreographyService(ChoreographyService choreographyService) {
		choreographyServices.add(choreographyService);
	}

	public void setDeployedChoreographyServices(
			List<ChoreographyService> choreographyServices) {
		this.choreographyServices.clear();
		for (ChoreographyService service : choreographyServices) {
			this.choreographyServices.add(service);
		}
	}
}
