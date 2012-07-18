package org.ow2.choreos.enactment.context;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public class ContextCaster {

	private Logger logger = Logger.getLogger(ContextCaster.class);
	
	private ContextSender sender = new ContextSender();
	
	public void cast(Choreography chor, Map<String, Service> deployedServices) {
		
		logger.info("Passing context to deployed services");
		for (ChorService service: chor.getServices()) {
			String serviceUri = deployedServices.get(service.getName()).getUri();
			for (ServiceDependence dep: service.getDependences()) {
				String partnerUri = deployedServices.get(dep.getServiceName()).getUri();
				boolean ok = sender.sendContext(serviceUri, dep.getServiceRole(), partnerUri);
				if (ok) {
					logger.debug(service.getName() + " has received "
							+ dep.getServiceName() + " as "
							+ dep.getServiceRole());
				} else {
					logger.error("Could not set " + dep.getServiceName()
							+ " as " + dep.getServiceRole() + " to "
							+ service.getName());
				}
			}
		}
	}
}
