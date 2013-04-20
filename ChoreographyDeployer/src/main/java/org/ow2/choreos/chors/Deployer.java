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
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.ServiceNotModifiedException;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.diff.UnhandledModificationException;
import org.ow2.choreos.deployment.services.rest.ServicesClient;

public class Deployer {

	private Logger logger = Logger.getLogger(Deployer.class);
	private volatile ServicesManager servicesManager = new ServicesClient(
			Configuration.get(Option.DEPLOYMENT_MANAGER_URI));

	/**
	 * 
	 * @param chorSpec
	 * @return
	 * @throws EnactmentException
	 *             if DeploymentManager is not accessible
	 */
	public Map<String, ChoreographyService> deployChoreographyServices(
			Choreography chor) throws EnactmentException {
		logger.info("Starting to deploy services of choreography "
				+ chor.getId());
		logger.info("Request to configure nodes; creating services; setting up Chef");
		List<ChoreographyService> choreographyServices = configureNodes(chor);
		if (choreographyServices == null) {
			logger.info("Deployer got a null list of choreography services; "
					+ "Verify if the DeploymentManager is up");
			throw new EnactmentException("Probably DeploymentManager is off. "
					+ "Deployer got a null list of choreography services");
		} else if (choreographyServices.isEmpty()) {
			logger.info("Deployer got a empty list of choreography services; "
					+ "Verify if the DeploymentManager is up");
			throw new EnactmentException("Probably DeploymentManager is off. "
					+ "Deployer got a null list of choreography services");
		}
		logger.info("Nodes are configured to run chef-client");
		logger.info("Requested to run chef-client on nodes");
		Map<String, ChoreographyService> deployedServices = runChefClient(choreographyServices);
		if (deployedServices == null) {
			logger.info("Deployed service list became null after run chef-client");
			throw new EnactmentException(
					"Deployed service list became null after run chef-client");
		} else if (choreographyServices.isEmpty()) {
			logger.info("eployed service list became empty after run chef-client");
			throw new EnactmentException(
					"eployed service list became empty after run chef-client");
		}
		logger.info("Deployement finished");
		return deployedServices;
	}

	private List<ChoreographyService> configureNodes(Choreography chor) {
		if ((chor.getDeployedChoreographyServices() == null)
				|| (chor.getDeployedChoreographyServices().isEmpty())) {
			return deployNewServices(chor.getRequestedChoreographySpec()
					.getChoreographyServiceSpecs());
		} else {
			System.out.println("THE WHOLE CHOR >>> " + chor);
			return updateAndDeployServices(chor);
		}
	}

