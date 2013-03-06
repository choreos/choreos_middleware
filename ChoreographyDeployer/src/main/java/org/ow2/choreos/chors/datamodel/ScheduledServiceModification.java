package org.ow2.choreos.chors.datamodel;


public class ScheduledServiceModification {
	
	private SpecAndService specAndService;
	private ChorServiceSpec requestedSpec;

	public SpecAndService getSpecAndService() {
		return specAndService;
	}

	public void setSpecAndService(SpecAndService specAndService) {
		this.specAndService = specAndService;
	}

	public ChorServiceSpec getRequestedSpec() {
		return requestedSpec;
	}

	public void setRequestedSpec(ChorServiceSpec requestedSpec) {
		this.requestedSpec = requestedSpec;
	}

	public ScheduledServiceModification(ChorServiceSpec spec, SpecAndService specAndService) {
		this.requestedSpec = spec;
		this.specAndService = specAndService;
	}
	
}
