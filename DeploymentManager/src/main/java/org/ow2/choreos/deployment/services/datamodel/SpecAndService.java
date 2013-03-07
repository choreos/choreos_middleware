package org.ow2.choreos.deployment.services.datamodel;


import org.ow2.choreos.deployment.services.datamodel.Service;

public class SpecAndService {
	
	private ServiceSpec spec;
	private Service service;
	
	public SpecAndService(ServiceSpec spec, Service serv) {
		this.spec = spec;
		this.service = serv;
	}
	public ServiceSpec getSpec() {
		return spec;
	}
	public void setSpec(ServiceSpec spec) {
		this.spec = spec;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
}
