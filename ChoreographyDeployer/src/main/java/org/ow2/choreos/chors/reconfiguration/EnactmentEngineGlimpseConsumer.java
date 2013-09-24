package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;
import it.cnr.isti.labsedc.glimpse.consumer.GlimpseAbstractConsumer;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class EnactmentEngineGlimpseConsumer extends GlimpseAbstractConsumer {

    private ComplexEventExceptionHandler complexEventExceptionHandler;

    private Logger logger = Logger.getLogger(EnactmentEngineGlimpseConsumer.class);

    public EnactmentEngineGlimpseConsumer(Properties settings, String plainTextRule) {
	super(settings, plainTextRule);

	complexEventExceptionHandler = new ComplexEventExceptionHandler();
    }

    @Override
    public void messageReceived(Message arg0) throws JMSException {
	try {
	    ObjectMessage responseFromMonitoring = (ObjectMessage) arg0;
	    if (responseFromMonitoring.getObject() instanceof ComplexEventException) {
		complexEventExceptionHandler.handle(responseFromMonitoring);
	    } else {
		ComplexEventHandler eventHandler = new ComplexEventHandler();
		ComplexEventResponseHandler complexEventResponseHandler = new ComplexEventResponseHandler(eventHandler);
		complexEventResponseHandler.handle(responseFromMonitoring);
	    }
	} catch (ClassCastException asd) {
	    logger.error("Error while casting message received. It is not a ObjectMessage instance");
	}
    }

}