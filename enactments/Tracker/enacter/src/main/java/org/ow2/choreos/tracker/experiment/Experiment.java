package org.ow2.choreos.tracker.experiment;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.tracker.Enacter;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.LogConfigurator;

public class Experiment {
	
	private static final int CHORS_QTY = 2;
	private static final int CHORS_SIZE = 3;
	private static final int RUN = 1;
	private static final String TRACKER_WAR_URL = "http://valinhos.ime.usp.br:54080/services/tracker.war";

	private static final int ENACTMENT_TIMEOUT = 10;
	private static final int VERIFY_TIMEOUT = 1;
	
	private static Logger logger = Logger.getLogger(Experiment.class);
	
	public static void main(String[] args) {

		LogConfigurator.configLog();
		Experiment experiment = new Experiment();
		experiment.run();
	}
	
	public void run() {
		
		logger.info("Running " + RUN);
		
		ExecutorService executor = Executors.newFixedThreadPool(CHORS_QTY);
		List<RunnableEnacter> enacters = new ArrayList<RunnableEnacter>();
		for (int i=0; i<CHORS_QTY; i++) {
			Enacter enacter = new Enacter(i);
			RunnableEnacter runnable = new RunnableEnacter(enacter);
			enacters.add(runnable);
			executor.submit(runnable);
		}
		
		Concurrency.waitExecutor(executor, ENACTMENT_TIMEOUT);
		
		executor = Executors.newFixedThreadPool(CHORS_QTY);
		for (RunnableEnacter enacter: enacters) {
			RunnableVerifier verifier = new RunnableVerifier(enacter.enacter);
			executor.submit(verifier);
		}
		
		Concurrency.waitExecutor(executor, VERIFY_TIMEOUT);
	}

	class RunnableEnacter implements Runnable {

		Enacter enacter;
		boolean ok = true;
		
		RunnableEnacter(Enacter enacter) {
			this.enacter = enacter;
		}
		
		@Override
		public void run() {
			logger.info("Enacting Enacter#" + enacter.getId());
			try {
				enacter.enact(TRACKER_WAR_URL, CHORS_SIZE);
			} catch (MalformedURLException e) {
				failed();
			} catch (EnactmentException e) {
				failed();
			} catch (ChoreographyNotFoundException e) {
				failed();
			}
			logger.info("Enacter#" + enacter.getId() + " enacted.");			
		}
		
		private void failed() {
			logger.error("Enacter#" + enacter.getId() + " not enacted!");
			ok = false;
		}
	}
	
	class RunnableVerifier implements Runnable {

		Enacter enacter;
		boolean ok = true;
		
		RunnableVerifier(Enacter enacter) {
			this.enacter = enacter;
		}
		
		@Override
		public void run() {
			logger.info("Verifying Enacter#" + enacter.getId());
			try {
				ok = enacter.verifyAnswer();
				logger.info("Enacter#" + enacter.getId() + " ok: " + ok);
			} catch (MalformedURLException e) {
				logger.error("Ops, this problem should not occur with Enacter#" + enacter.getId());
				ok = false;
			}
			logger.info("Enacter#" + enacter.getId() + " verified");
		}
		
	}
}
