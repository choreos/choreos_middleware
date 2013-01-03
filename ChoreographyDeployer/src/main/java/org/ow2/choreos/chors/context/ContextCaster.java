package org.ow2.choreos.chors.context;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.ServiceDependency;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public class ContextCaster {

	private Logger logger = Logger.getLogger(ContextCaster.class);
	private static final int MAX_TRIALS = 5;
	private static final int DELAY_BETWEEN_TRIALS = 30000;
	
	private ContextSender sender = new SoapContextSender();
	
	public void cast(ChorSpec chor, Map<String, Service> deployedServices) {
		
		logger.info("Passing context to deployed services");
		for (ChorServiceSpec spec: chor.getServiceSpecs()) {
			
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
			ChorServiceSpec spec, Service deployed) {
		
		String serviceUri = deployed.getUri();
		for (ServiceDependency dep: spec.getDependencies()) {
			
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
				int trial = 0;
				boolean ok = false;
				
				while (!ok && trial < MAX_TRIALS)
					try {
						sender.sendContext(serviceUri, dep.getServiceRole(), partnerUri);
						logger.debug(spec.getName() + " has received "
								+ dep.getServiceName() + " as "
								+ dep.getServiceRole());
						ok = true;
					} catch (ContextNotSentException e) {
						trial++;
						if (trial == MAX_TRIALS) {
							logger.error("Could not set " + dep.getServiceName()
									+ " as " + dep.getServiceRole() + " to "
									+ spec.getName());
						}
						try {
							Thread.sleep(DELAY_BETWEEN_TRIALS);
						} catch (InterruptedException e1) {
							logger.error("Exception at sleeping. This should not happen.");
						}
					}
			}
		}
	}
}
