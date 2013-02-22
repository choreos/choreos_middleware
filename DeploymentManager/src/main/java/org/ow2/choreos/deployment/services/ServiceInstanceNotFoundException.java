package org.ow2.choreos.deployment.services;

public class ServiceInstanceNotFoundException extends ServiceDeployerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1175784736408532584L;

	
	public ServiceInstanceNotFoundException(String serviceName, String instanceId) {
		super(instanceId + " of " +serviceName);
	}

	public ServiceInstanceNotFoundException(String serviceName, String instanceId, String message) {
		super(instanceId + " of " +serviceName, message);
	}

	@Override
	public String toString() {
		return "Could not find service instance in " + super.getServiceName();
	}

}
