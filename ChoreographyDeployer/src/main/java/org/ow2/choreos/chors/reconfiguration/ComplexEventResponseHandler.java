package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class ComplexEventResponseHandler {

    private static Map<String, HandlingEvent> handlingEvents = new HashMap<String, HandlingEvent>();
    private ComplexEventHandler handler;

    private Logger logger = Logger.getLogger(ComplexEventResponseHandler.class);

    public ComplexEventResponseHandler(ComplexEventHandler handler) {
	this.handler = handler;
    }

    public void handle(ObjectMessage responseFromMonitoring) throws JMSException {

	ComplexEventResponse resp = (ComplexEventResponse) responseFromMonitoring.getObject();

	// should be resp.getNetworkedSystemSource() but glimpse core do not
	// implements these functionality
	String responseHost = getNetworkedSystemSource(resp);
	String responseData = resp.getResponseValue();
	String ruleName = resp.getRuleName();
	String responseKey = resp.getResponseKey();

	logger.debug("Starting to handle response: " + resp + "(" + responseKey + ", " + responseData + ")");

	HandlingEvent event = new HandlingEvent(responseHost, responseData);

	synchronized (handlingEvents) {
	    HandlingEvent ev = eventIsBeingHandling(event);
	    if (ev == null) {
		logger.debug("Handling event: host = " + event.getNode());
		lockEvent(event);
		handler.handleEvent(responseHost, event);
		scheduleUnlockEvent(event);
	    } else {
		logger.debug("There is another event trigerred on host " + ev.getNode() + "being handled");
		return;
	    }
	}
    }

    private void scheduleUnlockEvent(HandlingEvent event) {
	handlingEvents.remove(event.getNode());
    }

    private void lockEvent(HandlingEvent event) {
	handlingEvents.put(event.getNode(), event);
    }

    private HandlingEvent eventIsBeingHandling(HandlingEvent event) {
	return handlingEvents.get(event.getNode());
    }

    private String getNetworkedSystemSource(ComplexEventResponse resp) {
	Matcher m = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}").matcher(resp.getResponseValue());
	if (!m.find())
	    logger.error("Error while extracting host ip from event data");
	return m.group(0);
    }

}
