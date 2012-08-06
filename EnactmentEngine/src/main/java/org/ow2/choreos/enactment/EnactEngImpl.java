package org.ow2.choreos.enactment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.context.ContextCaster;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public class EnactEngImpl implements EnactmentEngine {

	private Logger logger = Logger.getLogger(EnactEngImpl.class);
	private ChorRegistry reg = ChorRegistry.getInstance();
	
	@Override
	public String createChoreography(Choreography chor) {

		String chorId = reg.add(chor);
		return chorId;
	}

	@Override
	public Choreography getChorSpec(String chorId) {

		Choreography chor = reg.get(chorId);
		if (chor == null) {
			return null;
		}
		return chor;
	}

	@Override
	public List<Service> enact(String chorId) throws EnactmentException {

		Choreography chor = reg.get(chorId);
		if (chor == null) {
			return null;
		}
		
		logger.info("Starting enactment; chorId= " + chorId);
		
		Deployer deployer = new Deployer();
		Map<String, Service> deployedMap = deployer.deployServices(chor);
		
		logger.info("Deployment completed; chorId=" + chorId);
		
		ContextCaster caster = new ContextCaster();
		caster.cast(chor, deployedMap);
		
		logger.info("Enactment completed; chorId=" + chorId);
		
		List<Service> deployedList = new ArrayList<Service>(deployedMap.values());
		reg.addDeployedServices(chorId, deployedList);
		return deployedList;
	}

	@Override
	public List<Service> getEnactedServices(String chorId) {

		List<Service> deployedServices = reg.getDeployedServices(chorId); 
		return deployedServices;
	}

}
