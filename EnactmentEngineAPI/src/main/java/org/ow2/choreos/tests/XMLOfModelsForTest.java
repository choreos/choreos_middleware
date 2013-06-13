package org.ow2.choreos.tests;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.datamodel.xml.ChorXmlWriter;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;

public class XMLOfModelsForTest {
    
    private ChoreographySpec chorSpec;
    private Choreography chor;
    
    public XMLOfModelsForTest() {
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	this.chorSpec = models.getChorSpec();
	this.chor = models.getChoreography();
    }
    
    private Logger logger = Logger.getLogger(XMLOfModelsForTest.class);

    private String getChorSpecXML() {
	ChorXmlWriter writer = new ChorXmlWriter();
	try {
	    return writer.getChorSpecXML(this.chorSpec);
	} catch (JAXBException e) {
	    logger.error("It should never happen");
	    return null;
	}
    }

    private String getChoreographyXML() {
	ChorXmlWriter writer = new ChorXmlWriter();
	try {
	    return writer.getChoreographyXML(this.chor);
	} catch (JAXBException e) {
	    logger.error("It should never happen");
	    return null;
	}
    }

    /**
     * Prints ChorSpec and Choreography XML representations
     * 
     */
    public static void main(String[] args) throws JAXBException, IOException {
	XMLOfModelsForTest xmlModels = new XMLOfModelsForTest();
	System.out.println("ChorSpec XML representation:");
	System.out.println(xmlModels.getChorSpecXML());
	System.out.println("\nChoreography XML representation:");
	System.out.println(xmlModels.getChoreographyXML());
    }

}
