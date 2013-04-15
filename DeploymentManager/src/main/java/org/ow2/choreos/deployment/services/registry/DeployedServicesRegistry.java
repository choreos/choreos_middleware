package org.ow2.choreos.deployment.services.registry;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ow2.choreos.deployment.services.datamodel.DeployedService;

public class DeployedServicesRegistry {

	private ConcurrentMap<String, DeployedService> availableServices = new ConcurrentHashMap<String, DeployedService>();

	public void addService(String serviceId, DeployedService service) {
		availableServices.put(serviceId, service);
	}
	
	public DeployedService getService(String serviceId) {
		return availableServices.get(serviceId);
	}

	public Collection<DeployedService> getServices() {
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