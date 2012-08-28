package org.ow2.choreos.enactment.context;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public class ContextCaster {

	private Logger logger = Logger.getLogger(ContextCaster.class);
	
	private ContextSender sender = new ContextSender();
	
	public void cast(ChorSpec chor, Map<String, Service> deployedServices) {
		
		logger.info("Passing context to deployed services");
		for (ChorService spec: chor.getServiceSpecs()) {
			
			Service deployed = deployedServices.get(spec.getName());
			if (deployed == null) {
				logger.error("Service " + spec.getName()
						+ " not deployed. Not going to pass context to it.");
			} else {
				
				castContext(deployedServices, spec, deployed);
			}
		}
	}

	private void castContext(Map<String, Service> deployedServices,
			ChorService spec, Service deployed) {
		
		String serviceUri = deployed.getUri();
		for (ServiceDependence dep: spec.getDependences()) {
			
			Service deployedPartner = deployedServices.get(dep.getServiceName());
			if (deployedPartner == null) {
				logger.error("Service "
						+ dep.getServiceName()
						+ " ("
						+ spec.getName()
						+ "dependency) not deployed. Not goint to pass this context to "
						+ spec.getName());
			} else {
				
				String partnerUri = deployedPartner.getUri();
				boolean ok = sender.sendContext(serviceUri, dep.getServiceRole(), partnerUri);
				if (ok) {
					logger.debug(spec.getName() + " has received "
							+ dep.getServiceName() + " as "
							+ dep.getServiceRole());
				} else {
					logger.error("Could not set " + dep.getServiceName()
							+ " as " + dep.getServiceRole() + " to "
							+ spec.getName());
				}
			}
		}
	}
}
