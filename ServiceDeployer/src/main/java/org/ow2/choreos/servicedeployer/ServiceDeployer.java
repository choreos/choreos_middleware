package org.ow2.choreos.servicedeployer;

import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;


public interface ServiceDeployer {

	/**
	 * 
	 * @param service specification
	 * @return information about how the service was deployed. 
	 * <code>null</code> if deploy was not possible.
	 */
	public Service deploy(ServiceSpec serviceSpec);

	/**
	 * 
	 * @param serviceId
	 * @return the service representation or <code>null</code> if ID does not exist.
	 */
	public Service getService(String serviceId);
	
	/**
	 * 
	 * @param serviceId
	 * @return <code>true</code> for success or <code>false</code> to failure
	 */
	public boolean deleteService(String serviceId);
}
