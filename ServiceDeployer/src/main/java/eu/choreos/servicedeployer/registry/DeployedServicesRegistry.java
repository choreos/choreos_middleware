package eu.choreos.servicedeployer.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

import eu.choreos.servicedeployer.datamodel.Service;

public class DeployedServicesRegistry {

	private HashMap<String, Service> availableServices = new HashMap<String, Service>();

	public Service getNode(String nodeUuid) {
		return availableServices.get(nodeUuid);
	}

	public Collection<Service> getNodes() {
		return availableServices.values();
	}

	public boolean deleteService(String serviceUuid) {
		if (availableServices.remove(serviceUuid) == null)
			return false;
		else
			return true;
	}

}