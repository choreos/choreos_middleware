package org.ow2.choreos;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class MiddlewareExperiment {
	Logger logger = Logger.getLogger(MiddlewareExperiment.class);
	private static final String ENACTMENT_ENGINE_HOST = "http://localhost:9102/choreographydeployer";
	private ChoreographySpec chorSpec; // input
	private Choreography chor = null;

	public MiddlewareExperiment() {
		this.chorSpec = new MiddlewareExperimentSpec().getInitialChoreographySpec();
	}

	public void callEnactChor() {
		ChoreographyDeployer enacter = new ChorDeployerClient(
				ENACTMENT_ENGINE_HOST);
		
		String chorId = enacter.createChoreography(chorSpec);
		
		try {
			chor = enacter.enactChoreography(chorId);
		} catch (EnactmentException e) {
			e.printStackTrace();
			System.out.println("EnactmentException happened");
			System.exit(1);
		} catch (ChoreographyNotFoundException e) {
			System.out.println("ChoreographyNotFoundException");
			System.exit(1);
		}
	}
	
	public void exec() {
		callEnactChor();
		Timer timer = new Timer();
		MiddlewareExperimentTimerTask middlewareExperimentTimerTask = new MiddlewareExperimentTimerTask();
		
		timer.schedule(middlewareExperimentTimerTask, 0, 1000);
	}

	public static void main(String[] args) {
		MiddlewareExperiment m = new MiddlewareExperiment();
		m.exec();
	}

	private class MiddlewareExperimentTimerTask extends TimerTask {
		private ChoreographyService travel;

		private MiddlewareExperimentTimerTask() {
			travel = chor.getServiceByChorServiceSpecName(
					MiddlewareExperimentSpec.TRAVEL_AGENCY);
		}

		@Override
		public void run() {
			
			WSClient client = getTravelAgencyClient(travel);
			Item response = null;
			try {
				response = client.request("buyTrip");
			} catch (InvalidOperationNameException e1) {
				e1.printStackTrace();
			} catch (FrameworkException e1) {
				e1.printStackTrace();
			}
			try {
				String codes = response.getChild("return").getContent();
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			}
		}

		private WSClient getTravelAgencyClient(final ChoreographyService travel) {
			WSClient client = null;
			try {
				client = new WSClient(travel.getService().getUris().get(0)
						+ "?wsdl");
			} catch (WSDLException e1) {
				e1.printStackTrace();
			} catch (XmlException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (FrameworkException e1) {
				e1.printStackTrace();
			}
			return client;
		}
	}
}
