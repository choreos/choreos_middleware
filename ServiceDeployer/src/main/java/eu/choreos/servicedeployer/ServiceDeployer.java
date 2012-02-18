package eu.choreos.servicedeployer;

import java.net.MalformedURLException;
import java.net.URL;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.registry.DeployedServicesRegistry;

public class ServiceDeployer {

	private DeployedServicesRegistry registry;

	public URL deploy(Service service) throws MalformedURLException {
		// Create recipe from template
		// Upload template to Chef
		// Request NodePoolManager to deploy the recipe in a node
		return new URL(service.getUri());
	}

	public Service getService(String serviceID) {
		return registry.getNode(serviceID);
	}

	// What is this??
	public Service updateService(Service service) {
		return null;
	}

	public void deleteService(String serviceID) {
		registry.deleteService(serviceID);
	}

}
