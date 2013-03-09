package org.ow2.choreos.chors.bus;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;

public class InstancesFilter {

	/**
	 * Filter services to be proxified
	 * @param services
	 * @return
	 */
	public List<ServiceInstance> filter(List<Service> services) {
		
		List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
		for (Service svc: services) {
			if (svc.getSpec().getPackageType() != PackageType.EASY_ESB) {
				instances.addAll(svc.getInstances());
			}
		}
		return instances;
	}

}
