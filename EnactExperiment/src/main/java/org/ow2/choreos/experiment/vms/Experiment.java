package org.ow2.choreos.experiment.vms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory.CloudProviderType;
import org.ow2.choreos.utils.LogConfigurator;

public class Experiment {

	private static final int[] Ns = new int[]{1, 2};
	private static final int EXECUTIONS_PER_N = 2; // how many times each N is executed
	private static final CloudProviderType CLOUD_PROVIDER_TYPE = CloudProviderType.AWS;

	private Logger logger = Logger.getLogger(Experiment.class);

	public void start() {
		
		for (int n: Ns) {
			
			Date now = new Date();
			logger.info("Experiment running at " + now);

			for (int i=0; i < EXECUTIONS_PER_N; i++) {
		
				logger.info("== Creating " + n + " VMs / Execution " + i + " ==");
				
				VMsCreator creator = new VMsCreator();
				
				long t0 = System.currentTimeMillis();
				List<Long> times = creator.createVMs(n, CLOUD_PROVIDER_TYPE);
				long tf = System.currentTimeMillis();
				long dt = (tf - t0) / 1000;
				logger.info("Done in " + dt + " seconds");
				
				ExecutionRecorder recorder = new ExecutionRecorder();
				try {
					recorder.record(times, n, i);
				} catch (IOException e) {
					logger.error("Could not record VMs times in the file!");
				}
		
				int fails = n - times.size();
				logger.info("Failures: " + fails);
				
				VMsDestroyer destroyer = new VMsDestroyer(CLOUD_PROVIDER_TYPE);
				destroyer.destroyAll();
			}
		}		
	}

	public static void main(String[] args) {
		
		LogConfigurator.configLog();
		Experiment experiment = new Experiment();
		experiment.start();
	}

}
