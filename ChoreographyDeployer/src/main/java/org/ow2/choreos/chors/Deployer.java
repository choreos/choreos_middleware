/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

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
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpgradedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.ServiceNotDeployedException;
import org.ow2.choreos.services.ServiceNotModifiedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class Deployer {

    private Logger logger = Logger.getLogger(Deployer.class);

    /**
     * 
     * @param chorSpec
     * @return
     * @throws EnactmentException
     *             if DeploymentManager is not accessible
     */
    public Map<String, DeployableService> deployServices(Choreography chor) throws EnactmentException {

	logger.info("Starting to deploy services of choreography " + chor.getId());
	logger.info("Request to configure nodes; creating services; setting up Chef");
	List<DeployableService> choreographyServices = configureNodes(chor);
	if (choreographyServices == null) {
	    logger.info("Deployer got a null list of choreography services; " + "Verify if the DeploymentManager is up");
	    throw new EnactmentException("Probably DeploymentManager is off. "
		    + "Deployer got a null list of choreography services");
	} else if (choreographyServices.isEmpty()) {
	    logger.info("Deployer got a empty list of choreography services; "
		    + "Verify if the DeploymentManager is up");
	    throw new EnactmentException("Probably DeploymentManager is off. "
		    + "Deployer got a null list of choreography services");
	}
	logger.info("Nodes are configured to run chef-client");
	logger.info("Requested to run chef-client on nodes chorId=" + chor.getId());
	Map<String, DeployableService> deployedServices = runChefClient(choreographyServices, chor.getId());
	if (deployedServices == null) {
	    logger.info("Deployed service list became null after run chef-client");
	    throw new EnactmentException("Deployed service list became null after run chef-client");
	} else if (choreographyServices.isEmpty()) {
	    logger.info("eployed service list became empty after run chef-client");
	    throw new EnactmentException("eployed service list became empty after run chef-client");
	}
	logger.info("Deployement finished chorId=" + chor.getId());
	return deployedServices;
    }

    private List<DeployableService> configureNodes(Choreography chor) {
	if (isFirstDeployment(chor)) {
	    ChoreographySpec requestedChorSpec = chor.getRequestedChoreographySpec();
	    return deployNewServices(requestedChorSpec.getDeployableServiceSpecs());
	} else {
	    return updateAndDeployServices(chor);
	}
    }
    
    private boolean isFirstDeployment(Choreography chor) {
	return (chor.getServices() == null) || (chor.getServices().isEmpty());
    }

    private List<DeployableService> deployNewServices(List<DeployableServiceSpec> list) {

	final int TIMEOUT = 30; // it may encompasses bootstrap time
	final int N = list.size();

	ExecutorService executor = Executors.newFixedThreadPool(N);
	Map<DeployableServiceSpec, Future<DeployableService>> futures = new HashMap<DeployableServiceSpec, Future<DeployableService>>();

	for (DeployableServiceSpec choreographyServiceSpec : list) {
	    logger.debug("Requesting deploy of " + choreographyServiceSpec);
	    ServicesManagerInvoker invoker = new ServicesManagerInvoker(choreographyServiceSpec);
	    Future<DeployableService> future = executor.submit(invoker);
	    futures.put(choreographyServiceSpec, future);
	}

	waitExecutor(executor, TIMEOUT);

	List<DeployableService> services = new ArrayList<DeployableService>();
	for (Entry<DeployableServiceSpec, Future<DeployableService>> entry : futures.entrySet()) {
	    try {
		DeployableService service = this.checkFuture(entry.getValue());
		if (service != null) {
		    services.add(service);
		}
	    } catch (Exception e) {
		logger.error("Could not get service from future: " + e.getMessage());
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

    private Map<String, DeployableService> runChefClient(List<DeployableService> services, String chorId) {

	final int TIMEOUT = 30; // chef-client may take a long time
	final int N = services.size();
	if (N <= 0) {
	    logger.error(N + " services within chor " + chorId + "!");
	    throw new IllegalStateException();
	}

	logger.debug("Going to update " + services.size() + " services"); // serviceName
	ExecutorService executor = Executors.newFixedThreadPool(N);
	Map<String, DeployableService> deployedServices = new HashMap<String, DeployableService>();

	for (DeployableService deployable : services) {

	    deployedServices.put(deployable.getSpec().getName(), deployable);
	    String owner = deployable.getSpec().getOwner();

	    if (deployable.getSpec().getPackageType() != PackageType.LEGACY) {
		List<ServiceInstance> instances = deployable.getServiceInstances();
		if (instances != null) {
		    for (ServiceInstance instance : instances) {

			String nodeId = instance.getNode().getId();
			NodeUpgrader upgrader = new NodeUpgrader(nodeId, owner);
			executor.submit(upgrader);
		    }
		} else {
		    logger.warn("No services intances to chor " + chorId + "!");
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

    // =================================================================================================
    // Updating stuffs
    // =================================================================================================

    private Map<String, DeployableServiceSpec> makeMapFromServiceList(List<DeployableServiceSpec> list) {
	Map<String, DeployableServiceSpec> result = new HashMap<String, DeployableServiceSpec>();
	for (DeployableServiceSpec spec : list)
	    result.put(spec.getName(), spec);
	return result;
    }

    private Map<String, DeployableService> getServicesForChor(Choreography chor) {
	Map<String, DeployableService> currentServices = new HashMap<String, DeployableService>();
	for (DeployableService s : chor.getDeployableServices()) {
	    currentServices.put(s.getSpec().getName(), s);
	}
	return currentServices;
    }

    private List<DeployableService> updateAndDeployServices(Choreography chor) {

	Map<String, DeployableServiceSpec> requestedSpecMap = makeMapFromServiceList(chor
		.getRequestedChoreographySpec().getDeployableServiceSpecs());
	Map<String, DeployableService> currentServices = getServicesForChor(chor);
	Map<String, DeployableServiceSpec> toUpdate = new HashMap<String, DeployableServiceSpec>();
	List<DeployableServiceSpec> toCreate = new ArrayList<DeployableServiceSpec>();
	List<DeployableService> updatedServiceList = new ArrayList<DeployableService>();

	for (Map.Entry<String, DeployableService> currentServiceEntry : currentServices.entrySet()) {
	    DeployableServiceSpec requestedSpec = requestedSpecMap.get(currentServiceEntry.getKey());

	    if (requestedSpec != null) {

		if (!requestedSpec.equals(currentServiceEntry.getValue().getSpec())) {

		    toUpdate.put(currentServiceEntry.getValue().getSpec().getName(), requestedSpec);

		} else {
		    updatedServiceList.add(currentServiceEntry.getValue());
		}
	    }
	}

	for (Map.Entry<String, DeployableServiceSpec> specEntry : requestedSpecMap.entrySet()) {
	    if (!currentServices.containsKey(specEntry.getKey())) {
		toCreate.add(specEntry.getValue());
	    }
	}

	updatedServiceList.addAll(doUpdate(chor, toUpdate, toCreate));
	return updatedServiceList;
    }

    private List<DeployableService> doUpdate(Choreography chor, Map<String, DeployableServiceSpec> toUpdate,
	    List<DeployableServiceSpec> toCreate) {

	List<DeployableService> b = new ArrayList<DeployableService>();
	if (toUpdate.size() > 0)
	    b = updateExistingServices(chor, toUpdate);

	if (toCreate.size() > 0) {
	    List<DeployableService> a = deployNewServices(toCreate);
	    b.addAll(a);
	}
	return b;
    }

    private List<DeployableService> updateExistingServices(Choreography chor,
	    Map<String, DeployableServiceSpec> toUpdate) {

	final int TIMEOUT = 10;
	final int N = toUpdate.size();

	ExecutorService executor = Executors.newFixedThreadPool(N);
	Map<DeployableServiceSpec, Future<DeployableService>> futures = new HashMap<DeployableServiceSpec, Future<DeployableService>>();

	for (Entry<String, DeployableServiceSpec> serviceSpec : toUpdate.entrySet()) {
	    logger.debug("Requesting update of " + serviceSpec);

	    DeployableService chorService = chor.getDeployableServiceBySpecName(serviceSpec.getKey());

	    DeployableServiceSpec tmp = serviceSpec.getValue();
	    tmp.setUuid(chorService.getSpec().getUuid());
	    chorService.setSpec(tmp);

	    ServiceUpdateInvoker invoker = new ServiceUpdateInvoker(chorService);
	    Future<DeployableService> future = executor.submit(invoker);
	    futures.put(serviceSpec.getValue(), future);
	}

	waitExecutor(executor, TIMEOUT);

	List<DeployableService> services = new ArrayList<DeployableService>();
	for (Entry<DeployableServiceSpec, Future<DeployableService>> entry : futures.entrySet()) {
	    try {
		DeployableService service = this.checkFuture(entry.getValue());

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

    // ===================================================================================================================
    // Inner Classes
    // ===================================================================================================================

    private class ServicesManagerInvoker implements Callable<DeployableService> {

	DeployableServiceSpec serviceSpec; // input

	public ServicesManagerInvoker(DeployableServiceSpec choreographyServiceSpec) {
	    this.serviceSpec = choreographyServiceSpec;
	}

	@Override
	public DeployableService call() throws Exception {

	    String owner = serviceSpec.getOwner();
	    RESTClientsRetriever retriever = new RESTClientsRetriever();
	    ServicesManager servicesManager = retriever.getServicesClient(owner);

	    try {
		DeployableService deployedService = servicesManager.createService(serviceSpec);
		return deployedService;
	    } catch (ServiceNotDeployedException e) {
		logger.error("Probably DeploymentManager is off");
		throw e;
	    }
	}

    }

    private class ServiceUpdateInvoker implements Callable<DeployableService> {

	private DeployableService service;

	public ServiceUpdateInvoker(DeployableService choreographyService) {
	    this.service = choreographyService;
	}

	@Override
	public DeployableService call() throws ServiceNotModifiedException, UnhandledModificationException {

	    String owner = service.getSpec().getOwner();
	    RESTClientsRetriever retriever = new RESTClientsRetriever();
	    ServicesManager servicesManager = retriever.getServicesClient(owner);

	    try {
		return servicesManager.updateService(service.getSpec());
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

	String nodeId, owner;

	public NodeUpgrader(String nodeId, String owner) {
	    this.nodeId = nodeId;
	    this.owner = owner;
	}

	@Override
	public void run() {

	    RESTClientsRetriever retriever = new RESTClientsRetriever();
	    NodePoolManager npm = retriever.getNodesClient(owner);

	    try {
		npm.updateNode(nodeId);
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
