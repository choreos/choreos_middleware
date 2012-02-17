package eu.choreos.servicedeployer;

import java.net.MalformedURLException;
import java.net.URL;

import eu.choreos.servicedeployer.datamodel.Service;

public class ServiceDeployer {

	public ServiceDeployer() {

	}

	public URL deploy(Service service) throws MalformedURLException {
		// Create recipe from template
		// Upload template to Chef
		// Request NodePoolManager to deploy the recipe in a node
		return new URL(service.getUri());
	}

	public Service getService(String serviceID) {
		return null;
		// TODO Auto-generated method stub

	}

	public Service updateService(Service service) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteService(String serviceID) {
		// TODO Auto-generated method stub

	}

}
