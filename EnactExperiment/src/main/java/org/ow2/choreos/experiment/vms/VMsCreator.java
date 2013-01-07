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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory.CloudProviderType;
import org.ow2.choreos.deployment.nodes.datamodel.Node;

public class VMsCreator { 

	private Logger logger = Logger.getLogger(VMsCreator.class);
	private AtomicInteger counter = new AtomicInteger();
	
	private static final int TIMEOUT = 4; // minutes

	/**
	 * Create N VMs using the given cloud provider
	 * @param N
	 * @param cp
	 * @return an ordered list with the times necessary to create each VM.
	 * If a VM is not created, tiphe size of the returned list is less then N.
	 */
	public List<Long> createVMs(int N, CloudProviderType cpType) {

		CloudProvider cp = CloudProviderFactory.getInstance(cpType);
		ExecutorService executor = Executors.newFixedThreadPool(N);
		List<Future<Long>> futures = new ArrayList<Future<Long>>();
		for (int i=0; i<N; i++) {
			Future<Long> future = executor.submit(new VMCreator(cp));
			futures.add(future);
			sleep(); // EC2 1 second rule
		}
		
		executor.shutdown();
		boolean status = false;
		try {
			status = executor.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error("Interrupted thread! Should not happen!", e);
		}
		
		if (!status) {
			logger.info("Executor status is False. Probably some VM was not created.");
		}
		
		List<Long> times = new ArrayList<Long>();
		for (Future<Long> f: futures) {
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

	private void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.error("Exception at sleeping! Shoult not happen!");
		}
	}

	private class VMCreator implements Callable<Long> {

		CloudProvider cp;
		
		public VMCreator(CloudProvider cp) {
			this.cp = cp;
		}
		
		/**
		 * Returns the time (milliseconds) to create the VM
		 * If the VM is not created, an exception is thrown
		 */
		@Override
		public Long call() throws Exception {

			long t0 = System.currentTimeMillis();
			Node node = cp.createNode(new Node());
			VMChecker ssh = new VMChecker();
			ssh.check(node.getIp());
			long tf = System.currentTimeMillis();					
			VMsCreator.this.logger.info(counter.incrementAndGet() + " VMs ready");
			return tf - t0;
		}
		
		
	}

}
