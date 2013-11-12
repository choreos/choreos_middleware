package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;
import it.cnr.isti.labsedc.glimpse.consumer.GlimpseAbstractConsumer;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;

class ChorGlimpseConsumer extends GlimpseAbstractConsumer {

    Logger logger = Logger.getLogger("reconfLogger");

    private ComplexEventExceptionHandler complexEventExceptionHandler;
    private ComplexEventResponseHandler complexEventResponseHandler;

    private Choreography chor;

    public ChorGlimpseConsumer(Properties settings, String plainTextRule, Choreography chor) {
	super(settings, plainTextRule);

	this.chor = chor;
	complexEventExceptionHandler = new ComplexEventExceptionHandler();
	complexEventResponseHandler = new ComplexEventResponseHandler();
    }

    @Override
    public void messageReceived(Message arg0) throws JMSException {
	try {
	    ObjectMessage responseFromMonitoring = (ObjectMessage) arg0;

	    logger.debug(">>>>>>> outer \n" + responseFromMonitoring.getObject());

	    if (responseFromMonitoring.getObject() instanceof ComplexEventException) {
		complexEventExceptionHandler.handle(responseFromMonitoring);
	    } else {

		ComplexEventResponse respObject = (ComplexEventResponse) responseFromMonitoring.getObject();

		logger.debug(">>>>>>> inner \n" + respObject);

		if (isQoSComplexEvent(respObject) && isServiceKnown(respObject) && isCloudNodeKnown(respObject)) {
		    complexEventResponseHandler.handle(respObject);
		}
	    }
	} catch (ClassCastException asd) {
	    logger.error("Error while casting message received. It is not a ObjectMessage instance");
	}
    }

    private boolean isServiceKnown(ComplexEventResponse respObject) {
	logger.debug("isServiceKnown() : Searching for: " + respObject.getResponseValue());

	if (respObject.getResponseValue().equals("all")) {
	    logger.debug("isServiceKnown() : Searching for: " + respObject.getResponseValue());
	    logger.debug("isServiceKnown() : Applicable for service scope!");
	    return true;
	}

	for (DeployableService service : chor.getDeployableServices()) {
	    logger.debug("isServiceKnown() : Found service = " + service + " for chor = " + chor.getId());
	    if (service.getSpec().getName().equals(respObject.getResponseValue())) {
		logger.debug("isServiceKnown() : Macthed: " + service + " = " + respObject.getResponseValue());
		return true;
	    }
	}
	logger.debug("isServiceKnown() : Nothing matches");
	return false;
    }

    private boolean isCloudNodeKnown(ComplexEventResponse respObject) {
	logger.debug("isCloudNodeKnown() : Searching for: " + respObject.getResponseKey());
	for (DeployableService service : chor.getDeployableServices()) {
	    for (CloudNode node : service.getSelectedNodes()) {
		logger.debug("isCloudNodeKnown() : Found node = " + node + " for service = " + service);
		if (node.getIp().equals(respObject.getResponseKey())) {
		    logger.debug("isCloudNodeKnown() : Matched: " + node + " = " + respObject.getResponseKey());
		    return true;
		}
	    }
	}
	logger.debug("isCloudNodeKnown() : Nothing matches");
	return false;
    }

    private boolean isQoSComplexEvent(ComplexEventResponse respObject) {
	boolean result = false;
	String ruleName = respObject.getRuleName();

	logger.debug("isQoSComplexEvent() : Rule name = " + ruleName);

	if (ruleName.equals("LowCpuUser"))
	    result = true;

	else if (ruleName.startsWith("HighResponseTime")) {
	    result = true;
	    respObject.setRuleName("HighResponseTime");
	}

	logger.debug("isQoSComplexEvent() : return value = " + result);

	return result;
    }
}