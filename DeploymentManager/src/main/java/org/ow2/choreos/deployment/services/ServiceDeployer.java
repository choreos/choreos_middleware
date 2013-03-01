package org.ow2.choreos.deployment.services;

import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;


public interface ServiceDeployer {

	/**
	 * 
	 * @param service specification
	 * @return information about how the service was deployed. 
	 * @throws ServiceNotDeployedException if deploy was not possible.
	 */
	public Service deploy(ServiceSpec serviceSpec) throws ServiceNotDeployedException;

	/**
	 * 
	 * @param serviceName
	 * @return the service representation 
	 * @throws ServiceNotFoundException if ID does not exist
	 */
	public Service getService(String serviceName) throws ServiceNotFoundException;
	
	/**
	 * 
	 * @param serviceName
	 * @throws ServiceNotDeletedException if it fails
	 */
	public void deleteService(String serviceName) throws ServiceNotDeletedException, ServiceNotFoundException;

	/**
	 * 
	 * @param serviceName
	 * @param amount of new service instances
	 * @throws ServiceNotFoundException if it fails
	 */
	public void addServiceInstances(String serviceId, int amount) throws ServiceNotFoundException, ServiceNotModifiedException;
}
