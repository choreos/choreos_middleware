package org.ow2.choreos.chors.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceDependency;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

public class ContextCaster {

	private Logger logger = Logger.getLogger(ContextCaster.class);
	private static final int MAX_TRIALS = 5;
	private static final int DELAY_BETWEEN_TRIALS = 30000;

	private ContextSender sender;
	
	public ContextCaster(ContextSender sender) {
		this.sender = sender;
	}

	public void cast(ChorSpec chor, Map<String, Service> deployedServices) {

		logger.info("Passing context to deployed services");
		for (ServiceSpec spec: chor.getServiceSpecs()) {

			Service deployed = deployedServices.get(spec.getName());
			if (deployed == null) {
				logger.error("Service " + spec.getName()
						+ " not deployed. Not going to pass context to it.");
			} else {

				castContext(deployedServices, spec, deployed);
			}
		}
	}

	private void castContext(Map<String, Service> deployedServices, ServiceSpec spec, Service deployed) {

		List<String> serviceUris = deployed.getUris();
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
				try {
					trySendContext(spec, serviceUris, dep, deployedPartner);
				} catch (ContextNotSentException e) {
					logger.error("Could not setInvocationAddress to " + e.getServiceUri());
				}
			}
		}
	}

	/**
	 * Get URIs from service that will be used in the setInvocationAddress.
	 * 
	 * Implementation: if possible, uses the SOAP bus URI; if not use the nativeUri
	 * 
	 * @param deployed
	 * @return
	 */
	private List<String> getUris(Service deployed) {
		
		List<String> uris = new ArrayList<String>();
		for (ServiceInstance instance: deployed.getInstances()) {
			String proxifiedUri = instance.getBusUri(ServiceType.SOAP);
			if (proxifiedUri != null) {
				uris.add(proxifiedUri);
			} else {
				uris.add(instance.getNativeUri());
			}
		}
		return uris;
	}

	private void trySendContext(ServiceSpec spec, List<String> serviceUris,
			ServiceDependency dep, Service deployedPartner) throws ContextNotSentException {
		List<String> partnerUris = this.getUris(deployedPartner);
		int trial = 0;
		boolean ok = false;

		for(String serviceUri: serviceUris) {
			while (!ok && trial < MAX_TRIALS) {
				try {
					sender.sendContext(serviceUri, dep.getServiceRole(), dep.getServiceName(), partnerUris);
					logger.debug(spec.getName() + " has received "
							+ dep.getServiceName() + " as "
							+ dep.getServiceRole());
					ok = true;
				} catch (ContextNotSentException e) {
					trial = tryRecoveryNotSentContext(spec, dep, trial);
					logger.error("Trial=" + trial + "\n >>>> ");
				}
			}
			throw new ContextNotSentException(serviceUri, dep.getServiceRole(), dep.getServiceName(), partnerUris);
		}
	}

	private int tryRecoveryNotSentContext(ServiceSpec spec,
			ServiceDependency dep, int trial) {
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
		return trial;
	}
}
