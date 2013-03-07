package org.ow2.choreos.deployment.services;

import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.SpecAndService;



public class ScheduledServiceModification {
	
	private SpecAndService specAndService;
	private ServiceSpec requestedSpec;

	public SpecAndService getSpecAndService() {
		return specAndService;
	}

	public void setSpecAndService(SpecAndService specAndService) {
		this.specAndService = specAndService;
	}

	public ServiceSpec getRequestedSpec() {
		return requestedSpec;
	}

	public void setRequestedSpec(ServiceSpec requestedSpec) {
		this.requestedSpec = requestedSpec;
	}

	public ScheduledServiceModification(ServiceSpec spec, SpecAndService specAndService) {
		this.requestedSpec = spec;
		this.specAndService = specAndService;
	}
	
}
