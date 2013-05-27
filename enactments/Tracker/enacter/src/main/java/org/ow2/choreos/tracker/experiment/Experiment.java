package org.ow2.choreos.tracker.experiment;

import java.io.IOException;
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
	
	private static final int CHORS_QTY = 4;
	private static final int CHORS_SIZE = 5;
	private static final int RUN = 1;
	private static final String TRACKER_WAR_URL = "http://valinhos.ime.usp.br:54080/services/tracker.war";

	private static final int ENACTMENT_TIMEOUT = 20;
	private static final int VERIFY_TIMEOUT = 3;
	
	private static Logger logger = Logger.getLogger(Experiment.class);
	
	public static void main(String[] args) {

		LogConfigurator.configLog();
		Experiment experiment = new Experiment();
		experiment.run();
	}
	
	public void run() {
		
		logger.info("Running " + RUN);
		Report report = new Report(RUN, CHORS_QTY, CHORS_SIZE);
		
		long t0_total = System.nanoTime();
		
		ExecutorService executor = Executors.newFixedThreadPool(CHORS_QTY);
		List<RunnableEnacter> enacters = new ArrayList<RunnableEnacter>();
		long t0 = System.nanoTime();
		for (int i=0; i<CHORS_QTY; i++) {
			Enacter enacter = new Enacter(i);
			RunnableEnacter runnable = new RunnableEnacter(enacter, report);
			enacters.add(runnable);
			executor.submit(runnable);
		}
		
		Concurrency.waitExecutor(executor, ENACTMENT_TIMEOUT);
		long tf = System.nanoTime();
		report.setChorsEnactmentTotalTime(tf - t0);
		
		executor = Executors.newFixedThreadPool(CHORS_QTY);
		List<RunnableVerifier> verifiers = new ArrayList<RunnableVerifier>();
		t0 = System.nanoTime();
		for (RunnableEnacter enacter: enacters) {
			RunnableVerifier verifier = new RunnableVerifier(enacter.enacter, report);
			verifiers.add(verifier);
			executor.submit(verifier);
		}
		
		Concurrency.waitExecutor(executor, VERIFY_TIMEOUT);
		tf = System.nanoTime();
		report.setCheckTotalTime(tf - t0);
		
		long tf_total = System.nanoTime();
		long delta_total = tf_total - t0_total;
		report.setTotalTime(delta_total);
		
		int working = 0;
		for (RunnableVerifier verifier: verifiers) {
			if (verifier.ok) {
				working++;
			}
		}
		report.setChorsWorking(working);
		
		System.out.println(report);
		try {
			report.toFile();
		} catch (IOException e) {
			logger.error("Could not save the report.");
		}
	}

	class RunnableEnacter implements Runnable {

		Enacter enacter;
		Report report;
		boolean ok = false;
		
		RunnableEnacter(Enacter enacter, Report report) {
			this.enacter = enacter;
			this.report = report;
		}
		
		@Override
		public void run() {
			logger.info("Enacting Enacter#" + enacter.getId());
			try {
				long t0 = System.nanoTime();
				enacter.enact(TRACKER_WAR_URL, CHORS_SIZE);
				long tf = System.nanoTime();
				report.addChorEnactmentTime(tf-t0);
				ok = true;
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
		Report report;
		boolean ok = false;
		
		RunnableVerifier(Enacter enacter, Report report) {
			this.enacter = enacter;
			this.report = report;
		}

		
		@Override
		public void run() {
			logger.info("Verifying Enacter#" + enacter.getId());
			try {
				long t0 = System.nanoTime();
				ok = enacter.verifyAnswer();
				long tf = System.nanoTime();
				report.addCheckTime(tf-t0);
			} catch (MalformedURLException e) {
				logger.error("Ops, this problem should not occur with Enacter#" + enacter.getId());
				ok = false;
			}
			logger.info("Enacter#" + enacter.getId() + " ok: " + ok);
		}
		
	}
}
