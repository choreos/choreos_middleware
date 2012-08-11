package org.ow2.choreos.enact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.client.EnactEngClient;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

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

	public static final int CHORS_QTY = 3; // how many micro choreographies there will be 
	public static final int SERVICES_PER_CHOR = 2;
	
	private Logger logger = Logger.getLogger(Experiment.class);
	
	private static final String ENACTMENT_ENGINE_HOST = "http://localhost:9102/enactmentengine";
	private static final String TRAVEL_AGENCY = "travelagency";	
	
	public void run() {
		
		logger.info("Starting enactment");
		
		long t0 = System.currentTimeMillis();
		ChorSpec chorSpec = Spec.getSpec();
		List<Enactment> enacts = new ArrayList<Enactment>();
		List<Thread> trds = new ArrayList<Thread>();
		for (int i=0; i<CHORS_QTY; i++) {
			Enactment enact = new Enactment(chorSpec, i);
			enacts.add(enact);
			Thread trd = new Thread(enact);
			trds.add(trd);
			trd.start();
		}
		
		waitThreads(trds);
		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		
		logger.info("Enactment finished (" + duration + " milliseconds)");

		logger.info("Verifying enacted services");

		List<Checker> checkers= new ArrayList<Checker>();
		trds = new ArrayList<Thread>();
		for (int i=0; i<CHORS_QTY; i++) {
			Enactment enact = enacts.get(i);
			if (enact.ok) {
				String travelWSDL = enact.travelWSDL;
				Checker checker = new Checker(travelWSDL, i);
				checkers.add(checker);
				Thread trd = new Thread(checker);
				trds.add(trd);
				trd.start();
			}
		}

		waitThreads(trds);

		logger.info("Experiment completed");
		
		int ok = 0;
		for (Checker chk: checkers) {
			if (chk.ok) {
				ok++;
			}
		}
		
		logger.info("RESULT: " + ok + " of " + CHORS_QTY + " working.");
		
		logger.info("Aditional info retrieved from ServiceDeployer:");
		try {
			for (String line: LogParser.getAdditionalInfo()) {
				logger.info(line);
			}
		} catch (IOException e) {
			logger.error("Could not access service_deployer.log");
		}
	}
	
	private class Enactment implements Runnable {

		ChorSpec chorSpec;
		int idx;
		String travelWSDL = null; // result: deployed travel agency service WSDL
		long duration; // result: enactment duration in milliseconds
		boolean ok = true;
		
		public Enactment(ChorSpec chorSpec, int idx) {
			this.chorSpec = chorSpec;
			this.idx = idx;
		}
		
		@Override
		public void run() {
			
			logger.info("Enacting choreography #" + idx);
			
			long t0 = System.currentTimeMillis();
			EnactmentEngine enacter = new EnactEngClient(ENACTMENT_ENGINE_HOST);
			String chorId = enacter.createChoreography(chorSpec);
			Choreography chor = null;
			try {
				chor = enacter.enact(chorId);
			} catch (EnactmentException e) {
				logger.error("Enactment #" + idx + " has failed");
				ok = false;
				return;
			}
			long tf = System.currentTimeMillis();
			duration = tf - t0;
			Service travelService = chor.getDeployedServiceByName(TRAVEL_AGENCY);
			travelWSDL = travelService.getUri() + "?wsdl";
			
			logger.info("Choreography #" + idx + " enacted in " + duration + " miliseconds");
			StringBuilder chorMachinesMessage = new StringBuilder("Machines used by choreography #" + idx + ":");
			for (String mch: getMachinesFromChor(chor)) {
				chorMachinesMessage.append(mch + "; ");
			}
			logger.info(chorMachinesMessage.toString());
		}
		
		private List<String> getMachinesFromChor(Choreography chor) {
			
			List<String> machines = new ArrayList<String>();
			for (Service svc: chor.getDeployedServices()) {
				String machine = svc.getIp() + " (" + svc.getNodeId() + ")";
				machines.add(machine);
			}
			return machines;
		}
	}
	
	private class Checker implements Runnable {

		private static final String EXPECTED_RESULT = "33--22";
		private String travelWSDL;
		int idx;
		private boolean ok = false; // result: service properly accessed
		
		public Checker(String travelWSDL, int idx) {
			this.travelWSDL = travelWSDL;
			this.idx = idx;
		}
		
		@Override
		public void run() {

			logger.info("Cheking choreography #" + idx);

			WSClient client = getClient(travelWSDL);
			if (client == null) {
				notWorking();
				return;
			}
			
			long t0 = System.currentTimeMillis();
			Item response;
			try {
				synchronized(Experiment.class) {
					response = client.request("buyTrip");
				}
			} catch (InvalidOperationNameException e) {
				notWorking();
				return;
			} catch (FrameworkException e) {
				notWorking();
				return;
			}
			long tf = System.currentTimeMillis();
			long duration = tf - t0;
			
			String answer;
			try {
				answer = response.getChild("return").getContent();
			} catch (NoSuchFieldException e) {
				notWorking();
				return;
			}
			
			ok = EXPECTED_RESULT.equals(answer);
			
			if (ok) {
				logger.info("Choreography #" + idx
						+ " is working (invocation took " + duration
						+ " milliseconds)");
			} else {
				notWorking();
			}
		}
		
		private void notWorking() {
			logger.info("Choreography #" + idx + " is not working");
		}

	}

	private WSClient getClient(String travelWSDL) {

		try {
			synchronized(Experiment.class) {
				WSClient client = new WSClient(travelWSDL);
				return client;
			}
		} catch (WSDLException e) {
			return null;
		} catch (XmlException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (FrameworkException e) {
			return null;
		}
	}
	
	private void waitThreads(List<Thread> trds) {
		for (Thread t: trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				logger.error("Wait thread exception!", e);
			}
		}
	}
	
	public static void main(String[] args) {
		
		LogConfigurator.configLog();
		Experiment experiment = new Experiment();
		experiment.run();
	}
}
