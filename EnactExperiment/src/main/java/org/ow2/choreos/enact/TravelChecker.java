package org.ow2.choreos.enact;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class TravelChecker implements Runnable {
	
	private static final String EXPECTED_RESULT = "33--22";
	private String travelWSDL;
	int idx;
	boolean ok = false; // result: service properly accessed
	
	public TravelChecker(String travelWSDL, int idx) {
		this.travelWSDL = travelWSDL;
		this.idx = idx;
	}
	
	@Override
	public void run() {

		System.out.println("Cheking choreography #" + idx);

		WSClient client = getClient(travelWSDL);
		if (client == null) {
			notWorking();
			return;
		}
		
		long t0 = System.currentTimeMillis();
		Item response;
		try {
			response = client.request("buyTrip");
		} catch (InvalidOperationNameException e) {
			notWorking();
			return;
		} catch (FrameworkException e) {
			notWorking();
			return;
		}
		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		
		String result;
		try {
			result = response.getChild("return").getContent();
		} catch (NoSuchFieldException e) {
			notWorking();
			return;
		}
		
		ok = EXPECTED_RESULT.equals(result);
		
		if (ok) {
			System.out.println("Choreography #" + idx
					+ " is working (invocation took " + duration
					+ " milliseconds)");
		} else {
			notWorking();
		}
	}
	
	private void notWorking() {
		System.out.println("Choreography #" + idx + " is not working");
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

}
