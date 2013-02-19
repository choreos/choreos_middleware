package org.ow2.choreos.deployment.services.bus;

import java.io.IOException;

public interface EasyESBNode {

	public String getAdminEndpoint();
	
	public void bindService(String serviceUrl, String serviceWsdl) throws IOException;
	
	public void exposeService(String serviceNamespace, String serviceName, String endpointName) throws IOException;
}
