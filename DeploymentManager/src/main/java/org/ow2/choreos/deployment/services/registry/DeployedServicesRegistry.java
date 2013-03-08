package org.ow2.choreos.deployment.services.registry;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ow2.choreos.deployment.services.datamodel.Service;

public class DeployedServicesRegistry {

	private ConcurrentMap<String, Service> availableServices = new ConcurrentHashMap<String, Service>();

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
	
	private DeployedServicesRegistry() {
		
	}
	
	private static DeployedServicesRegistry INSTANCE = null;

	public static DeployedServicesRegistry getInstance() {
		if(INSTANCE == null)
			INSTANCE = new DeployedServicesRegistry();
		return INSTANCE;
	}

}