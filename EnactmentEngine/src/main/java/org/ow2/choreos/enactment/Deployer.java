package org.ow2.choreos.enactment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.Configuration.Option;
import org.ow2.choreos.enactment.datamodel.ChorServiceSpec;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.npm.NodeNotFoundException;
import org.ow2.choreos.npm.NodeNotUpgradedException;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.client.NPMClient;
import org.ow2.choreos.servicedeployer.ServiceDeployer;
import org.ow2.choreos.servicedeployer.ServiceNotDeployedException;
import org.ow2.choreos.servicedeployer.client.ServiceDeployerClient;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceSpec;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;

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
			
			if (deployed.getSpec().getType() != ServiceType.LEGACY) {
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

		ServiceDeployer deployer = new ServiceDeployerClient(Configuration.get(Option.SERVICE_DEPLOYER_URI));
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

		NodePoolManager npm = new NPMClient(Configuration.get(Option.NODE_POOL_MANAGER_URI));
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
