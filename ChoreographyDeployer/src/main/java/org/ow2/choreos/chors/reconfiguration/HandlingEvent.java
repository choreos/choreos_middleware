package org.ow2.choreos.chors.reconfiguration;

public class HandlingEvent {

    String node;
    String eventData;

    /**
     * @param node
     * @param eventData
     */
    public HandlingEvent(String node, String eventData) {
	super();
	this.node = node;
	this.eventData = eventData;
    }

    public String getNode() {
	return node;
    }

    public void setNode(String node) {
	this.node = node;
    }

    public String getEventData() {
	return eventData;
    }

    public void setEventData(String eventData) {
	this.eventData = eventData;
    }

}
