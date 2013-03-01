package org.ow2.choreos.deployment.services.bus;

import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

import esstar.petalslink.com.service.management._1_0.ManagementException;

public class ServiceProxifier {

	public String proxify(ServiceInstance serviceInstance, EasyESBNode esbNode) throws ManagementException {

		ServiceType type = serviceInstance.getMyParentServiceSpec().getType();
		if (type != ServiceType.SOAP) {
			throw new IllegalArgumentException("We can bind only SOAP services, not " + type);
		}
		
		String url = serviceInstance.getNativeUri();
		String wsdl = url;
		if (wsdl.endsWith("/")) {
			wsdl = wsdl.replaceAll("/$", "");
		}
		wsdl += "?wsdl";
		
		return esbNode.proxifyService(url, wsdl);
	}

}
