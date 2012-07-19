package org.ow2.choreos.enactment;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.context.ContextCaster;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public class EnactEngImpl implements EnactmentEngine {

	private Logger logger = Logger.getLogger(EnactEngImpl.class);
	
	@Override
	public Map<String, Service> enact(Choreography chor) {

		logger.info("Starting enactment");
		
		Deployer deployer = new Deployer();
		Map<String, Service> deployed = deployer.deployServices(chor);
		
		ContextCaster caster = new ContextCaster();
		caster.cast(chor, deployed);
		
		logger.info("Enactment completed");
		
		return deployed;
	}

}
