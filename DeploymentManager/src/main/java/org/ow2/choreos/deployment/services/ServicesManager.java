package org.ow2.choreos.deployment.services;

import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.diff.UnhandledModificationException;


public interface ServicesManager {

	/**
	 * 
	 * @param service specification
	 * @return information about how the service was deployed. 
	 * @throws ServiceNotDeployedException if deploy was not possible.
	 */
	public Service createService(ServiceSpec serviceSpec) throws ServiceNotDeployedException;

	/**
	 * 
	 * @param serviceName
	 * @return the service representation 
	 * @throws ServiceNotFoundException if ID does not exist
	 * @throws javax.management.ServiceNotFoundException 
	 */
	public Service getService(String serviceName) throws ServiceNotFoundException;
	
	/**
	 * 
	 * @param serviceName
	 * @throws ServiceNotDeletedException if it fails
	 */
	public void deleteService(String serviceName) 
			throws ServiceNotDeletedException, ServiceNotFoundException;

	/**
	 * 
	 * @param service specification
	 * @return information about how the service was deployed. 
	 * @throws UnhandledModificationException 
	 * @throws ServiceNotDeployedException if deploy was not possible.
	 */
	public Service updateService(String serviceId, ServiceSpec serviceSpec) throws ServiceNotModifiedException, UnhandledModificationException;
}
