package org.ow2.choreos.servicedeployer;

import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;


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
}
