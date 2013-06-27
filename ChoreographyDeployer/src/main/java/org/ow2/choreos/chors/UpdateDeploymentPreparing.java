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
import org.ow2.choreos.services.ServiceNotModifiedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.utils.Concurrency;

public class UpdateDeploymentPreparing {

    private Choreography chor;

    private Logger logger = Logger.getLogger(UpdateDeploymentPreparing.class);

    public UpdateDeploymentPreparing(Choreography chor) {
        this.chor = chor;
    }

    public List<DeployableService> prepare() throws EnactmentException {
        logger.info("Request to configure nodes; creating services; setting up Chef");
        List<DeployableService> choreographyServices = updateAndDeployServices(chor);
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
        return choreographyServices;
    }

    private List<DeployableService> configureNodesForNewServices(List<DeployableServiceSpec> list) {

        final int TIMEOUT = 30; // it may encompasses bootstrap time
        final int N = list.size();

        ExecutorService executor = Executors.newFixedThreadPool(N);
        Map<DeployableServiceSpec, Future<DeployableService>> futures = new HashMap<DeployableServiceSpec, Future<DeployableService>>();
        for (DeployableServiceSpec choreographyServiceSpec : list) {
            ServicesManagerInvoker invoker = new ServicesManagerInvoker(choreographyServiceSpec);
            Future<DeployableService> future = executor.submit(invoker);
            futures.put(choreographyServiceSpec, future);
        }

        Concurrency.waitExecutor(executor, TIMEOUT);

        List<DeployableService> services = new ArrayList<DeployableService>();
        for (Entry<DeployableServiceSpec, Future<DeployableService>> entry : futures.entrySet()) {
            try {
                DeployableService service = Concurrency.checkAndGetFromFuture(entry.getValue());
                if (service != null) {
                    services.add(service);
                } else {
                    logger.error("Future returned a null service for service " + entry.getKey().getName());
                }
            } catch (ExecutionException e) {
                logger.error("Could not get service from future for service " + entry.getKey().getName() + " because "
                        + e.getMessage());
            }
        }

        return services;
    }

    private class ServicesManagerInvoker implements Callable<DeployableService> {

        DeployableServiceSpec serviceSpec;

        public ServicesManagerInvoker(DeployableServiceSpec serviceSpec) {
            this.serviceSpec = serviceSpec;
        }

        @Override
        public DeployableService call() throws Exception {

            String owner = serviceSpec.getOwner();
            RESTClientsRetriever retriever = new RESTClientsRetriever();
            ServicesManager servicesManager = retriever.getServicesClient(owner);

            try {
                DeployableService deployedService = servicesManager.createService(serviceSpec);
                return deployedService;
            } catch (ServiceNotCreatedException e) {
                logger.error("Maybe DeploymentManager is off");
                throw e;
            }
        }

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

    private List<DeployableService> doUpdate(Choreography chor, Map<String, DeployableServiceSpec> toUpdate,
            List<DeployableServiceSpec> toCreate) {

        List<DeployableService> b = new ArrayList<DeployableService>();
        if (toUpdate.size() > 0)
            b = updateExistingServices(chor, toUpdate);

        if (toCreate.size() > 0) {
            List<DeployableService> a = configureNodesForNewServices(toCreate);
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

        Concurrency.waitExecutor(executor, TIMEOUT);

        List<DeployableService> services = new ArrayList<DeployableService>();
        for (Entry<DeployableServiceSpec, Future<DeployableService>> entry : futures.entrySet()) {
            try {
                DeployableService service = Concurrency.checkAndGetFromFuture(entry.getValue());

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
}
