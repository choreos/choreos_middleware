package org.ow2.choreos.servicedeployer;

public class ServiceNotDeletedException extends ServiceDeployerException {

	private static final long serialVersionUID = -4535824877031190147L;

	public ServiceNotDeletedException(String serviceName) {
		super(serviceName);
	}

	public ServiceNotDeletedException(String serviceName, String message) {
		super(serviceName, message);
	}

	@Override
	public String toString() {
		return "Could not delete service " + super.getServiceName();
	}
	
}
