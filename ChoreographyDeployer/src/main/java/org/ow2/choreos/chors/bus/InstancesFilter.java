package org.ow2.choreos.chors.bus;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.deployment.services.datamodel.DeployableService;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.ee.api.PackageType;

public class InstancesFilter {

	/**
	 * Filter services to be proxified
	 * 
	 * @param list
	 * @return
	 */
	public List<ServiceInstance> filter(List<ChoreographyService> list) {
		
		List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
		for (ChoreographyService svc: list) {
			if (svc.getChoreographyServiceSpec().getServiceSpec().getPackageType() != PackageType.EASY_ESB) {
				instances.addAll(((DeployableService)svc.getService()).getInstances());
			}
		}
		return instances;
	}
}
