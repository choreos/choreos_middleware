package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.rest.ServicesClient;

public class Deployer {

	private Logger logger = Logger.getLogger(Deployer.class);
	
	public Map<String, Service> deployServices(ChorSpec chor) {
	
		logger.info("Deploying services");

		List<Thread> trds = new ArrayList<Thread>();
		List<ServiceDeployerInvoker> invokers = new ArrayList<ServiceDeployerInvoker>();
		for (ChorServiceSpec chorServiceSpec: chor.getServiceSpecs()) {
			ServiceSpec serviceSpec = chorServiceSpec.getServiceSpec();
			logger.debug("Requesting deploy of " + serviceSpec);
			ServiceDeployerInvoker invoker = new ServiceDeployerInvoker(serviceSpec);
			Thread trd = new Thread(invoker);
			trds.add(trd);
			invokers.add(invoker);
			trd.start();
		}
		
		waitThreads(trds);
		
		logger.info("Nodes are configured to receive services");
		
		trds = new ArrayList<Thread>();
		Map<String, Service> deployedServices = new HashMap<String, Service>(); // key is serviceName
		for (ServiceDeployerInvoker invoker: invokers) {
			
			Service deployed = invoker.deployed;
			deployedServices.put(deployed.getName(), deployed);
			
			if (deployed.getSpec().getArtifactType() != ArtifactType.LEGACY) {
				String nodeId = deployed.getNodeId();
				NodeUpgrader upgrader = new NodeUpgrader(nodeId);
				Thread trd = new Thread(upgrader);
				trds.add(trd);
				trd.start();
			}
		}
		
		waitThreads(trds);
		
		logger.info("Deployement finished");

		return deployedServices;
	}
	
	private void waitThreads(List<Thread> trds) {

		for (Thread trd: trds) {
			try {
				trd.join();
			} catch (InterruptedException e) {
				logger.error("Error at thread join!", e);
			}
		}
	}

	private class ServiceDeployerInvoker implements Runnable {

		ServiceDeployer deployer = new ServicesClient(Configuration.get(Option.DEPLOYMENT_MANAGER_URI));
		ServiceSpec serviceSpec; // input
		Service deployed; // output
		
		public ServiceDeployerInvoker(ServiceSpec serviceSpec) {
			this.serviceSpec = serviceSpec;
		}
		
		@Override
		public void run() {
			try {
				deployed = deployer.deploy(serviceSpec);
			} catch (ServiceNotDeployedException e) {
				logger.error(serviceSpec.getName() + " deploy has failed");
			}			
		}
		
	}
	
	private class NodeUpgrader implements Runnable {

		NodePoolManager npm = new NodesClient(Configuration.get(Option.DEPLOYMENT_MANAGER_URI));
		String nodeId;
		
		public NodeUpgrader(String nodeId) {
			this.nodeId = nodeId;
		}
		
		@Override
		public void run() {
			try {
				npm.upgradeNode(nodeId);
			} catch (NodeNotUpgradedException e) {
				logger.error("Bad response from /nodes/" + nodeId + "/upgrade; maybe some service is not deployed");
			} catch (NodeNotFoundException e) {
				logger.error("Bad response from /nodes/" + nodeId + "/upgrade; maybe some service is not deployed");
			}
		}
		
	}
}
