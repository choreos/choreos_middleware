package org.ow2.choreos.chors.context;

import org.ow2.choreos.ee.api.ServiceType;


public class ContextSenderFactory {

	public static ContextSender getInstance(ServiceType serviceType) {
		
		switch (serviceType) {

		case SOAP:
			return new SoapContextSender();

		default:
			throw new IllegalArgumentException(serviceType + " not supported");
		}
	}
	
}
