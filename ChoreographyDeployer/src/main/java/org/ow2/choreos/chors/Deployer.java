package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.SpecAndService;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
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
	public Map<String, Service> deployServices(Choreography chor) throws EnactmentException {

		logger.info("Deploying services");
		
		List<Service> services = configureNodes(chor);
		
		if (services == null || services.isEmpty()) {
			throw new EnactmentException("Probably DeploymentManager is off");
		}

		logger.info("Nodes are configured to receive services");

		Map<String, Service> deployedServices = upgradeNodes(services);

		logger.info("Deployement finished");

		return deployedServices;
	}

	private List<Service> configureNodes(Choreography chor) {
		List<Service> services = new ArrayList<Service>();
		
		if(chor.getSpecsAndServices().isEmpty() ) {
			for (ChorServiceSpec spec : chor.getRequestedSpec().getChorServiceSpecs())
				chor.addScheduledServiceCreation(spec);
			services = deployNewServices(chor);
		} else {
			if(!chor.getRequestedSpec().equals(chor.getSpec())) {
				services = doUpdatedDiff(chor);
			}
		}
		return services;
	}

	private List<Service> deployNewServices(Choreography chor) {
		
		final int TIMEOUT = 1; // communication between chorDeployer and DeploymentManager should be fast
		final int N = chor.getRequestedSpec().getChorServiceSpecs().size();
		
		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<ChorServiceSpec, Future<Service>> futures = new HashMap<ChorServiceSpec, Future<Service>>();

		for (ChorServiceSpec chorServiceSpec: chor.getScheduledServiceCreation()) {
			ServiceSpec serviceSpec = chorServiceSpec.getServiceSpec();
			logger.debug("Requesting deploy of " + serviceSpec);
			ServiceDeployerInvoker invoker = new ServiceDeployerInvoker(serviceSpec);
			Future<Service> future = executor.submit(invoker);
			futures.put(chorServiceSpec, future);	
		}
		
		waitExecutor(executor, TIMEOUT);
		
		List<Service> services = new ArrayList<Service>();
		for (Entry<ChorServiceSpec, Future<Service>> f : futures.entrySet())
		{
			try {
				Service service = this.checkFuture(f.getValue());
				if (service != null) {
					chor.addSpecAndService(new SpecAndService(f.getKey(), service));
					services.add(service);
				}
			} catch (Exception e) {
				logger.error("Could not get service from future: " + e.getMessage());
				e.printStackTrace();
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

			if (deployed.getSpec().getPackageType() != PackageType.LEGACY) {
				for(ServiceInstance instance: deployed.getInstances()) {
					String nodeId = instance.getNode().getId();
					NodeUpgrader upgrader = new NodeUpgrader(nodeId);
					executor.submit(upgrader);

				}
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
	
	
	
	
	//=================================================================================================
	// 			Updating stuffs
	//=================================================================================================
	
	private Map<String, ChorServiceSpec> makeMapFromServiceList (List<ChorServiceSpec> l) {
		Map<String, ChorServiceSpec> result = new HashMap<String, ChorServiceSpec>();
		for(ChorServiceSpec spec:l)
			result.put(spec.getName(), spec);
		return result;
	}

	private List<Service> doUpdatedDiff(Choreography chor) {
		
		Map<String, ChorServiceSpec> requestedSpecMap = 
				makeMapFromServiceList(chor.getRequestedSpec().getChorServiceSpecs());
		Map<String, ChorServiceSpec> specMap = 
				makeMapFromServiceList(chor.getSpec().getChorServiceSpecs());
			
		for (Map.Entry<String, ChorServiceSpec> specEntry : specMap.entrySet()) {	
			ChorServiceSpec spec = requestedSpecMap.get(specEntry.getKey());
			if(requestedSpecMap.containsKey(specEntry.getKey())) {
				if(!spec.equals(specEntry.getValue())) {
					chor.addScheduledServiceUpdate(spec); // same service, different specs
				}
				chor.addScheduledServiceNoChange(spec);
			}
			else {
				chor.addScheduledServiceRemoval(spec); // there is no more this service in choreography
			}
		}
		
		for (Map.Entry<String, ChorServiceSpec> specEntry : requestedSpecMap.entrySet()) {
			if(!specMap.containsKey(specEntry.getKey())) {
				chor.addScheduledServiceCreation(specEntry.getValue());
			}
		}
				
		return doUpdate(chor);
	}

	private List<Service> doUpdate(Choreography chor) {
		
		logger.info("Trying to deploy new services.");
		
		createNewServicesWithInstances(chor);
		
		logger.info("New services deployed. ");
		
		logger.info("Trying to create new instances for currently running services. ");
		
		createNewServiceInstancesForExistingServices(chor);
		
		logger.info("New instances created successfully");
		
		return chor.getDeployedServices();
	}

	private void createNewServicesWithInstances(Choreography chor) {
		this.deployNewServices(chor);
	}

	private void createNewServiceInstancesForExistingServices(Choreography chor) {
		
	}
	
	
	
	
	//===================================================================================================================
	//								Inner Classes
	//===================================================================================================================

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
