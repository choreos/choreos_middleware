package org.ow2.choreos.chors.context;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

public class SoapContextSender implements ContextSender {
	
	@Override
	public void sendContext(String serviceEndpoint, 
			String partnerRole, String partnerEndpoint) throws ContextNotSentException {
		
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
	        QName bodyName = new QName("setInvocationAddress");
	        SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);
	        bodyElement.setPrefix("chor");
	        
	        QName role = new QName("arg0");
	        SOAPElement quotation1 = bodyElement.addChildElement(role);
	        quotation1.addTextNode(partnerRole);
	
	        QName address = new QName("arg1");
	        SOAPElement quotation2 = bodyElement.addChildElement(address);
	        quotation2.addTextNode(partnerEndpoint);
	
	        URL endpoint = new URL(serviceEndpoint);
	        connection.call(sm, endpoint);
		
		} catch (Exception e) {
			throw new ContextNotSentException(serviceEndpoint, partnerRole, partnerEndpoint);
		}
	}

}
