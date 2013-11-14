package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServiceNotModifiedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.utils.Concurrency;

public class UpdateDeploymentPreparing {

    private String chorId;
    private Map<DeployableService, DeployableServiceSpec> toUpdate;
    private List<DeployableService> configuredServices;

    private Logger logger = Logger.getLogger(UpdateDeploymentPreparing.class);

    public UpdateDeploymentPreparing(String chorId, Map<DeployableService, DeployableServiceSpec> toUpdate) {
        this.chorId = chorId;
        this.toUpdate = toUpdate;
    }

    public List<DeployableService> prepare() throws EnactmentException {
        if (toUpdate.size() == 0)
            return new ArrayList<DeployableService>();        
        logger.info("Request to configure nodes; creating services; setting up Chef");
        updateExistingServices();
        checkStatus();
        return configuredServices;
    }
    
    private void checkStatus() throws EnactmentException {
        if (configuredServices == null || configuredServices.isEmpty()) {
            logger.error("No services configured in chor " + chorId + "!");
            throw new EnactmentException();
        }
    }    

    private void updateExistingServices() {
        
        final int TIMEOUT = 10;
        final int N = toUpdate.size();
        ExecutorService executor = Executors.newFixedThreadPool(N);
        List<Future<DeployableService>> futures = new ArrayList<Future<DeployableService>>();
        for (Entry<DeployableService, DeployableServiceSpec> entry: toUpdate.entrySet()) {
            DeployableService service = entry.getKey();
            DeployableServiceSpec spec = entry.getValue();
            String uuid = service.getUUID();
            logger.debug("Requesting update of " + spec);
            ServiceUpdateInvoker invoker = new ServiceUpdateInvoker(uuid, spec);
            Future<DeployableService> future = executor.submit(invoker);
            futures.add(future);
        }

        Concurrency.waitExecutor(executor, TIMEOUT, "Could not properly update all the services of chor" + chorId);

        configuredServices = new ArrayList<DeployableService>();
        for (Future<DeployableService> future : futures) {
            try {
                DeployableService service = Concurrency.checkAndGetFromFuture(future);
                if (service != null) {
                    configuredServices.add(service);
                }
            } catch (Exception e) {
                logger.error("Could not get service from future: " + e.getMessage());
            }
        }

    }

    private class ServiceUpdateInvoker implements Callable<DeployableService> {

        private DeployableServiceSpec serviceSpec;
        private String serviceUUID;

        public ServiceUpdateInvoker(String serviceUUID, DeployableServiceSpec serviceSpec) {
            this.serviceSpec = serviceSpec;
            this.serviceUUID = serviceUUID;
        }

        @Override
        public DeployableService call() throws UnhandledModificationException, ServiceNotFoundException, ServiceNotModifiedException {

            String owner = serviceSpec.getOwner();
            ServicesManager servicesManager = RESTClientsRetriever.getServicesClient(owner);

            try {
                DeployableService service = servicesManager.updateService(serviceUUID, serviceSpec);
                logger.debug("Service updated: " + service);
                return service;
            } catch (UnhandledModificationException e) {
                logger.error(e.getMessage());
                throw e;
            } catch (ServiceNotFoundException e) {
                logger.error(e.getMessage());
                throw e;
            } catch (ServiceNotModifiedException e) {
                logger.error(e.getMessage());
                throw e;
            }
        }
    }

}
