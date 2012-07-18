package org.ow2.choreos.enactment;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.Configuration.Option;
import org.ow2.choreos.enactment.context.ContextSender;
import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.client.NPMClient;
import org.ow2.choreos.servicedeployer.ServiceDeployer;
import org.ow2.choreos.servicedeployer.client.ServiceDeployerClient;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;

public class EnactEngImpl implements EnactmentEngine {

	private Logger logger = Logger.getLogger(EnactEngImpl.class);
	
	private NodePoolManager npm;
	private ServiceDeployer deployer;
	
	private Map<String, Service> deployedServices; // key is serviceName
	
	public EnactEngImpl() {
		
		npm = new NPMClient(Configuration.get(Option.NODE_POOL_MANAGER_URI));
		deployer = new ServiceDeployerClient(Configuration.get(Option.SERVICE_DEPLOYER_URI));
	}
	
	@Override
	public void enact(Choreography chor) {

		deployServices(chor);
		castContext(chor);
		logger.info("Enactment completed");
	}

	private void deployServices(Choreography chor) {
		
		logger.info("Deploying services");
		for (ChorService service: chor.getServices()) {
			ServiceSpec serviceSpec = service.getServiceSpec();
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
	}

	private void castContext(Choreography chor) {
		
		logger.info("Passing context to deployed services");
		ContextSender sender = new ContextSender();
		for (ChorService service: chor.getServices()) {
			String serviceUri = deployedServices.get(service.getName()).getUri();
			for (ServiceDependence dep: service.getDependences()) {
				String partnerUri = deployedServices.get(dep.getServiceName()).getUri();
				boolean ok = sender.sendContext(serviceUri, dep.getServiceRole(), partnerUri);
				if (ok) {
					logger.debug(service.getName() + " has received "
							+ dep.getServiceName() + " as "
							+ dep.getServiceRole());
				} else {
					logger.error("Could not set " + dep.getServiceName()
							+ " as " + dep.getServiceRole() + " to "
							+ service.getName());
				}
			}
		}
	}
}
