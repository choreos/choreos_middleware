package org.ow2.choreos.chors.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceDependency;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

public class ContextCaster {

	private Logger logger = Logger.getLogger(ContextCaster.class);
	private static final int MAX_TRIALS = 5;
	private static final int DELAY_BETWEEN_TRIALS = 30000;

	private ContextSender sender;

	public ContextCaster(ContextSender sender) {
		this.sender = sender;
	}

	public void cast(ChoreographySpec chor,
			Map<String, ChoreographyService> deployedServices) {

		logger.info("Passing context to deployed services");
		for (ChoreographyServiceSpec spec : chor.getChoreographyServiceSpecs()) {

			ChoreographyService deployed = deployedServices.get(spec
					.getChoreographyServiceUID());

			if (deployed == null) {
				logger.error("Service " + spec.getChoreographyServiceUID()
						+ " not deployed. Not going to pass context to it.");
			} else {
				castContext(deployedServices, spec, deployed);
			}
		}
	}

	private void castContext(Map<String, ChoreographyService> deployedServices,
			ChoreographyServiceSpec spec, ChoreographyService deployed) {

		List<String> serviceUris = deployed.getService().getUris();
		for (ChoreographyServiceDependency dep : spec.getDependencies()) {

			ChoreographyService deployedPartner = deployedServices.get(dep
					.getChoreographyServiceUID());

			if (deployedPartner == null) {
				logger.error("Service "
						+ dep.getChoreographyServiceUID()
						+ " ("
						+ spec.getChoreographyServiceUID()
						+ "dependency) not deployed. Not goint to pass this context to "
						+ spec.getChoreographyServiceUID());
			} else {
				try {
					trySendContext(spec, serviceUris, dep, deployedPartner);
				} catch (ContextNotSentException e) {
					logger.error("Could not setInvocationAddress to "
							+ e.getServiceUri());
				}
			}
		}
	}

	/**
	 * Get URIs from service that will be used in the setInvocationAddress.
	 * 
	 * Implementation: if possible, uses the SOAP bus URI; if not use the
	 * nativeUri
	 * 
	 * @param deployedPartner
	 * @return
	 */
	private List<String> getUris(ChoreographyService deployedPartner) {

		List<String> uris = new ArrayList<String>();
		for (ServiceInstance instance : ((DeployedService) deployedPartner
				.getService()).getInstances()) {
			String proxifiedUri = instance.getBusUri(ServiceType.SOAP);
			if (proxifiedUri != null) {
				uris.add(proxifiedUri);
			} else {
				uris.add(instance.getNativeUri());
			}
		}
		return uris;
	}

	private void trySendContext(ChoreographyServiceSpec spec,
			List<String> serviceUris, ChoreographyServiceDependency dep,
			ChoreographyService deployedPartner) throws ContextNotSentException {

		List<String> partnerUris = this.getUris(deployedPartner);
		int trial = 0;

		for (String serviceUri : serviceUris) {
			while (trial < MAX_TRIALS) {
				try {
					sender.sendContext(serviceUri,
							dep.getChoreographyServiceRole(),
							dep.getChoreographyServiceUID(), partnerUris);
					logger.debug(spec.getChoreographyServiceUID()
							+ " has received "
							+ dep.getChoreographyServiceUID() + " as "
							+ dep.getChoreographyServiceRole() + ": "
							+ partnerUris);
					return;
				} catch (ContextNotSentException e) {
					trial = tryRecoveryNotSentContext(spec, dep, trial);
					logger.error("Trial=" + trial);
				}
			}
			throw new ContextNotSentException(serviceUri,
					dep.getChoreographyServiceRole(),
					dep.getChoreographyServiceUID(), partnerUris);
		}
	}

	private int tryRecoveryNotSentContext(ChoreographyServiceSpec spec,
			ChoreographyServiceDependency dep, int trial) {
		trial++;
		if (trial == MAX_TRIALS) {
			logger.error("Could not set " + dep.getChoreographyServiceUID()
					+ " as " + dep.getChoreographyServiceRole() + " to "
					+ spec.getChoreographyServiceUID());
		}
		try {
			Thread.sleep(DELAY_BETWEEN_TRIALS);
		} catch (InterruptedException e1) {
			logger.error("Exception at sleeping. This should not happen.");
		}
		return trial;
	}
}
