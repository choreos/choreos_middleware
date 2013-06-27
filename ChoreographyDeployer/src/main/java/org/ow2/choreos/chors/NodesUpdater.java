package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.Concurrency;

public class NodesUpdater {

    private static final int TIMEOUT = 30; // chef-client may take a long time

    private List<DeployableService> services;
    private String chorId;
    private RESTClientsRetriever npmRetriever = new RESTClientsRetriever();
    private ExecutorService executor;
    private Map<ServiceInstance, NodeUpdater> updaters;
    private List<DeployableService> deployedServices;

    private Logger logger = Logger.getLogger(NodesUpdater.class);

    public NodesUpdater(List<DeployableService> services, String chorId) {
        this.services = services;
        this.chorId = chorId;
    }

    public NodesUpdater(List<DeployableService> services, String chorId, RESTClientsRetriever npmRetriever) {
        this.services = services;
        this.chorId = chorId;
        this.npmRetriever = npmRetriever;
    }

    public List<DeployableService> updateNodes() throws EnactmentException {
        logger.info("Going to run chef-client on nodes of choreography " + chorId);
        submitUpdates();
        waitUpdates();
        retrieveDeployedServices();
        return deployedServices;
    }

    private void submitUpdates() {
        final int N = services.size();
        executor = Executors.newFixedThreadPool(N);
        updaters = new HashMap<ServiceInstance, NodeUpdater>();
        for (DeployableService deployable : services) {
            String owner = deployable.getSpec().getOwner();
            List<ServiceInstance> instances = deployable.getServiceInstances();
            if (instances != null) {
                for (ServiceInstance instance : instances) {
                    String nodeId = instance.getNode().getId();
                    NodeUpdater updater = new NodeUpdater(nodeId, owner);
                    updaters.put(instance, updater);
                    executor.submit(updater);
                }
            } else {
                logger.warn("No services intances to choreography " + chorId + "!");
            }
        }
    }

    private void waitUpdates() {
        Concurrency.waitExecutor(executor, TIMEOUT);
    }

    private void retrieveDeployedServices() {
        Set<DeployableService> deployedServicesSet = new HashSet<DeployableService>();
        for (ServiceInstance instance : updaters.keySet()) {
            if (updaters.get(instance).ok) {
                String uuid = instance.getServiceSpec().getUuid();
                DeployableService service = findDeployableServiceBySpecUUID(uuid, services);
                deployedServicesSet.add(service);
            }
        }
        deployedServices = new ArrayList<DeployableService>(deployedServicesSet);
    }

    private DeployableService findDeployableServiceBySpecUUID(String specUUID, List<DeployableService> services) {
        for (DeployableService service : services) {
            if (specUUID.equals(service.getSpec().getUuid()))
                return service;
        }
        throw new NoSuchElementException();
    }

    private class NodeUpdater implements Runnable {

        String nodeId, owner;
        boolean ok = false;

        public NodeUpdater(String nodeId, String owner) {
            this.nodeId = nodeId;
            this.owner = owner;
        }

        @Override
        public void run() {
            NodePoolManager npm = npmRetriever.getNodesClient(owner);
            try {
                npm.updateNode(nodeId);
                ok = true;
            } catch (NodeNotUpdatedException e) {
                fail();
            } catch (NodeNotFoundException e) {
                fail();
            } catch (org.apache.cxf.interceptor.Fault e) {
                fail();
            }
        }

        private void fail() {
            logger.error("Bad response from updating node " + nodeId + "; maybe some service is not deployed");
            ok = false;
        }
    }

}
