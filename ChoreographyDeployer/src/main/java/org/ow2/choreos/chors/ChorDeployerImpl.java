package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.Service;

public class ChorDeployerImpl implements ChoreographyDeployer {

	private Logger logger = Logger.getLogger(ChorDeployerImpl.class);
	private ChorRegistry reg = ChorRegistry.getInstance();
	
	@Override
	public String createChoreography(ChorSpec chor) {

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
	public Choreography enact(String chorId) throws EnactmentException, ChoreographyNotFoundException {

		Choreography chor = reg.get(chorId);
		if (chor == null) {
			throw new ChoreographyNotFoundException(chorId);
		}
		
		logger.info("Starting enactment; chorId= " + chorId);
		
		Deployer deployer = new Deployer();
		Map<String, Service> deployedMap = deployer.deployServices(chor.getChorSpec());
		
		ContextCaster caster = new ContextCaster();
		caster.cast(chor.getChorSpec(), deployedMap);
		
		logger.info("Enactment completed; chorId=" + chorId);
		
		List<Service> deployedList = new ArrayList<Service>(deployedMap.values());
		reg.addDeployedServices(chorId, deployedList);
		return chor;
	}

}
