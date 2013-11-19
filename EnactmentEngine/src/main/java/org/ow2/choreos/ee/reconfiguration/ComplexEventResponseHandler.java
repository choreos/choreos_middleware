package org.ow2.choreos.ee.reconfiguration;

import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

public class ComplexEventResponseHandler {

    private ComplexEventHandler handler;

    private Logger logger = Logger.getLogger("reconfLogger");
    private ComplexEventResponse response;
    private HandlingEvent event;

    public void handle(ComplexEventResponse responseFromMonitoring) throws JMSException {
	setUp(responseFromMonitoring);
	loadHandler();
	handles();
    }

    private void handles() {
	logger.debug("Handling event\n\n>>>\n " + event.getRule() + "on host = " + event.getNode());
	handler.handleEvent(event);
	try {
	    Thread.sleep(1000 * 60);
	    logger.debug("Sleeping one minute");
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	logger.debug("Event handled!\n\n>>>");
    }

    private void setUp(ComplexEventResponse responseFromMonitoring) throws JMSException {
	response = responseFromMonitoring;
	String ruleMatched = response.getRuleName();
	String node = response.getResponseKey();
	String serviceName = response.getResponseValue();
	event = new HandlingEvent(ruleMatched, node, serviceName);
    }

    @SuppressWarnings("unchecked")
    private void loadHandler() {
	logger.debug("Loading handler...\n>>> " + "(" + event.getRule() + ", " + event.getNode() + ", "
		+ event.getService() + ")");
	Class<ComplexEventHandler> clazz;
	try {
	    clazz = (Class<ComplexEventHandler>) Class.forName("org.ow2.choreos.chors.reconfiguration.events."
		    + event.getRule());
	    handler = clazz.newInstance();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
    }

}
