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
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.deployment.services.ScheduledServiceModification;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.SpecAndService;
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

		if( isFirstDeployment(chor) ) {
			services = deployNewChoreography(chor);
		} else {
			services = updateExistingChoreography(chor, services);
		}
		return services;
	}

	private List<Service> updateExistingChoreography(Choreography chor,
			List<Service> services) {
		if(!chor.getRequestedSpec().equals(chor.getSpec())) {
			services = updateAndDeployServices(chor);
		}
		return services;
	}

	private List<Service> deployNewChoreography(Choreography chor) {
		List<Service> services;
		for (ServiceSpec spec : chor.getRequestedSpec().getServiceSpecs())
			chor.addScheduledServiceCreation(spec);
		services = deployNewServices(chor);
		return services;
	}

	private boolean isFirstDeployment(Choreography chor) {
		return chor.getSpecsAndServices().isEmpty();
	}

	private List<Service> deployNewServices(Choreography chor) {

		final int TIMEOUT = 1; // communication between chorDeployer and DeploymentManager should be fast
		final int N = chor.getRequestedSpec().getServiceSpecs().size();

		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<ServiceSpec, Future<Service>> futures = new HashMap<ServiceSpec, Future<Service>>();

		for (ScheduledServiceModification s : chor.getScheduledServiceCreation()) {
			ServiceSpec serviceSpec = s.getRequestedSpec();
			logger.debug("Requesting deploy of " + serviceSpec);
			ServiceDeployerInvoker invoker = new ServiceDeployerInvoker(serviceSpec);
			Future<Service> future = executor.submit(invoker);
			futures.put(serviceSpec, future);	
		}

		waitExecutor(executor, TIMEOUT);

		List<Service> services = new ArrayList<Service>();
		for (Entry<ServiceSpec, Future<Service>> f : futures.entrySet())
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

	private Map<String, ServiceSpec> makeMapFromServiceList (List<ServiceSpec> l) {
		Map<String, ServiceSpec> result = new HashMap<String, ServiceSpec>();
		for(ServiceSpec spec:l)
			result.put(spec.getName(), spec);
		return result;
	}

	private Map<String, SpecAndService> getSpecsAndServicesForChor(
			Choreography chor) {
		Map<String, SpecAndService> currentSpecsAndServices = new HashMap<String, SpecAndService>();
		
		for( SpecAndService s : chor.getSpecsAndServices() ) {
			currentSpecsAndServices.put(s.getSpec().getName(), s);
		}
		return currentSpecsAndServices;
	}

	private List<Service> updateAndDeployServices(Choreography chor) {

		Map<String, ServiceSpec> requestedSpecMap = makeMapFromServiceList(chor.getRequestedSpec().getServiceSpecs());
		Map<String, SpecAndService> currentSpecsAndServices = getSpecsAndServicesForChor(chor);

		for (Map.Entry<String, SpecAndService> specEntry : currentSpecsAndServices.entrySet()) {	
			ServiceSpec spec = requestedSpecMap.get(specEntry.getKey());
			if(requestedSpecMap.containsKey(specEntry.getKey())) {
				if(!spec.equals(specEntry.getValue().getSpec())) {
					chor.addScheduledServiceUpdate(spec, specEntry.getValue()); // same service, different specs
				}
				chor.addScheduledServiceNoChange(specEntry.getValue());
			}
			else {
				chor.addScheduledServiceRemoval(specEntry.getValue()); // there is no more this service in choreography
			}
		}

		for (Map.Entry<String, ServiceSpec> specEntry : requestedSpecMap.entrySet()) {
			if(!currentSpecsAndServices.containsKey(specEntry.getKey())) {
				chor.addScheduledServiceCreation(specEntry.getValue());
			}
		}

		return doUpdate(chor);
	}


	private List<Service> doUpdate(Choreography chor) {

		deployNewServices(chor);
		updateExistingServices(chor);

		return chor.getDeployedServices();
	}

	private void updateExistingServices(Choreography chor) {

		final int TIMEOUT = 5;
		final int N = chor.getScheduledServiceUpdate().size();

		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<ServiceSpec, Future<Service>> futures = new HashMap<ServiceSpec, Future<Service>>();

		for (ScheduledServiceModification s : chor.getScheduledServiceUpdate()) {
			ServiceSpec serviceSpec = s.getRequestedSpec();
			logger.debug("Requesting update of " + serviceSpec);
			ServiceUpdateInvoker invoker = new ServiceUpdateInvoker(serviceSpec);
			Future<Service> future = executor.submit(invoker);
			futures.put(serviceSpec, future);	
		}

		waitExecutor(executor, TIMEOUT);

		for (Entry<ServiceSpec, Future<Service>> f : futures.entrySet())
		{
			try {
				this.checkFuture(f.getValue());
			} catch (Exception e) {
				logger.error("Could not get service from future: " + e.getMessage());
				e.printStackTrace();
			}
		}
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

	private class ServiceUpdateInvoker implements Callable<Service> {

		ServiceDeployer deployer = new ServicesClient(Configuration.get(Option.DEPLOYMENT_MANAGER_URI));
		private ServiceSpec serviceSpec;

		public ServiceUpdateInvoker(ServiceSpec serviceSpec) {
			this.serviceSpec = serviceSpec;
		}

		@Override
		public Service call() throws Exception {
			deployer.updateService(serviceSpec);
			return new Service();
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
