package org.ow2.choreos.experiment.enact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;

/**
 * Before run the experiment:
 * Prepare the cloud environment
 * Set NODE_SELECTOR=ALWAYS_CREATE
 * Start NPMServer, ServiceDeployerServer, and EnactEngServer
 * 
 * The experiment will:
 * enact 100 services, one per node
 * 50 services are the travel agency service,
 * and other 50 are the airline service.
 * Every travel agency service depends on an airline service
 * The experiment will set every airline experiment as a dependence from some travel agency service
 * After the enactment, the experiment will invoke the 50 travel agency
 * and registry how many of them worked
 * 
 * @author leonardo
 *
 */
public class Experiment {

	public static final int CHORS_QTY = 1;  
	public static final int RUN = 1; // sequence ordinal
	public static final int SERVICES_PER_CHOR = 2;
	
	private Report report;
	private int chorsQty; // how many micro choreographies there will be
	
	public Experiment(Report report, int chorsQty) {
		this.report = report;
		this.report.chorsQuantity = chorsQty;
		this.chorsQty = chorsQty;
	}
	
	public void run() {
		
		long t0 = System.currentTimeMillis();
		List<Enacter> enacts = enact();
		long tf = System.currentTimeMillis();
		long delta = tf - t0;
		report.setTotalTimeWithoutCheck(delta);
		
		List<TravelChecker> checkers = verify(enacts);
		tf = System.currentTimeMillis();
		delta = tf - t0;
		System.out.println(Utils.getTimeStamp() + "Experiment completed in " + delta + " miliseconds");
		report.setTotalTime(delta);
		
		results(checkers);
		System.out.println("");
		System.out.println(report);
		try {
			report.toFile();
		} catch (IOException e) {
			System.out.println("Report not saved!");
			e.printStackTrace();
		}
	}

	private List<Enacter> enact() {
		
		System.out.println(Utils.getTimeStamp() + "Starting enactment");
		
		long t0 = System.currentTimeMillis();
		ChoreographySpec chorSpec = Spec.getSpec();
		List<Enacter> enacts = new ArrayList<Enacter>();
		List<Thread> trds = new ArrayList<Thread>();
		for (int i=0; i<this.chorsQty; i++) {
			Enacter enact = new Enacter(chorSpec, i, this.report);
			enacts.add(enact);
			Thread trd = new Thread(enact);
			trds.add(trd);
			trd.start();
		}
		
		waitThreads(trds);
		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		
		System.out.println(Utils.getTimeStamp() + "Enactment finished (" + duration + " milliseconds)");
		report.setChorsEnactmentTotalTime(duration);
		return enacts;
	}

	private List<TravelChecker> verify(List<Enacter> enacts) {
		
		System.out.println(Utils.getTimeStamp() + "Verifying enacted services");
	
		long t0 = System.currentTimeMillis();
		List<TravelChecker> checkers= new ArrayList<TravelChecker>();
		List<Thread> trds = new ArrayList<Thread>();
		for (int i=0; i<this.chorsQty; i++) {
			Enacter enact = enacts.get(i);
			if (enact.ok) {
				String travelWSDL = enact.travelWSDL;
				TravelChecker checker = new TravelChecker(travelWSDL, i, report);
				checkers.add(checker);
				Thread trd = new Thread(checker);
				trds.add(trd);
				trd.start();
			}
		}
		
		waitThreads(trds);
		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		report.setCheckTotalTime(duration);
		
		return checkers;
	}

	private void results(List<TravelChecker> checkers) {
		
		int ok = 0;
		for (TravelChecker chk: checkers) {
			if (chk.ok) {
				ok++;
			}
		}
		
		System.out.println(Utils.getTimeStamp() + "RESULT: " + ok + " of " + this.chorsQty + " working.");
		report.setChorsWorking(ok);

		boolean showExtra = false;
		if (showExtra) {
			System.out.println(Utils.getTimeStamp() + "Aditional info retrieved from ServiceDeployer:");
			try {
				for (String line: LogParser.getAdditionalInfo()) {
					System.out.println(Utils.getTimeStamp() + line);
				}
			} catch (IOException e) {
				System.out.println(Utils.getTimeStamp() + "Could not access service_deployer.log");
			}
		}
	}

	private void waitThreads(List<Thread> trds) {
		for (Thread t: trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println(Utils.getTimeStamp() + "Wait thread exception!");
			}
		}
	}
	
	public static void main(String[] args) {
		
		Experiment experiment = new Experiment(new Report(RUN), CHORS_QTY);
		experiment.run();
	}
}
