package org.ow2.choreos.experiment.vms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.Concurrency;

public class VMsCreator {

    private Logger logger = Logger.getLogger(VMsCreator.class);
    private AtomicInteger counter = new AtomicInteger();

    private static final int TIMEOUT = 60; // minutes

    /**
     * Create N VMs using the given cloud provider waitExecutor
     * 
     * @param N
     * @param cp
     * @return an ordered list with the times necessary to create each VM. If a
     *         VM is not created, the size of the returned list is less then N.
     */
    public List<Long> createVMs(int N, String cpType) {

        CloudProvider cp = CloudProviderFactory.getFactoryInstance().getCloudProviderInstance(cpType);
        ExecutorService executor = Executors.newFixedThreadPool(N);
        List<Future<Long>> futures = new ArrayList<Future<Long>>();
        for (int i = 0; i < N; i++) {
            Future<Long> future = executor.submit(new VMCreator(cp));
            futures.add(future);
        }

        Concurrency.waitExecutor(executor, TIMEOUT, "pam!");
        System.out.println("");

        List<Long> times = new ArrayList<Long>();
        for (Future<Long> f : futures) {
            Long time = this.checkFuture(f);
            if (time != null) {
                times.add(time);
            }
        }

        executor.shutdownNow();
        Collections.sort(times);
        return times;
    }

    private Long checkFuture(Future<Long> f) {
        Long time = null;
        try {
            if (f.isDone()) {
                time = f.get();
            } else {
                logger.warn("VM not ready yet");
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted thread! Should not happen!");
        } catch (ExecutionException e) {
            logger.info("VM not created (Execution exception): " + e.getCause().getMessage());
        } catch (CancellationException e) {
            logger.info("VM not created (CancellationException)");
        }
        return time;
    }

    private class VMCreator implements Callable<Long> {

        CloudProvider cp;

        public VMCreator(CloudProvider cp) {
            this.cp = cp;
        }

        /**
         * Returns the time (seconds) to create the VM This time encompasses the
         * time to the VM gets ready for use, which we verify with a SSH
         * connection If the VM is not created, an exception is thrown
         */
        @Override
        public Long call() throws Exception {

            long t0 = System.currentTimeMillis();
            CloudNode node = cp.createNode(new NodeSpec());
            VMChecker ssh = new VMChecker();
            ssh.check(node.getIp());
            long tf = System.currentTimeMillis();
            System.out.println(counter.incrementAndGet() + " " + node.getIp());
            return (tf - t0) / 1000;
        }

    }

}
