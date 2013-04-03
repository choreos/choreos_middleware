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
import org.ow2.choreos.deployment.services.ServiceNotModifiedException;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.diff.UnhandledModificationException;
import org.ow2.choreos.deployment.services.rest.ServicesClient;

public class Deployer {

	private Logger logger = Logger.getLogger(Deployer.class);
	private volatile ServicesManager servicesManager = new ServicesClient(Configuration.get(Option.DEPLOYMENT_MANAGER_URI));

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
		
		List<Service> services = null; // = new ArrayList<Service>();

		if( isFirstDeployment(chor) ) {
			services = deployNewChoreography(chor);
		} else {
			services = updateExistingChoreography(chor);
		}
		return services;
	}

	private List<Service> updateExistingChoreography(Choreography chor /*,List<Service> services*/) {
		List<Service> services = null;
		if(!chor.getRequestedSpec().equals(chor.getSpec())) {
			services = updateAndDeployServices(chor);
		}
		return services;
	}

	private List<Service> deployNewChoreography(Choreography chor) {
		List<Service> services;
		services = deployNewServices(chor.getRequestedSpec().getServiceSpecs());
		return services;
	}

	private boolean isFirstDeployment(Choreography chor) {
		return (chor.getServices() == null) || (chor.getServices().isEmpty());
	}

	private List<Service> deployNewServices(List<ServiceSpec> list) {

		final int TIMEOUT = 5; // it may encompasses bootstrap time
		final int N = list.size();

		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<ServiceSpec, Future<Service>> futures = new HashMap<ServiceSpec, Future<Service>>();

		for (ServiceSpec serviceSpec : list) {
			logger.debug("Requesting deploy of " + serviceSpec);
			ServicesManagerInvoker invoker = new ServicesManagerInvoker(serviceSpec);
			Future<Service> future = executor.submit(invoker);
			futures.put(serviceSpec, future);	
		}

		waitExecutor(executor, TIMEOUT);

		List<Service> services = new ArrayList<Service>();
		for (Entry<ServiceSpec, Future<Service>> entry : futures.entrySet())
		{
			try {
				Service service = this.checkFuture(entry.getValue());
				if (service != null) {
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
	
	private Map<String, Service> getServicesForChor(Choreography chor) {
		Map<String, Service> currentServices = new HashMap<String, Service>();

		for( Service s : chor.getServices() ) {
			currentServices.put(s.getSpec().getName(), s);
		}
		return currentServices;
	}
	
	private List<Service> updateAndDeployServices(Choreography chor) {

		Map<String, ServiceSpec> requestedSpecMap = makeMapFromServiceList(chor.getRequestedSpec().getServiceSpecs());
		Map<String, Service> currentServices = getServicesForChor(chor);
		
		Map<String, ServiceSpec> toUpdate = new HashMap<String, ServiceSpec>();
		List<ServiceSpec> toCreate = new ArrayList<ServiceSpec>();
		List<Service> updatedServiceList = new ArrayList<Service>();

		for (Map.Entry<String, Service> currentServiceEntry : currentServices.entrySet()) {	
			
			ServiceSpec requestedSpec = requestedSpecMap.get(currentServiceEntry.getKey());
			
			if(requestedSpec != null) {
				
				if(!requestedSpec.equals(currentServiceEntry.getValue().getSpec())) {
					toUpdate.put(currentServiceEntry.getValue().getId(), requestedSpec);
				} else {
					updatedServiceList.add(currentServiceEntry.getValue());
				}
			}
			else {
				//chor.addScheduledServiceRemoval(specEntry.getValue()); // there is no more this service in choreography
			}
		}

		for (Map.Entry<String, ServiceSpec> specEntry : requestedSpecMap.entrySet()) {
			if(!currentServices.containsKey(specEntry.getKey())) {
				toCreate.add(specEntry.getValue());
			}
		}

		updatedServiceList.addAll(doUpdate(toUpdate, toCreate));
		return updatedServiceList;
	}


	private List<Service> doUpdate(Map<String, ServiceSpec> toUpdate, List<ServiceSpec> toCreate) {

		List<Service> b = updateExistingServices(toUpdate);
		if(toCreate.size() > 0) {
			List<Service> a = deployNewServices(toCreate);
			b.addAll(a);
		}
		return b;
	}

	private List<Service> updateExistingServices(Map<String, ServiceSpec> toUpdate) {

		final int TIMEOUT = 5;
		final int N = toUpdate.size();

		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<ServiceSpec, Future<Service>> futures = new HashMap<ServiceSpec, Future<Service>>();

		for (Entry<String, ServiceSpec> serviceSpec : toUpdate.entrySet()) {
			logger.debug("Requesting update of " + serviceSpec);
			ServiceUpdateInvoker invoker = new ServiceUpdateInvoker(serviceSpec.getKey(), serviceSpec.getValue());
			Future<Service> future = executor.submit(invoker);
			futures.put(serviceSpec.getValue(), future);	
		}

		waitExecutor(executor, TIMEOUT);

		List<Service> services = new ArrayList<Service>();
		for (Entry<ServiceSpec, Future<Service>> entry : futures.entrySet())
		{
			try {
				Service service = this.checkFuture(entry.getValue());
				
				//System.out.println("Printing returned service from future ... \n\n" + service + "\n\n");
				
				if (service != null) {
					services.add(service);
				}
			} catch (Exception e) {
				logger.error("Could not get service from future: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return services;
	}
	
	//===================================================================================================================
	//								Inner Classes
	//===================================================================================================================


	private class ServicesManagerInvoker implements Callable<Service> {

		ServiceSpec serviceSpec; // input

		public ServicesManagerInvoker(ServiceSpec serviceSpec) {
			this.serviceSpec = serviceSpec;
		}

		@Override
		public Service call() throws Exception {
			try {
				Service deployed = servicesManager.createService(serviceSpec);
				return deployed;
			} catch (ServiceNotDeployedException e) {
				logger.error("Probably DeploymentManager is off");
				throw e;
			} 
		}
	}

	private class ServiceUpdateInvoker implements Callable<Service> {

		private ServiceSpec serviceSpec;
		private String serviceId;

		public ServiceUpdateInvoker(String serviceId, ServiceSpec serviceSpec) {
			this.serviceSpec = serviceSpec;
			this.serviceId = serviceId;
		}

		@Override
		public Service call() throws ServiceNotModifiedException, UnhandledModificationException {
			try {
				return servicesManager.updateService(serviceId, serviceSpec);
			} catch (ServiceNotModifiedException e) {
				logger.error(e.getMessage());
				throw e;
			} catch (UnhandledModificationException e) {
				logger.error(e.getMessage());
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
