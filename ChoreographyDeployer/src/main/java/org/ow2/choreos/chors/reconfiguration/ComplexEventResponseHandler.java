package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class ComplexEventResponseHandler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static List<HandlingEvent> handlingEvents = new ArrayList<HandlingEvent>();
    private ComplexEventHandler handler;

    private Logger logger = Logger.getLogger(ComplexEventResponseHandler.class);
    private ComplexEventResponse response;
    private HandlingEvent event;

    public void handle(ObjectMessage responseFromMonitoring) throws JMSException {
	setUp(responseFromMonitoring);
	synchronized (handlingEvents) {
	    if (eventIsBeingHandling(event)) {
		logger.debug("There is another event trigerred on host " + event.getNode() + "being handled");
		return;
	    }
	}
	loadHandler();
	handles();
    }

    private void handles() {
	synchronized (handlingEvents) {
	    logger.debug("Handling event: host = " + event.getNode());
	    lockEvent(event);
	    handler.handleEvent(event);
	    scheduleUnlockEvent(event);
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
	    clazz = (Class<ComplexEventHandler>) Class.forName("org.ow2.choreos.chors.reconfiguration.events."
		    + event.getEventData());
	    handler = clazz.newInstance();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
    }

    private void scheduleUnlockEvent(final HandlingEvent event) {
	final Runnable remove = new Runnable() {

	    @Override
	    public void run() {
		handlingEvents.remove(event.getNode());
	    }
	};
	final ScheduledFuture<?> removeHandle = scheduler.scheduleAtFixedRate(remove, 30, 100, TimeUnit.SECONDS);
	scheduler.schedule(new Runnable() {
	    public void run() {
		removeHandle.cancel(true);
	    }
	}, 40, TimeUnit.SECONDS);
    }

    private void lockEvent(HandlingEvent event) {
	handlingEvents.add(event);
    }

    private boolean eventIsBeingHandling(HandlingEvent event) {
	return handlingEvents.contains(event);
    }
}
