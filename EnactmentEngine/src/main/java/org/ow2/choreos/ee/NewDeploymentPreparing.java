package org.ow2.choreos.ee;

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
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.ee.services.ServiceCreator;
import org.ow2.choreos.ee.services.ServiceCreatorFactory;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerFactory;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public class NewDeploymentPreparing {

    private static final String TASK_NAME = "CREATE_SERVICE";

    private String chorId;
    private List<DeployableServiceSpec> specs;
    private ExecutorService executor;
    private Map<DeployableServiceSpec, Future<DeployableService>> futures;
    private List<DeployableService> configuredServices;

    private int totalTimeout;

    private Logger logger = Logger.getLogger(NewDeploymentPreparing.class);

    public NewDeploymentPreparing(String chorId, List<DeployableServiceSpec> specs) {
	this.chorId = chorId;
	this.specs = specs;
	int timeout = TimeoutsAndTrials.get("CREATE_SERVICE_TIMEOUT");
	int trials = TimeoutsAndTrials.get("CREATE_SERVICE_TRIALS");
	int pause = TimeoutsAndTrials.get("CREATE_SERVICE_PAUSE");
	this.totalTimeout = (timeout + pause) * trials;
	this.totalTimeout += totalTimeout * 0.2;
    }

    public List<DeployableService> prepare() throws EnactmentException {
	if (specs.size() == 0)
	    return new ArrayList<DeployableService>();
	logger.info("Request to configure nodes; creating services; setting up Chef; for chor " + chorId);
	submitConfigureTasks();
	waitConfigureTasks();
	retrieveConfiguredServices();
	checkStatus();
	logger.info("Nodes are configured to run chef-client on chor " + chorId);
	return configuredServices;
    }

    private void submitConfigureTasks() {
	final int N = specs.size();
	executor = Executors.newFixedThreadPool(N);
	futures = new HashMap<DeployableServiceSpec, Future<DeployableService>>();
	for (DeployableServiceSpec choreographyServiceSpec : specs) {
	    CreateServiceInvoker invoker = new CreateServiceInvoker(choreographyServiceSpec);
	    Future<DeployableService> future = executor.submit(invoker);
	    futures.put(choreographyServiceSpec, future);
	}
    }

    private void waitConfigureTasks() {
	Concurrency.waitExecutor(executor, totalTimeout, "Could not properly configure all the services of chor "
		+ chorId);
    }

    private void retrieveConfiguredServices() {
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
	    logger.error("No services configured in chor " + chorId + "!");
	    throw new EnactmentException();
	}
    }

    private class CreateServiceInvoker implements Callable<DeployableService> {

	DeployableServiceSpec spec;

	public CreateServiceInvoker(DeployableServiceSpec serviceSpec) {
	    this.spec = serviceSpec;
	}

	@Override
	public DeployableService call() throws Exception {
	    CreateServiceTask task = new CreateServiceTask(spec);
	    InvokerFactory<DeployableService> factory = new InvokerFactory<DeployableService>();
	    Invoker<DeployableService> invoker = factory.geNewInvokerInstance(TASK_NAME, task);
	    return invoker.invoke();
	}
    }

    private class CreateServiceTask implements Callable<DeployableService> {

	DeployableServiceSpec spec;

	public CreateServiceTask(DeployableServiceSpec serviceSpec) {
	    this.spec = serviceSpec;
	}

	@Override
	public DeployableService call() throws Exception {
	    ServiceCreator serviceCreator = ServiceCreatorFactory.getNewInstance();
	    try {
		DeployableService deployedService = serviceCreator.createService(spec);
		return deployedService;
	    } catch (ServiceNotCreatedException e) {
		logger.error("Service " + spec.getName() + " not created!");
		throw e;
	    }
	}
    }

}
