package org.ow2.choreos.chors.datamodel;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.ow2.choreos.deployment.services.ScheduledServiceModification;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.SpecAndService;

@XmlRootElement
public class Choreography {

	private String id;
	private ChorSpec spec = null;

	@XmlTransient
	private ChoreographyRunningStatus runningStatus = new ChoreographyRunningStatus();

	
	
	
	public List<SpecAndService> getSpecsAndServices() {
		return runningStatus.specsAndServices;
	}

	public Service getDeployedServiceByName(String serviceName) {

		List<Service> deployedServices = this.getDeployedServices();
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

	public ChorSpec getSpec() {
		return spec;
	}

	public ChorSpec getRequestedSpec() {
		return runningStatus.requestedSpec;
	}

	public void setSpec(ChorSpec spec) {
		if(spec == null) {
			this.spec = spec;
		}
		this.runningStatus.requestedSpec = spec;		
	}
	
	public void choreographyEnacted() {
		this.spec = this.runningStatus.requestedSpec;
	    runningStatus.cleanUpScheduledChanges(); // this should be called after all enactment (deploy or update)
	}

	public List<Service> getDeployedServices() {
		List<Service> res = new ArrayList<Service>();
		for(SpecAndService b : runningStatus.specsAndServices) {
			res.add(b.getService());
		}
		return res;
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
		return "Choreography [id=" + id + ", chorSpec=" + runningStatus.requestedSpec
				+ ", deployedServices=" + getDeployedServices() + "]";
	}

	public void addSpecAndService(SpecAndService specAndService) {
		this.runningStatus.specsAndServices.add(specAndService);
	}

	public void addScheduledServiceCreation(ServiceSpec spec) {
		runningStatus.addScheduledServiceChange("create", new ScheduledServiceModification(spec, null));
	}

	public void addScheduledServiceRemoval(SpecAndService specAndService) {
		runningStatus.addScheduledServiceChange("remove", new ScheduledServiceModification(null, specAndService));
	}

	public void addScheduledServiceUpdate(ServiceSpec spec, SpecAndService specAndService) {
		runningStatus.addScheduledServiceChange("update", new ScheduledServiceModification(spec, specAndService));
	}

	public void addScheduledServiceNoChange(SpecAndService specAndService) {
		runningStatus.addScheduledServiceChange("nochange", new ScheduledServiceModification(specAndService.getSpec(), specAndService));
	}

	public void cleanUp() {
		// TODO: remove unnecessary services
	}
	
	public List<ScheduledServiceModification> getScheduledServiceCreation() {
		return this.runningStatus.scheduledChanges.get("create");
	}

	public List<ScheduledServiceModification> getScheduledServiceUpdate() {
		return this.runningStatus.scheduledChanges.get("update");
	}

}
