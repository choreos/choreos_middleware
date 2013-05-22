package org.ow2.choreos.experiment.enact;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

public class TravelChecker implements Runnable {
	
	private String travelEndpoint;
	int idx;
	boolean ok = false; // result: service properly accessed
	Report report;
	
	public TravelChecker(String travelWSDL, int idx, Report report) {
		this.travelEndpoint = travelWSDL;
		this.idx = idx;
		this.report = report;
	}
	
	@Override
	public void run() {

		System.out.println(Utils.getTimeStamp() + "Cheking choreography #" + idx);

		long t0 = System.currentTimeMillis();
		String result = buyTrip();
		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		
		ok = this.isTravelResponseOK(result);
		
		if (ok) {
			System.out.println(Utils.getTimeStamp() + "Choreography #" + idx
					+ " is working (invocation took " + duration
					+ " milliseconds)");
			report.addCheckTime(duration);
		} else {
			notWorking(duration);
		}
	}
	
	private boolean isTravelResponseOK(String travelResponse) {
		
		return (travelResponse.startsWith("33") && travelResponse.endsWith("22"));
	}
	
	private String buyTrip() {
		
		try {
			
			final String OPERATION_NAME = "buyTrip";
			
	        SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
	        SOAPConnection connection = sfc.createConnection();
	
	        MessageFactory mf = MessageFactory.newInstance();
	        SOAPMessage sm = mf.createMessage();
	
	        SOAPEnvelope envelope = sm.getSOAPPart().getEnvelope();
	        envelope.addNamespaceDeclaration("chor", "http://choreos.ow2.org/");
	        
	        SOAPHeader sh = sm.getSOAPHeader();
	        SOAPBody sb = sm.getSOAPBody();
	        sh.detachNode();
	        QName bodyName = new QName(OPERATION_NAME);
	        SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);
	        bodyElement.setPrefix("chor");
	        
	        URL endpoint = new URL(this.travelEndpoint);
	        SOAPMessage response = connection.call(sm, endpoint);
	        String result = response.getSOAPBody().getTextContent(); 
	        System.out.println("Result from #" + idx + ": " + result);
	        return result;

		} catch (Exception e) {
			return null;
		}
	}

	private void notWorking(long duration) {
		System.out.println(Utils.getTimeStamp() + "Choreography #" + idx
				+ " is not working (invocation took " + duration
				+ " milliseconds)");
	}
	
}
