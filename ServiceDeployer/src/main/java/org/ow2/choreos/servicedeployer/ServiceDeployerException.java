package org.ow2.choreos.servicedeployer;

public class ServiceDeployerException extends Exception {

	private static final long serialVersionUID = -8759048371767307766L;
	private final String serviceName;
	
	public ServiceDeployerException(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public ServiceDeployerException(String serviceName, String message) {
		super(message);
		this.serviceName = serviceName;
	}
	
	public String getServiceName() {
		return this.serviceName;
	}
}
