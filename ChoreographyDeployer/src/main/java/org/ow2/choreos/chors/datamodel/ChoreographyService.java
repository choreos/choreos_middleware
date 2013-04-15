package org.ow2.choreos.chors.datamodel;

import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;

public class ChoreographyService {

	private ChoreographyServiceSpec choreographyServiceSpec;
	private Service service;

	public ChoreographyService(ChoreographyServiceSpec choreographyServiceSpec) {
		this.choreographyServiceSpec = choreographyServiceSpec;
		Service newService = null;
		if (choreographyServiceSpec.getServiceSpec().getPackageType() == PackageType.LEGACY) {
			newService = new LegacyService((LegacyServiceSpec) choreographyServiceSpec.getServiceSpec());
		} else {
			newService = new DeployedService((DeployedServiceSpec) choreographyServiceSpec.getServiceSpec());
		}
		service = newService;
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
}
