package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class ComplexEventExceptionHandler {

    Logger logger = Logger.getLogger("reconfLogger");

    public void handle(ObjectMessage responseFromMonitoring) throws JMSException {
	ComplexEventException exceptionReceived = (ComplexEventException) responseFromMonitoring.getObject();
	logger.warn("Exception ClassName: " + exceptionReceived.getClassName());
    }

}
