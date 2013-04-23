package org.ow2.choreos.deployment.services;

import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.diff.UnhandledModificationException;


public interface ServicesManager {

	/**
	 * 
	 * @param service specification
	 * @return information about how the service was deployed. 
	 * @throws ServiceNotDeployedException if deploy was not possible.
	 */
	public DeployedService createService(DeployedServiceSpec serviceSpec) throws ServiceNotDeployedException;

	/**
	 * 
	 * @param uuid
	 * @return the service representation 
	 * @throws ServiceNotFoundException if ID does not exist
	 * @throws javax.management.ServiceNotFoundException 
	 */
	public DeployedService getService(String uuid) throws ServiceNotFoundException;
	
	/**
	 * 
	 * @param uuid
	 * @throws ServiceNotDeletedException if it fails
	 */
	public void deleteService(String uuid) 
			throws ServiceNotDeletedException, ServiceNotFoundException;

	/**
	 * 
	 * @param service specification
	 * @return information about how the service was deployed. 
	 * @throws UnhandledModificationException 
	 * @throws ServiceNotDeployedException if deploy was not possible.
	 */
	public DeployedService updateService(DeployedServiceSpec newServiceSpec) throws ServiceNotModifiedException, UnhandledModificationException;
}
