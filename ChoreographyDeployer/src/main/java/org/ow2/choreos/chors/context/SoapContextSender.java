package org.ow2.choreos.chors.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

public class SoapContextSender implements ContextSender {

	@Override
	public void sendContext(String serviceEndpoint, String partnerRole,
			String partnerEndpoint) throws ContextNotSentException {

		try {

			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = sfc.createConnection();

			MessageFactory mf = MessageFactory.newInstance();
			SOAPMessage sm = mf.createMessage();

			SOAPEnvelope envelope = sm.getSOAPPart().getEnvelope();
			String namespace = parseNamespace(serviceEndpoint);
			envelope.addNamespaceDeclaration("pre", namespace);

			SOAPHeader sh = sm.getSOAPHeader();
			SOAPBody sb = sm.getSOAPBody();
			sh.detachNode();
			QName bodyName = new QName("setInvocationAddress");
			SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);
			bodyElement.setPrefix("pre");

			QName role = new QName("arg0");
			SOAPElement quotation1 = bodyElement.addChildElement(role);
			quotation1.addTextNode(partnerRole);

			QName address = new QName("arg1");
			SOAPElement quotation2 = bodyElement.addChildElement(address);
			quotation2.addTextNode(partnerEndpoint);

			if (serviceEndpoint.trim().endsWith("/"))
				serviceEndpoint = serviceEndpoint.substring(0,
						serviceEndpoint.length() - 1);

			URL endpoint = new URL(serviceEndpoint);
			SOAPMessage msg = connection.call(sm, endpoint);

		} catch (Exception e) {
			throw new ContextNotSentException(serviceEndpoint, partnerRole,
					partnerEndpoint);
		}
	}

	private String parseNamespace(final String endpoint)
			throws XMLStreamException, IOException {
		final String wsdl = getWsdl(endpoint);
		final URL url = new URL(wsdl);
		final InputStreamReader streamReader = new InputStreamReader(
				url.openStream());
		final BufferedReader wsdlInputStream = new BufferedReader(streamReader);
		final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		final XMLEventReader reader = xmlInputFactory
				.createXMLEventReader(wsdlInputStream);

		String elementName, namespace = "";
		XMLEvent event;
		StartElement element;

		while (reader.hasNext()) {
			event = reader.nextEvent();
			if (event.isStartElement()) {
				element = event.asStartElement();
				elementName = element.getName().getLocalPart();
				if ("import".equals(elementName)) {
					final QName qname = new QName("namespace"); // NOPMD
					namespace = element.getAttributeByName(qname).getValue();
					break;
				}
			}
		}

		reader.close();

		return namespace;
	}

	private String getWsdl(final String endpoint) {
		String slashLess;
		if (endpoint.endsWith("/")) {
			slashLess = endpoint.substring(0, endpoint.length() - 1);
		} else {
			slashLess = endpoint;
		}

		return slashLess + "?wsdl";
	}

}
