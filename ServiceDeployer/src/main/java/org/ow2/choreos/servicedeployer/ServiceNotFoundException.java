package org.ow2.choreos.servicedeployer;

public class ServiceNotFoundException extends ServiceDeployerException {

	private static final long serialVersionUID = -4535824877031190147L;

	public ServiceNotFoundException(String serviceName) {
		super(serviceName);
	}

	public ServiceNotFoundException(String serviceName, String message) {
		super(serviceName, message);
	}

	@Override
	public String toString() {
		return "Could not find service " + super.getServiceName();
	}
	
}
