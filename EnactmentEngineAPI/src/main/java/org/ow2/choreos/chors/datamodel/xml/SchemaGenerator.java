/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.datamodel.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.w3c.dom.Document;

public class SchemaGenerator {

    private Logger logger = Logger.getLogger(SchemaGenerator.class);

    public void printChorSpecSchema() {
        try {
            this.printSchema(ChoreographySpec.class);
        } catch (JAXBException e) {
            logger.error("It should never happen", e);
        } catch (IOException e) {
            logger.error("It should never happen", e);
        } catch (TransformerException e) {
            logger.error("It should never happen", e);
        }
    }

    public void printChoreographySchema() {
        try {
            this.printSchema(Choreography.class);
        } catch (JAXBException e) {
            logger.error("It should never happen", e);
        } catch (IOException e) {
            logger.error("It should never happen", e);
        } catch (TransformerException e) {
            logger.error("It should never happen", e);
        }
    }

    // based on
    // http://arthur.gonigberg.com/2010/04/26/jaxb-generating-schema-from-object-model/
    // and
    // http://stackoverflow.com/questions/2325388/java-shortest-way-to-pretty-print-to-stdout-a-org-w3c-dom-document
    private void printSchema(Class<?> clazz) throws JAXBException, IOException, TransformerException {

        // grab the context
        JAXBContext context = JAXBContext.newInstance(clazz);

        final List<DOMResult> results = new ArrayList<DOMResult>();

        // generate the schema
        context.generateSchema(
        // need to define a SchemaOutputResolver to store to
        new SchemaOutputResolver() {
            @Override
            public Result createOutput(String ns, String file) throws IOException {
                // save the schema to the list
                DOMResult result = new DOMResult();
                result.setSystemId(file);
                results.add(result);
                return result;
            }
        });

        // output schema via System.out
        DOMResult domResult = results.get(0);
        Document doc = (Document) domResult.getNode();
        printDocument(doc, System.out);
        
//        OutputFormat format = new OutputFormat(doc);
//        format.setIndenting(true);
//        StringWriter writer = new StringWriter();
//        XMLSerializer serializer = new XMLSerializer(writer, format);
//        serializer.serialize(doc);
//        return writer.toString();
    }
    
    private static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(doc), 
             new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    public static void main(String[] args) {
        SchemaGenerator gen = new SchemaGenerator();
        System.out.println("ChorSpec XSD:");
        gen.printChorSpecSchema();
        System.out.println("\nChoreography XSD:");
        gen.printChoreographySchema();
    }
}
