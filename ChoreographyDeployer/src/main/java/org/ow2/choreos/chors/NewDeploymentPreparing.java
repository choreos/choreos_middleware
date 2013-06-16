package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.utils.Concurrency;

public class NewDeploymentPreparing {

    private static final int TIMEOUT = 30; // it may encompasses bootstrap time

    private Choreography chor;
    private RESTClientsRetriever servicesClientRetriever = new RESTClientsRetriever();
    private ExecutorService executor;
    private Map<DeployableServiceSpec, Future<DeployableService>> futures;
    private List<DeployableService> configuredServices;

    private Logger logger = Logger.getLogger(NewDeploymentPreparing.class);

    public NewDeploymentPreparing(Choreography chor) {
	this.chor = chor;
    }

    public NewDeploymentPreparing(Choreography chor, RESTClientsRetriever servicesClientRetriever) {
	this.chor = chor;
	this.servicesClientRetriever = servicesClientRetriever;
    }

    public List<DeployableService> prepare() throws EnactmentException {
	logger.info("Request to configure nodes; creating services; setting up Chef; for chor " + chor.getId());
	submitConfigureTasks();
	waitConfigureTasks();
	retrievedConfiguredServices();
	checkStatus();
	logger.info("Nodes are configured to run chef-client on chor " + chor.getId());
	return configuredServices;
    }

    private void submitConfigureTasks() {
	List<DeployableServiceSpec> specs = chor.getRequestedChoreographySpec().getDeployableServiceSpecs();
	final int N = specs.size();
	executor = Executors.newFixedThreadPool(N);
	futures = new HashMap<DeployableServiceSpec, Future<DeployableService>>();
	for (DeployableServiceSpec choreographyServiceSpec : specs) {
	    ServicesManagerInvoker invoker = new ServicesManagerInvoker(choreographyServiceSpec);
	    Future<DeployableService> future = executor.submit(invoker);
	    futures.put(choreographyServiceSpec, future);
	}
    }

    private void waitConfigureTasks() {
	Concurrency.waitExecutor(executor, TIMEOUT);
    }

    private void retrievedConfiguredServices() {
	configuredServices = new ArrayList<DeployableService>();
	for (Entry<DeployableServiceSpec, Future<DeployableService>> entry : futures.entrySet()) {
	    try {
		DeployableService service = Concurrency.checkAndGetFromFuture(entry.getValue());
		if (service != null) {
		    configuredServices.add(service);
		} else {
		    logger.error("Future returned a null service for service " + entry.getKey().getName());
		}
	    } catch (ExecutionException e) {
		logger.error("Could not get service from future for service " + entry.getKey().getName() + " because "
			+ e.getMessage());
	    }
	}
    }

    private void checkStatus() throws EnactmentException {
	if (configuredServices == null || configuredServices.isEmpty()) {
	    logger.error("No services configured in chor " + chor.getId() + "!");
	    throw new EnactmentException();
	}
    }

    private class ServicesManagerInvoker implements Callable<DeployableService> {

	DeployableServiceSpec spec;

	public ServicesManagerInvoker(DeployableServiceSpec serviceSpec) {
	    this.spec = serviceSpec;
	}

	@Override
	public DeployableService call() throws Exception {
	    String owner = spec.getOwner();
	    ServicesManager servicesManager = servicesClientRetriever.getServicesClient(owner);
	    try {
		DeployableService deployedService = servicesManager.createService(spec);
		return deployedService;
	    } catch (ServiceNotCreatedException e) {
		logger.error("Service " + spec.getUuid() + " not created!");
		throw e;
	    }
	}
    }

}
