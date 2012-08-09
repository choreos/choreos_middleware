package org.ow2.choreos.enact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.client.EnactEngClient;
import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
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

	public static final int CHORS_QTY = 1; // how many micro choreographies there will be 
	public static final int SERVICES_PER_CHOR = 2;
	
	private static final String ENACTMENT_ENGINE_HOST = "http://localhost:9102/enactmentengine";
	private static final String AIRLINE = "airline";
	private static final String TRAVEL_AGENCY = "travelagency";	
	private static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/enact_test/airline-service.jar";
	private static final String TRAVEL_AGENCY_JAR = "http://valinhos.ime.usp.br:54080/enact_test/travel-agency-service.jar";	
	private static final int AIRLINE_PORT = 1234;
	private static final int TRAVEL_AGENCY_PORT = 1235;	
	
	public ChorSpec getSpec() {
		
		ChorSpec chorSpec = new ChorSpec(); 
		
		ChorService airline = new ChorService();
		airline.setName(AIRLINE);
		airline.setCodeUri(AIRLINE_JAR);
		airline.setEndpointName(AIRLINE);
		airline.setPort(AIRLINE_PORT);
		airline.setType(ServiceType.JAR);
		airline.getRoles().add(AIRLINE);
		chorSpec.addServiceSpec(airline);
		
		ChorService travel = new ChorService();
		travel.setName(TRAVEL_AGENCY);
		travel.setCodeUri(TRAVEL_AGENCY_JAR);
		travel.setEndpointName(TRAVEL_AGENCY);
		travel.setPort(TRAVEL_AGENCY_PORT);
		travel.setType(ServiceType.JAR);
		travel.getRoles().add(TRAVEL_AGENCY);
		ServiceDependence dep = new ServiceDependence(AIRLINE, AIRLINE);
		travel.getDependences().add(dep);
		chorSpec.addServiceSpec(travel);
		
		return chorSpec;
	}

	public void run() {
		
		System.out.println("Starting enactment");
		
		ChorSpec chorSpec = getSpec();
		List<Enactment> enacts = new ArrayList<Enactment>();
		List<Thread> trds = new ArrayList<Thread>();
		for (int i=0; i<CHORS_QTY; i++) {
			Enactment enact = new Enactment(chorSpec);
			enacts.add(enact);
			Thread trd = new Thread(enact);
			trds.add(trd);
			trd.start();
		}
		
		waitThreads(trds);
		
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

		waitThreads(trds);

		System.out.println("Experiment completed");
		
		int ok = 0;
		for (Checker chk: checkers) {
			if (chk.ok) {
				ok++;
			}
		}
		
		System.out.println(ok + " of " + CHORS_QTY + " working.");
	}
	
	private class Enactment implements Runnable {

		private ChorSpec chorSpec;
		private String travelWSDL = null; // result: deployed travel agency service WSDL 
		
		public Enactment(ChorSpec chorSpec) {
			this.chorSpec = chorSpec;
		}
		
		@Override
		public void run() {
			
			EnactmentEngine enacter = new EnactEngClient(ENACTMENT_ENGINE_HOST);
			String chorId = enacter.createChoreography(chorSpec);
			Choreography chor = null;
			try {
				chor = enacter.enact(chorId);
			} catch (EnactmentException e) {
				System.out.println("Enactment has failed");
				return;
			}
			Service travelService = chor.getDeployedServiceByName(TRAVEL_AGENCY);
			travelWSDL = travelService.getUri() + "?wsdl";
		}
	}
	
	private class Checker implements Runnable {

		private static final String EXPECTED_RESULT = "33--22";
		private String travelWSDL;
		private boolean ok = false; // result: service properly accessed
		
		public Checker(String travelWSDL) {
			this.travelWSDL = travelWSDL;
		}
		
		@Override
		public void run() {

			WSClient client = getClient(travelWSDL);
			if (client == null)
				return;
			
			Item response;
			try {
				response = client.request("buyTrip");
			} catch (InvalidOperationNameException e) {
				return;
			} catch (FrameworkException e) {
				return;
			}
			
			String codes;
			try {
				codes = response.getChild("return").getContent();
			} catch (NoSuchFieldException e) {
				return;
			}
			
			ok = EXPECTED_RESULT.equals(codes);
		}

	}

	private WSClient getClient(String travelWSDL) {

		try {
			WSClient client = new WSClient(travelWSDL);
			return client;
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
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		LogConfigurator.configLog();
		Experiment experiment = new Experiment();
		experiment.run();
	}
}
