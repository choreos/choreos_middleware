package org.ow2.choreos.experiment.vms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Concurrency;

public class VMsDestroyer {

    private static final int TIMEOUT = 2; // minutes

    private CloudProvider cp;
    private AtomicInteger counter = new AtomicInteger();

    private Logger logger = Logger.getLogger(VMsDestroyer.class);

    public VMsDestroyer(String cpType) {
        this.cp = CloudProviderFactory.getFactoryInstance().getCloudProviderInstance(cpType);
    }

    public void destroyAll() {

        logger.info("Destroying all the nodes...");

        List<CloudNode> nodes = this.cp.getNodes();
        final int N = nodes.size();
        ExecutorService executor = Executors.newFixedThreadPool(N);
        for (CloudNode node : nodes) {
            RunnableDestroyer destroyer = new RunnableDestroyer(node);
            executor.submit(destroyer);
            this.sleepForAws();
        }

        Concurrency.waitExecutor(executor, TIMEOUT, "pam!");
        System.out.println("");
    }

    private void sleepForAws() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error("Exceptio at sleeping, should never happen!");
        }
    }

    private class RunnableDestroyer implements Runnable {

        CloudNode node;

        RunnableDestroyer(CloudNode node) {
            this.node = node;
        }

        public void run() {
            try {
                cp.destroyNode(node.getId());
                System.out.print(counter.incrementAndGet() + " ");
            } catch (NodeNotDestroyed e) {
                logger.error("VM " + node.getId() + " not destroyed");
            } catch (NodeNotFoundException e) {
                logger.error("VM " + node.getId() + " does not exist");
            }
        }

    }

}
