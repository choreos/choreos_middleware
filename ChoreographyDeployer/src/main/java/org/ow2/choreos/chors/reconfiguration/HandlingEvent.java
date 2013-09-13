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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((eventData == null) ? 0 : eventData.hashCode());
	result = prime * result + ((node == null) ? 0 : node.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	HandlingEvent other = (HandlingEvent) obj;
	if (eventData == null) {
	    if (other.eventData != null)
		return false;
	} else if (!eventData.equals(other.eventData))
	    return false;
	if (node == null) {
	    if (other.node != null)
		return false;
	} else if (!node.equals(other.node))
	    return false;
	return true;
    }

}
