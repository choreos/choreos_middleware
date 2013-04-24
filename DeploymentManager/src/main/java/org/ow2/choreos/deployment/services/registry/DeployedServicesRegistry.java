package org.ow2.choreos.deployment.services.registry;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ow2.choreos.deployment.services.datamodel.DeployableService;

public class DeployedServicesRegistry {

	private ConcurrentMap<String, DeployableService> availableServices = new ConcurrentHashMap<String, DeployableService>();

	public void addService(String serviceId, DeployableService service) {
		availableServices.put(serviceId, service);
	}
	
	public DeployableService getService(String serviceId) {
		return availableServices.get(serviceId);
	}

	public Collection<DeployableService> getServices() {
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