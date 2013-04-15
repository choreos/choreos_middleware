package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.chors.bus.ESBNodesSelector;
import org.ow2.choreos.chors.bus.ESBNodesSelectorFactory;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.chors.bus.ServiceInstancesProxifier;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.context.ContextSender;
import org.ow2.choreos.chors.context.ContextSenderFactory;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

public class ChoreographyDeployerImpl implements ChoreographyDeployer {

	private Logger logger = Logger.getLogger(ChoreographyDeployerImpl.class);
	private ChorRegistry reg = ChorRegistry.getInstance();
	
	@Override
	public String createChoreography(ChoreographySpec chor) {

		String chorId = reg.create(chor);
		logger.info("Choreography " + chorId + " created.");
		
		return chorId;
	}

	@Override
	public Choreography getChoreography(String chorId) {

		Choreography chor = reg.get(chorId);
		return chor;
	}

	@Override
	public Choreography enactChoreography(String chorId) throws EnactmentException, ChoreographyNotFoundException {

		Choreography chor = reg.get(chorId);
		if (chor == null) {
			throw new ChoreographyNotFoundException(chorId);
		}
		
		logger.info("Starting enactment; chorId= " + chorId);
		
		Deployer deployer = new Deployer();
		Map<String, ChoreographyService> deployedMap = deployer.deployChoreographyServices(chor);
		chor.setDeployedChoreographyServices(
				new ArrayList<ChoreographyService>(deployedMap.values()));
		
		logger.info("Deployed services="+ deployedMap);
		
		boolean useTheBus = Boolean.parseBoolean(Configuration.get(Option.BUS));
		if (useTheBus) {
			this.proxifyServices(chor);
		}
		
		ContextSender sender = ContextSenderFactory.getInstance(ServiceType.SOAP);
		ContextCaster caster = new ContextCaster(sender);
		caster.cast(chor.getRequestedChoreographySpec(), deployedMap);
		
		chor.finishChoreographyEnactment();
		
		logger.info("Enactment completed; chorId=" + chorId);

		return chor;
	}

	private void proxifyServices(Choreography choreography) {

		ESBNodesSelector selector = null;
		try {
			String esbSelectorType = Configuration.get(Option.BUS_POLICY);
			selector = ESBNodesSelectorFactory.getInstance(esbSelectorType);
		} catch(IllegalArgumentException e) {
			logger.error("Not going to proxify services because invalid ESBNodesSelector type.");
			return;
		}

		Map<ServiceInstance, EasyESBNode> instancesNodesMap = selector.selectESBNodes(choreography);
		ServiceInstancesProxifier proxifier = new ServiceInstancesProxifier();
		proxifier.proxify(instancesNodesMap);
		// TODO:  should PUT /services/ (a registry would resolve...)
	}

	@Override
	public void updateChoreography(String chorId, ChoreographySpec spec) throws ChoreographyNotFoundException {

		Choreography chor = reg.get(chorId);
		if(chor == null) {
			throw new ChoreographyNotFoundException(chorId);
		}
		
		logger.info("Starting update on choreography with ID " + chorId);
		
		chor.setChoreographySpec(spec);
		
		logger.info("Updated choreography with ID " + chorId);
		
		return;
	}

}
