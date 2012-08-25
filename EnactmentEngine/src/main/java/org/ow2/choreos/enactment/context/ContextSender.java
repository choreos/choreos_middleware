package org.ow2.choreos.enactment.context;

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

public class ContextSender {
	
	/**
	 * Calls setInvokationAddress operation on service in the <code>serviceEndpoint</code>.
	 * So, the service in <code>endpoint</code> will know that its
	 * partner with <code>partnerRole</code> is realized by <code>partnerEndpoint</code>.
	 * A context is transferred by successive calls of this operation.
	 * 
	 * @param endpoint
	 * @param context
	 * @return true if context was successfully set, false otherwise
	 */
	public boolean sendContext(String serviceEndpoint, 
			String partnerRole, String partnerEndpoint) {
		
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
			return false;
		}
        
		return true;
	}

}
