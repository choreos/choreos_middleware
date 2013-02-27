package org.ow2.choreos.chors;

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
		Map<String, Service> deployedMap = deployer.deployServices(chor);
		
		ContextCaster caster = new ContextCaster();
		caster.cast(chor.getRequestedSpec(), deployedMap);
		
		logger.info("Enactment completed; chorId=" + chorId);

		return chor;
	}

	@Override
	public void update(String chorId, ChorSpec spec) throws ChoreographyNotFoundException {

		Choreography chor = reg.get(chorId);
		if(chor == null) {
			throw new ChoreographyNotFoundException(chorId);
		}
		
		logger.info("Starting update on choreography with ID " + chorId);
		
		chor.setSpec(spec);
		
		logger.info("Updated choreography with ID " + chorId);
		
		return;
	}

}
