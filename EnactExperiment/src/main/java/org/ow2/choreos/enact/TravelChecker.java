package org.ow2.choreos.enact;

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
	
	private static final String EXPECTED_RESULT = "33--22";
	private String travelEndpoint;
	int idx;
	boolean ok = false; // result: service properly accessed
	
	public TravelChecker(String travelWSDL, int idx) {
		this.travelEndpoint = travelWSDL;
		this.idx = idx;
	}
	
	@Override
	public void run() {

		System.out.println(Utils.getTimeStamp() + "Cheking choreography #" + idx);

		long t0 = System.currentTimeMillis();
		String result = buyTrip();
		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		
		ok = EXPECTED_RESULT.equals(result);
		
		if (ok) {
			System.out.println(Utils.getTimeStamp() + "Choreography #" + idx
					+ " is working (invocation took " + duration
					+ " milliseconds)");
		} else {
			notWorking();
		}
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
	        return result;

		} catch (Exception e) {
			return null;
		}
	}

	private void notWorking() {
		System.out.println(Utils.getTimeStamp() + "Choreography #" + idx + " is not working");
	}
	
}
