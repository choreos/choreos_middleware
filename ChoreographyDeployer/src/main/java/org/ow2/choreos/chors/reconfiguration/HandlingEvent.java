package org.ow2.choreos.chors.reconfiguration;

public class HandlingEvent {

    String rule;
    String node;
    String service;

    public HandlingEvent(String rule, String node, String service) {
	super();
	this.rule = rule;
	this.node = node;
	this.service = service;
    }

    public String getRule() {
	return rule;
    }

    public void setRule(String rule) {
	this.rule = rule;
    }

    public String getNode() {
	return node;
    }

    public void setNode(String node) {
	this.node = node;
    }

    public String getService() {
	return service;
    }

    public void setEventData(String eventData) {
	this.service = eventData;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((node == null) ? 0 : node.hashCode());
	result = prime * result + ((rule == null) ? 0 : rule.hashCode());
	result = prime * result + ((service == null) ? 0 : service.hashCode());
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
	if (node == null) {
	    if (other.node != null)
		return false;
	} else if (!node.equals(other.node))
	    return false;
	if (rule == null) {
	    if (other.rule != null)
		return false;
	} else if (!rule.equals(other.rule))
	    return false;
	if (service == null) {
	    if (other.service != null)
		return false;
	} else if (!service.equals(other.service))
	    return false;
	return true;
    }

}
