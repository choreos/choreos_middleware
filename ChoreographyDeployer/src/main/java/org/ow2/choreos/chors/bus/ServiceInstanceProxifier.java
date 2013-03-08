package org.ow2.choreos.chors.bus;

import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

import esstar.petalslink.com.service.management._1_0.ManagementException;

public class ServiceInstanceProxifier {

	public String proxify(ServiceInstance serviceInstance, EasyESBNode esbNode) throws ManagementException {

		ServiceType type = serviceInstance.getMyParentServiceSpec().getType();
		if (type != ServiceType.SOAP) {
			throw new IllegalArgumentException("We can bind only SOAP services, not " + type);
		}
		
		String url = serviceInstance.getNativeUri();
		url = url.replaceAll("/$", "");
		String wsdl = url + "?wsdl";
		
		return esbNode.proxifyService(url, wsdl);
	}

}
