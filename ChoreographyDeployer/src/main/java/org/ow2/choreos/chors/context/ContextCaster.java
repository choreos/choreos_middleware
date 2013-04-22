package org.ow2.choreos.chors.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceDependency;
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

		for (Map.Entry<String, ChoreographyService> entry : deployedServices
				.entrySet()) {
			ChoreographyService deployed = entry.getValue();
			castContext(deployedServices, deployed);
		}
	}

	private void castContext(Map<String, ChoreographyService> deployedServices,
			ChoreographyService deployed) {

		ChoreographyServiceSpec spec = deployed.getChoreographyServiceSpec();
		if (spec.getDependencies() == null)
			return;

		List<String> serviceUris = deployed.getService().getUris();

		for (ChoreographyServiceDependency dep : spec.getDependencies()) {

			ChoreographyService providerService = deployedServices.get(dep
					.getChoreographyServiceUID());
			
					
			if (providerService == null) {
				logger.error("Service "
						+ dep.getChoreographyServiceUID()
						+ " ("
						+ spec.getChoreographyServiceUID()
						+ "dependency) not deployed. Not goint to pass this context to "
						+ spec.getChoreographyServiceUID());
			} else {
				try {
					trySendContext(spec, serviceUris, dep, providerService);
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
	 * @param providerService
	 * @return
	 */
	private List<String> getUris(ChoreographyService providerService) {

		List<String> uris = new ArrayList<String>();
		for (ServiceInstance instance : ((DeployedService) providerService
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

	private void trySendContext(ChoreographyServiceSpec consumerServiceSpec,
			List<String> consumerServiceInstanceUris,
			ChoreographyServiceDependency consumerServiceDependency,
			ChoreographyService providerService) throws ContextNotSentException {

		List<String> providerUris = this.getUris(providerService);
		int trial = 0;

		for (String serviceUri : consumerServiceInstanceUris) {
			while (trial < MAX_TRIALS) {
				try {
					sender.sendContext(
							serviceUri,
							consumerServiceDependency.getChoreographyServiceRole(),
							consumerServiceDependency.getChoreographyServiceUID(),
							providerUris);
					logger.debug(consumerServiceSpec
							.getChoreographyServiceUID()
							+ " has received "
							+ consumerServiceDependency
									.getChoreographyServiceUID()
							+ " as "
							+ consumerServiceDependency
									.getChoreographyServiceRole()
							+ ": "
							+ providerUris);
					return;
				} catch (ContextNotSentException e) {
					trial = tryRecoveryNotSentContext(consumerServiceSpec,
							consumerServiceDependency, trial);
					logger.error("Trial=" + trial);
				}
			}
			throw new ContextNotSentException(serviceUri,
					consumerServiceDependency.getChoreographyServiceRole(),
					consumerServiceDependency.getChoreographyServiceUID(),
					providerUris);
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
