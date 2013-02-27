package org.ow2.choreos.chors.datamodel;

import org.ow2.choreos.deployment.services.datamodel.Service;

public class SpecAndService {
	
	private ChorServiceSpec spec;
	private Service service;
	
	public SpecAndService(ChorServiceSpec spec, Service serv) {
		this.spec = spec;
		this.service = serv;
	}
	public ChorServiceSpec getSpec() {
		return spec;
	}
	public void setSpec(ChorServiceSpec spec) {
		this.spec = spec;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
}
