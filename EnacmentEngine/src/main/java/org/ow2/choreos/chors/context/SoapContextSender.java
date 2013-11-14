/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class SoapContextSender implements ContextSender {

    private String parseNamespace(final String endpoint) throws XMLStreamException, IOException {

        final String wsdl = getWsdl(endpoint);
        final URL url = new URL(wsdl);
        final InputStreamReader streamReader = new InputStreamReader(url.openStream());
        final BufferedReader wsdlInputStream = new BufferedReader(streamReader);
        final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        final XMLEventReader reader = xmlInputFactory.createXMLEventReader(wsdlInputStream);

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

    @Override
    public void sendContext(String serviceEndpoint, String partnerRole, String partnerName,
            List<String> partnerEndpoints) throws ContextNotSentException {

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

            QName name = new QName("arg1");
            SOAPElement quotation2 = bodyElement.addChildElement(name);
            quotation2.addTextNode(partnerName);

            for (String partnerEndpoint : partnerEndpoints) {
                QName address = new QName("arg2");
                SOAPElement quotation3 = bodyElement.addChildElement(address);
                quotation3.addTextNode(partnerEndpoint);
            }
            if (serviceEndpoint.trim().endsWith("/"))
                serviceEndpoint = serviceEndpoint.substring(0, serviceEndpoint.length() - 1);

            URL endpoint = new URL(serviceEndpoint);
            // @SuppressWarnings("unused")
            // this.printSOAPMessage(sm);

            connection.call(sm, endpoint);

            // this.printSOAPMessage(msg);

        } catch (Exception e) {
            throw new ContextNotSentException(serviceEndpoint, partnerRole, partnerName, partnerEndpoints);
        }

    }

    @SuppressWarnings("unused")
    private void printSOAPMessage(SOAPMessage sm) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        Source sourceContent;
        StreamResult result = new StreamResult(System.out);
        try {
            transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            sourceContent = sm.getSOAPPart().getContent();
            transformer.transform(sourceContent, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

}
