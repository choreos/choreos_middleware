package org.ow2.choreos.experiment.vms;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory.CloudProviderType;
import org.ow2.choreos.utils.LogConfigurator;

public class Experiment {

	private static final int N = 50;

	public static void main(String[] args) {

		LogConfigurator.configLog();
		Logger logger = Logger.getLogger(Experiment.class);

		Date now = new Date();
		logger.info("Experiment running at " + now);
		logger.info("Creating " + N + " VMs...");
		
		VMsCreator creator = new VMsCreator();
		List<Long> times = creator.createVMs(N, CloudProviderType.AWS);

		logger.info("Times (s):");
		for (Long time: times) {
			logger.info(time / 1000);
		}

		int fails = N - times.size();
		logger.info("Failures: " + fails);
	}

}
