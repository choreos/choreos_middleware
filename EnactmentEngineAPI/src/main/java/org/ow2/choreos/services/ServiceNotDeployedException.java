package org.ow2.choreos.services;

public class ServiceNotDeployedException extends ServiceDeployerException {

    private static final long serialVersionUID = -4535824877031190147L;

    public ServiceNotDeployedException(String serviceName) {
	super(serviceName);
    }

    public ServiceNotDeployedException(String serviceName, String message) {
	super(serviceName, message);
    }

    @Override
    public String toString() {
	return "Could not deploy service " + super.getServiceName();
    }

}
