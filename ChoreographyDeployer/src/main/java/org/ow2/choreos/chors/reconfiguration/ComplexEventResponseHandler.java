package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class ComplexEventResponseHandler {

    private static Map<String, HandlingEvent> handlingEvents = new HashMap<String, HandlingEvent>();
    private ComplexEventHandler handler;

    private Logger logger = Logger.getLogger(ComplexEventResponseHandler.class);
    private ComplexEventResponse response;
    private HandlingEvent event;

    public void handle(ObjectMessage responseFromMonitoring) throws JMSException {
	setUp(responseFromMonitoring);
	loadHandler();
	handles();
    }

    private void handles() {
	synchronized (handlingEvents) {
	    HandlingEvent ev = eventIsBeingHandling(event);
	    if (ev == null) {
		logger.debug("Handling event: host = " + event.getNode());
		lockEvent(event);
		handler.handleEvent(event);
		scheduleUnlockEvent(event);
	    } else {
		logger.debug("There is another event trigerred on host " + ev.getNode() + "being handled");
	    }
	}
    }

    private void setUp(ObjectMessage responseFromMonitoring) throws JMSException {
	response = (ComplexEventResponse) responseFromMonitoring.getObject();
	event = new HandlingEvent(response.getResponseValue(), response.getRuleName());
    }

    @SuppressWarnings("unchecked")
    private void loadHandler() {
	logger.debug("Loading handler...\n>>> " + "(" + event.getNode() + ", " + event.getEventData() + ")");
	Class<ComplexEventHandler> clazz;
	try {
	    clazz = (Class<ComplexEventHandler>) Class.forName(event.getEventData());
	    handler = clazz.newInstance();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
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
}
