package org.ow2.choreos.chors.datamodel.xml;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.PackageType;

public class ChorXmlWriter {

	private Logger logger = Logger.getLogger(ChorXmlWriter.class);
	
	public String getChorSpecXML(ChorSpec chorSpec) throws JAXBException {

		return getXML(chorSpec, ChorSpec.class);
	}
	
	public String getChoreographyXML(Choreography chor) throws JAXBException {

		return getXML(chor, Choreography.class);
	}
	
	private String getXML(Object obj, Class<?> clazz) throws JAXBException {
		
        Marshaller marshaller;
        try {
        	marshaller = getMarshaller(clazz);
        } catch (JAXBException e) {
			logger.error("It should never happen");
			return null;
        }
		Writer writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}

	private Marshaller getMarshaller(Class<?> clazz) throws JAXBException {
	
			JAXBContext context = JAXBContext.newInstance(clazz);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			return marshaller;
	}
	
	public static void main(String[] args) throws JAXBException {
		
		ChorXmlWriter xmlWriter = new ChorXmlWriter();
		ModelsForTest models = new ModelsForTest(PackageType.COMMAND_LINE);
		String specXml = xmlWriter.getChorSpecXML(models.getChorSpec());
		String chorXml = xmlWriter.getChoreographyXML(models.getChoreography());
		System.out.println("== ChorSpec XML ==");
		System.out.println(specXml);
		System.out.println("== Choreography XML ==");
		System.out.println(chorXml);
		
	}

}