	private List<ChoreographyService> deployNewServices(
			List<ChoreographyServiceSpec> list) {

		final int TIMEOUT = 8; // it may encompasses bootstrap time
		final int N = list.size();

		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<ChoreographyServiceSpec, Future<ChoreographyService>> futures = new HashMap<ChoreographyServiceSpec, Future<ChoreographyService>>();

		for (ChoreographyServiceSpec choreographyServiceSpec : list) {
			logger.debug("Requesting deploy of " + choreographyServiceSpec);
			ServicesManagerInvoker invoker = new ServicesManagerInvoker(
					choreographyServiceSpec);
			Future<ChoreographyService> future = executor.submit(invoker);
			futures.put(choreographyServiceSpec, future);
		}

		waitExecutor(executor, TIMEOUT);

		List<ChoreographyService> services = new ArrayList<ChoreographyService>();
		for (Entry<ChoreographyServiceSpec, Future<ChoreographyService>> entry : futures
				.entrySet()) {
			try {
				ChoreographyService service = this
						.checkFuture(entry.getValue());
				if (service != null) {
					services.add(service);
				}
			} catch (Exception e) {
				logger.error("Could not get service from future: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return services;
	}

	private <T> T checkFuture(Future<T> f) throws Exception {
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
	private Map<String, ChoreographyService> runChefClient(
			List<ChoreographyService> services) {

		final int TIMEOUT = 10; // chef-client may take a long time
		final int N = services.size();
		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<String, ChoreographyService> deployedServices = new HashMap<String, ChoreographyService>(); // key
																										// is
																										// serviceName

		for (ChoreographyService deployed : services) {

			deployedServices.put(deployed.getChoreographyServiceSpec()
					.getChoreographyServiceUID(), deployed);

			if (deployed.getChoreographyServiceSpec().getServiceSpec()
					.getPackageType() != PackageType.LEGACY) {
				for (ServiceInstance instance : ((DeployedService) deployed
						.getService()).getInstances()) {
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
			status = executor
					.awaitTermination(timeoutMinutes, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error("Interrupted thread! Should not happen!", e);
		}
		if (!status) {
			logger.info("Executor status is False. Probably there is some problem!.");
		}
		executor.shutdownNow();
	}

	// =================================================================================================
	// Updating stuffs
	// =================================================================================================

	private Map<String, ChoreographyServiceSpec> makeMapFromServiceList(
			List<ChoreographyServiceSpec> list) {
		
		Map<String, ChoreographyServiceSpec> result = new HashMap<String, ChoreographyServiceSpec>();
		for (ChoreographyServiceSpec spec : list)
			result.put(spec.getChoreographyServiceUID(), spec);
		
		return result;
	}

	private Map<String, ChoreographyService> getServicesForChor(
			Choreography chor) {
		Map<String, ChoreographyService> currentServices = new HashMap<String, ChoreographyService>();

		for (ChoreographyService s : chor.getDeployedChoreographyServices()) {
			currentServices.put(s.getChoreographyServiceSpec()
					.getChoreographyServiceUID(), s);
		}
		return currentServices;
	}

	private List<ChoreographyService> updateAndDeployServices(Choreography chor) {

		Map<String, ChoreographyServiceSpec> requestedSpecMap = makeMapFromServiceList(chor
				.getRequestedChoreographySpec().getChoreographyServiceSpecs());
		Map<String, ChoreographyService> currentServices = getServicesForChor(chor);
		Map<String, ChoreographyServiceSpec> toUpdate = new HashMap<String, ChoreographyServiceSpec>();
		List<ChoreographyServiceSpec> toCreate = new ArrayList<ChoreographyServiceSpec>();
		List<ChoreographyService> updatedServiceList = new ArrayList<ChoreographyService>();

		for (Map.Entry<String, ChoreographyService> currentServiceEntry : currentServices
				.entrySet()) {			
			ChoreographyServiceSpec requestedSpec = requestedSpecMap
					.get(currentServiceEntry.getKey());
			
			if (requestedSpec != null) {
				
				if (!requestedSpec.equals(currentServiceEntry.getValue()
						.getChoreographyServiceSpec())) {
					
					toUpdate.put(currentServiceEntry.getValue()
							.getChoreographyServiceSpec()
							.getChoreographyServiceUID(), requestedSpec);
					
				} else {
					updatedServiceList.add(currentServiceEntry.getValue());
				}
			}
		}

		for (Map.Entry<String, ChoreographyServiceSpec> specEntry : requestedSpecMap
				.entrySet()) {
			if (!currentServices.containsKey(specEntry.getKey())) {
				toCreate.add(specEntry.getValue());
			}
		}

		updatedServiceList.addAll(doUpdate(chor, toUpdate, toCreate));
		return updatedServiceList;
	}

	private List<ChoreographyService> doUpdate(Choreography chor,
			Map<String, ChoreographyServiceSpec> toUpdate,
			List<ChoreographyServiceSpec> toCreate) {

		List<ChoreographyService> b = new ArrayList<ChoreographyService>();
		if(toUpdate.size() > 0) 
			b = updateExistingServices(chor, toUpdate);

		if (toCreate.size() > 0) {
			List<ChoreographyService> a = deployNewServices(toCreate);
			b.addAll(a);
		}
		return b;
	}

	private List<ChoreographyService> updateExistingServices(Choreography chor,
			Map<String, ChoreographyServiceSpec> toUpdate) {

		final int TIMEOUT = 10;
		final int N = toUpdate.size();
		
		ExecutorService executor = Executors.newFixedThreadPool(N);
		Map<ChoreographyServiceSpec, Future<ChoreographyService>> futures = new HashMap<ChoreographyServiceSpec, Future<ChoreographyService>>();

		for (Entry<String, ChoreographyServiceSpec> serviceSpec : toUpdate
				.entrySet()) {
			logger.debug("Requesting update of " + serviceSpec);

			ChoreographyService chorService = chor
					.getDeployedChoreographyServiceByChoreographyServiceUID(serviceSpec
							.getKey());

			ServiceUpdateInvoker invoker = new ServiceUpdateInvoker(chorService);
			Future<ChoreographyService> future = executor.submit(invoker);
			futures.put(serviceSpec.getValue(), future);
		}

		waitExecutor(executor, TIMEOUT);

		List<ChoreographyService> services = new ArrayList<ChoreographyService>();
		for (Entry<ChoreographyServiceSpec, Future<ChoreographyService>> entry : futures
				.entrySet()) {
			try {
				ChoreographyService service = this
						.checkFuture(entry.getValue());

				if (service != null) {
					services.add(service);
				}
			} catch (Exception e) {
				logger.error("Could not get service from future: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return services;
	}

	// ===================================================================================================================
	// Inner Classes
	// ===================================================================================================================

	private class ServicesManagerInvoker implements
			Callable<ChoreographyService> {

		ChoreographyServiceSpec choreographyServiceSpec; // input

		public ServicesManagerInvoker(
				ChoreographyServiceSpec choreographyServiceSpec) {
			this.choreographyServiceSpec = choreographyServiceSpec;
		}

		@Override
		public ChoreographyService call() throws Exception {
			try {
				DeployedService deployed = servicesManager
						.createService((DeployedServiceSpec) choreographyServiceSpec
								.getServiceSpec());
				return getChoreographyService(deployed);
			} catch (ServiceNotDeployedException e) {
				logger.error("Probably DeploymentManager is off");
				throw e;
			}
		}

		private ChoreographyService getChoreographyService(DeployedService d) {
			ChoreographyService s = new ChoreographyService(
					choreographyServiceSpec);
			s.setService(d);
			return s;
		}
	}

	private class ServiceUpdateInvoker implements Callable<ChoreographyService> {

		private ChoreographyService choreographyService;

		public ServiceUpdateInvoker(ChoreographyService choreographyService) {
			this.choreographyService = choreographyService;
		}

		@Override
		public ChoreographyService call() throws ServiceNotModifiedException,
				UnhandledModificationException {
			try {
				choreographyService.getChoreographyServiceSpec()
				.getServiceSpec().setUUID(choreographyService.getService().getSpec().getUUID());
				
				servicesManager
						.updateService(
								(DeployedServiceSpec) choreographyService
								.getChoreographyServiceSpec().getServiceSpec());
			} catch (ServiceNotModifiedException e) {
				logger.error(e.getMessage());
				throw e;
			} catch (UnhandledModificationException e) {
				logger.error(e.getMessage());
				throw e;
			}
			return choreographyService;
		}
	}

	private class NodeUpgrader implements Runnable {

		NodePoolManager npm = new NodesClient(
				Configuration.get(Option.DEPLOYMENT_MANAGER_URI));
		String nodeId;

		public NodeUpgrader(String nodeId) {
			this.nodeId = nodeId;
		}

		@Override
		public void run() {
			try {
				npm.upgradeNode(nodeId);
			} catch (NodeNotUpgradedException e) {
				logger.error("Bad response from /nodes/" + nodeId
						+ "/upgrade; maybe some service is not deployed");
			} catch (NodeNotFoundException e) {
				logger.error("Bad response from /nodes/" + nodeId
						+ "/upgrade; maybe some service is not deployed");
			} catch (org.apache.cxf.interceptor.Fault e) {
				throw e;
			}
		}

	}
}
