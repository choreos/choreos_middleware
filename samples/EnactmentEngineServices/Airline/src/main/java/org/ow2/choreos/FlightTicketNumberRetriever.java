package org.ow2.choreos;

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

import org.w3c.dom.Node;

public class FlightTicketNumberRetriever {
	
	private final URL endpoint;
	
	public FlightTicketNumberRetriever(URL endpoint) {
		
		this.endpoint = endpoint;
	}

	public String getFlightTicketNumber() {
		
		if (this.endpoint == null) {
			
			throw new IllegalStateException("Null endpoint");
			
		}
		
		try {
			
	        SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
	        SOAPConnection connection = sfc.createConnection();
	
	        MessageFactory mf = MessageFactory.newInstance();
	        SOAPMessage sm = mf.createMessage();
	
	        SOAPEnvelope envelope = sm.getSOAPPart().getEnvelope();
	        envelope.addNamespaceDeclaration("chor", "http://choreos.ow2.org/");
	        
	        SOAPHeader sh = sm.getSOAPHeader();
	        SOAPBody sb = sm.getSOAPBody();
	        sh.detachNode();
	        QName bodyName = new QName("buyFlight");
	        SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);
	        bodyElement.setPrefix("chor");
	        
	        SOAPMessage response = connection.call(sm, this.endpoint);
	        
	        Node returnNode = response.getSOAPBody().getChildNodes().item(0).getChildNodes().item(0);
	        String flightTicketNumber = returnNode.getTextContent();
	        
	        return flightTicketNumber;
		
		} catch (Exception e) {
			throw new  IllegalStateException("Something went wrong!", e);
		}
	}
}
