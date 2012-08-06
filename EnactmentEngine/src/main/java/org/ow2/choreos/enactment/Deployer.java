package org.ow2.choreos.enactment;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.Configuration.Option;
import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.client.NPMClient;
import org.ow2.choreos.servicedeployer.ServiceDeployer;
import org.ow2.choreos.servicedeployer.client.ServiceDeployerClient;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;

public class Deployer {

	private Logger logger = Logger.getLogger(Deployer.class);
	
	private NodePoolManager npm;
	private ServiceDeployer deployer;

	public Deployer() {
		
		npm = new NPMClient(Configuration.get(Option.NODE_POOL_MANAGER_URI));
		deployer = new ServiceDeployerClient(Configuration.get(Option.SERVICE_DEPLOYER_URI));
	}
	
	public Map<String, Service> deployServices(ChorSpec chor) {
	
		logger.info("Deploying services");
		Map<String, Service> deployedServices = new HashMap<String, Service>(); // key is serviceName
		
		for (ChorService service: chor.getServiceSpecs()) {
			ServiceSpec serviceSpec = service.getServiceSpec();
			logger.debug("Requesting deploy of " + serviceSpec);
			Service deployed = deployer.deploy(serviceSpec);
			if (deployed != null) {
				deployedServices.put(service.getName(), deployed);
			} else {
				logger.error(service.getName() + " deploy has failed");
			}
		}
		logger.info("Nodes are configured to receive services");
		
		boolean ok = npm.upgradeNodes();
		if (!ok) {
			logger.error("Bad response from /nodes/upgrade; maybe some service is not deployed");
		}
		logger.info("Deployement finished");

		return deployedServices;
	}
}
