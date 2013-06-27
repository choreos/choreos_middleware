package org.ow2.choreos.chors;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.utils.Concurrency;

public class NodesUpdater {

    private static final int TIMEOUT = 30; 

    private List<DeployableService> services;
    private String chorId;
    private ExecutorService executor;

    private Logger logger = Logger.getLogger(NodesUpdater.class);

    public NodesUpdater(List<DeployableService> services, String chorId) {
        this.services = services;
        this.chorId = chorId;
    }

    public void updateNodes() throws EnactmentException {
        logger.info("Going to update nodes of choreography " + chorId);
        submitUpdates();
        waitUpdates();
    }

    private void submitUpdates() {
        final int N = services.size();
        executor = Executors.newFixedThreadPool(N);
        for (DeployableService deployable : services) {
            String owner = deployable.getSpec().getOwner();
                for (CloudNode node: deployable.getSelectedNodes()) {
                    String nodeId = node.getId();
                    NodeUpdater updater = new NodeUpdater(nodeId, owner);
                    executor.submit(updater);
                }
        }
    }

    private void waitUpdates() {
        Concurrency.waitExecutor(executor, TIMEOUT);
    }

    private class NodeUpdater implements Runnable {

        String nodeId, owner;

        public NodeUpdater(String nodeId, String owner) {
            this.nodeId = nodeId;
            this.owner = owner;
        }

        @Override
        public void run() {
            NodePoolManager npm = RESTClientsRetriever.getNodesClient(owner);
            try {
                npm.updateNode(nodeId);
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
        }
    }

}
