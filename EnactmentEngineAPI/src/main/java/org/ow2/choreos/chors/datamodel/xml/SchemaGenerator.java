/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.datamodel.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.w3c.dom.Document;

public class SchemaGenerator {

    private Logger logger = Logger.getLogger(SchemaGenerator.class);

    public String generateChorSpecSchema() {

        try {
            return this.generateSchema(ChoreographySpec.class);
        } catch (JAXBException e) {
            logger.error("It should never happen");
            return null;
        } catch (IOException e) {
            logger.error("It should never happen");
            return null;
        }
    }

    public String generateChoreographySchema() {

        try {
            return this.generateSchema(Choreography.class);
        } catch (JAXBException e) {
            logger.error("It should never happen");
            return null;
        } catch (IOException e) {
            logger.error("It should never happen");
            return null;
        }
    }

    // based on
    // http://arthur.gonigberg.com/2010/04/26/jaxb-generating-schema-from-object-model/
    private String generateSchema(Class<?> clazz) throws JAXBException, IOException {

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
        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        StringWriter writer = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(writer, format);
        serializer.serialize(doc);
        return writer.toString();
    }

    public static void main(String[] args) {

        SchemaGenerator gen = new SchemaGenerator();
        System.out.println("ChorSpec XSD:");
        System.out.println(gen.generateChorSpecSchema());
        System.out.println("\nChoreography XSD:");
        System.out.println(gen.generateChoreographySchema());
    }
}
