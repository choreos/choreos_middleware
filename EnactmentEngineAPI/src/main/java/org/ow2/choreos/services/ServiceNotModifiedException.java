package org.ow2.choreos.services;

public class ServiceNotModifiedException extends ServiceDeployerException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8106574276166921975L;

    public ServiceNotModifiedException(String serviceName) {
	super(serviceName);
    }

    public ServiceNotModifiedException(String serviceName, String message) {
	super(serviceName, message);
    }

    @Override
    public String toString() {
	return "Could not modify service " + super.getServiceName();
    }

}
