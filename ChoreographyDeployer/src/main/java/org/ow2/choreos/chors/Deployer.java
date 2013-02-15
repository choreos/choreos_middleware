package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
	
	/** 
	 * 
	 * @param chorSpec
	 * @return
	 * @throws EnactmentException if DeploymentManager is not accessible
	 */
	public Map<String, Service> deployServices(ChorSpec chorSpec) throws EnactmentException {
	
		logger.info("Deploying services");

		List<Service> services = configureNodes(chorSpec);
		if (services == null || services.isEmpty()) {
			throw new EnactmentException("Probably DeploymentManager is off");
		}
		
		logger.info("Nodes are configured to receive services");
		
		Map<String, Service> deployedServices = upgradeNodes(services);
		
		logger.info("Deployement finished");

		return deployedServices;
	}
	
	// knife node list add ...
	private List<Service> configureNodes(ChorSpec chorSpec) {
 
		final int TIMEOUT = 1; // communication between chorDeployer and DeploymentManager should be fast
		final int N = chorSpec.getServiceSpecs().size();
		ExecutorService executor = Executors.newFixedThreadPool(N);
		List<Future<Service>> futures = new ArrayList<Future<Service>>();
		
		for (ChorServiceSpec chorServiceSpec: chorSpec.getServiceSpecs()) {
			
			ServiceSpec serviceSpec = chorServiceSpec.getServiceSpec();
			logger.debug("Requesting deploy of " + serviceSpec);
			ServiceDeployerInvoker invoker = new ServiceDeployerInvoker(serviceSpec);
			Future<Service> future = executor.submit(invoker);
			futures.add(future);
		}
		
		waitExecutor(executor, TIMEOUT);
		
		List<Service> services = new ArrayList<Service>();
		for (Future<Service> f: futures) {
			try {
				Service service = this.checkFuture(f);
				if (service != null) {
					services.add(service);
				}
			} catch (Exception e) {
				logger.error("Could not get service from future: " + e.getMessage());
			}
		}
		return services;
	}
	
	private <T> T checkFuture(Future<T> f)  throws Exception {
		T result = null;
		try {
			if (f.isDone()) {
				result = f.get();
			} else {
				throw new Exception(); 
			}
		} catch (InterruptedException e) {
			throw e;
		} catch (ExecutionException e) {
			throw e;
		} catch (CancellationException e) {
			throw e;
		}
		return result;
	}

	// chef-client
	private Map<String, Service> upgradeNodes(List<Service> services) {

		final int TIMEOUT = 10; // chef-client may take a long time
		final int N = services.size();
		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<String, Service> deployedServices = new HashMap<String, Service>(); // key is serviceName

		for (Service deployed: services) {
			
			deployedServices.put(deployed.getName(), deployed);
			if (deployed.getSpec().getArtifactType() != ArtifactType.LEGACY) {

				String nodeId = deployed.getNodeId();
				NodeUpgrader upgrader = new NodeUpgrader(nodeId);
				executor.submit(upgrader);
			}
		}

		waitExecutor(executor, TIMEOUT);
		return deployedServices;
	}

	private void waitExecutor(ExecutorService executor, int timeoutMinutes) {

		executor.shutdown();
		boolean status = false;
		try {
			status = executor.awaitTermination(timeoutMinutes, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error("Interrupted thread! Should not happen!", e);
		}
		if (!status) {
			logger.info("Executor status is False. Probably there is some problem!.");
		}
		executor.shutdownNow();
	}

	private class ServiceDeployerInvoker implements Callable<Service> {

		ServiceDeployer deployer = new ServicesClient(Configuration.get(Option.DEPLOYMENT_MANAGER_URI));
		ServiceSpec serviceSpec; // input
		
		public ServiceDeployerInvoker(ServiceSpec serviceSpec) {
			this.serviceSpec = serviceSpec;
		}
		
		@Override
		public Service call() throws Exception {
			try {
				Service deployed = deployer.deploy(serviceSpec);
				return deployed;
			} catch (ServiceNotDeployedException e) {
				logger.error("Probably DeploymentManager is off");
				throw e;
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
			} catch (org.apache.cxf.interceptor.Fault e) {
				throw e;
			}
		}
	}
}
