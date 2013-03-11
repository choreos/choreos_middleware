package org.ow2.choreos;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FlightTicketNumberRetriever {
	
	private final URL endpoint;
	
	public FlightTicketNumberRetriever(URL endpoint) {
		
		this.endpoint = endpoint;
	}

	public String getFlightTicketNumber() throws IllegalStateException {
		
		if (this.endpoint == null)
			throw new IllegalStateException();

		try {
			
	        SOAPMessage response = invokeService();
	        String flightTicketNumber = parseResponse(response);
	        return flightTicketNumber;
		} catch (Exception e) {
			throw new IllegalStateException();
		}
	}

	private SOAPMessage invokeService() throws SOAPException {
		
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
		return response;
	}

	private String parseResponse(SOAPMessage response) throws SOAPException {
		
		NodeList bodyChildren = response.getSOAPBody().getChildNodes();
		Node responseNode = null;
		for (int i=0; i<bodyChildren.getLength(); i++) {
			Node node = bodyChildren.item(i);
			if (node.getNodeName().contains("buyFlightResponse")) {
				responseNode = node;
			}
		}
		
		NodeList responseChildren = responseNode.getChildNodes();
		Node returnNode = null;
		for (int i=0; i<responseChildren.getLength(); i++) {
			Node node = responseChildren.item(i);
			if (node.getNodeName().contains("return") || node.getNodeName().contains("result")) {
				returnNode = node;
			}
		}
		
		String flightTicketNumber = returnNode.getTextContent();
		return flightTicketNumber;
	}

}
