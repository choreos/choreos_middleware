package org.ow2.choreos.deployment.services.registry;

import java.util.Collection;
import java.util.HashMap;

import org.ow2.choreos.deployment.services.datamodel.Service;

public class DeployedServicesRegistry {

	private HashMap<String, Service> availableServices = new HashMap<String, Service>();

	public void addService(String serviceId, Service service) {
		availableServices.put(serviceId, service);
	}
	
	public Service getService(String serviceId) {
		return availableServices.get(serviceId);
	}

	public Collection<Service> getServices() {
		return availableServices.values();
	}

	public void deleteService(String serviceId) {
		if (availableServices.remove(serviceId) == null)
			throw new IllegalArgumentException("Service " + serviceId + " not registered");
	}

}