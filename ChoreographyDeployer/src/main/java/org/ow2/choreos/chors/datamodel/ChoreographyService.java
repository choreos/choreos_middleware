package org.ow2.choreos.chors.datamodel;

import org.ow2.choreos.deployment.services.datamodel.Service;

import org.ow2.choreos.ee.api.ChoreographyServiceSpec;

public class ChoreographyService {

	private ChoreographyServiceSpec choreographyServiceSpec;
	private Service service;
	
	public ChoreographyService() {
	}
	
	public ChoreographyService(ChoreographyServiceSpec choreographyServiceSpec) {
		this.choreographyServiceSpec = choreographyServiceSpec;
	}

	public ChoreographyServiceSpec getChoreographyServiceSpec() {
		return choreographyServiceSpec;
	}

	public void setChoreographyServiceSpec(
			ChoreographyServiceSpec choreographyServiceSpec) {
		this.choreographyServiceSpec = choreographyServiceSpec;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return "ChoreographyService [choreographyServiceSpec="
				+ choreographyServiceSpec + ", service=" + service;
	}
}
