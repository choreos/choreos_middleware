package org.ow2.choreos.enact;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;

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

	public static final int CHORS_QTY = 1; // how many micro choreographies there will be 
	public static final int SERVICES_PER_CHOR = 2;
	
	private static final String AIRLINE = "airline";
	private static final String TRAVEL_AGENCY = "travelagency";	
	private static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/enact_test/airline-service.jar";
	private static final String TRAVEL_AGENCY_JAR = "http://valinhos.ime.usp.br:54080/enact_test/travel-agency-service.jar";	
	private static final int AIRLINE_PORT = 1234;
	private static final int TRAVEL_AGENCY_PORT = 1235;	
	
	public Choreography getSpec() {
		
		Choreography chorSpec = new Choreography(); 
		
		ChorService airline = new ChorService();
		airline.setName(AIRLINE);
		airline.setCodeUri(AIRLINE_JAR);
		airline.setEndpointName(AIRLINE);
		airline.setPort(AIRLINE_PORT);
		airline.setType(ServiceType.JAR);
		airline.getRoles().add(AIRLINE);
		chorSpec.addService(airline);
		
		ChorService travel = new ChorService();
		travel.setName(TRAVEL_AGENCY);
		travel.setCodeUri(TRAVEL_AGENCY_JAR);
		travel.setEndpointName(TRAVEL_AGENCY);
		travel.setPort(TRAVEL_AGENCY_PORT);
		travel.setType(ServiceType.JAR);
		travel.getRoles().add(TRAVEL_AGENCY);
		ServiceDependence dep = new ServiceDependence(AIRLINE, AIRLINE);
		travel.getDependences().add(dep);
		chorSpec.addService(travel);
		
		return chorSpec;
	}

	public void run() {
		
		System.out.println("Starting enactment");
		
		Choreography chorSpec = getSpec();
		List<Enactment> enacts = new ArrayList<Enactment>();
		List<Thread> trds = new ArrayList<Thread>();
		for (int i=0; i<CHORS_QTY; i++) {
			Enactment enact = new Enactment(chorSpec);
			enacts.add(enact);
			Thread trd = new Thread(enact);
			trds.add(trd);
			trd.start();
		}
		
		for (Thread trd: trds) {
			try {
				trd.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Enactment finished");

		System.out.println("Verifying enacted services");

		List<Checker> checkers= new ArrayList<Checker>();
		trds = new ArrayList<Thread>();
		for (int i=0; i<CHORS_QTY; i++) {
			String travelWSDL = enacts.get(i).travelWSDL;
			Checker checker = new Checker(travelWSDL);
			checkers.add(checker);
			Thread trd = new Thread(checker);
			trds.add(trd);
			trd.start();
		}

		for (Thread trd: trds) {
			try {
				trd.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Experiment completed");
		
		int ok = 0;
		for (Checker chk: checkers) {
			if (chk.ok) {
				ok++;
			}
		}
		
		System.out.println(ok + " of " + CHORS_QTY + " working.");
	}
	
	private static class Enactment implements Runnable {

		private Choreography chor;
		private String travelWSDL; // result: deployed travel agency service WSDL 
		
		public Enactment(Choreography chor) {
			this.chor = chor;
		}
		
		@Override
		public void run() {
			// TODO
		}
	}
	
	private static class Checker implements Runnable {

		private String travelWSDL;
		private boolean ok; // result: service properly accessed
		
		public Checker(String travelWSDL) {
			this.travelWSDL = travelWSDL;
		}
		
		@Override
		public void run() {
			// TODO
		}
	}
	
	public static void main(String[] args) {
		
		Experiment experiment = new Experiment();
		experiment.run();
	}
}
