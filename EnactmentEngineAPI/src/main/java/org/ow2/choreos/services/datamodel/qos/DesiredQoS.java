package org.ow2.choreos.services.datamodel.qos;

import java.io.Serializable;

public class DesiredQoS implements Serializable {

    private static final long serialVersionUID = 5137885233674831781L;

    private ResourceLimits resourceLimits = new ResourceLimits();

    private ResponseTimeMetric responseTime;

    public void setResponseTimeMetric(ResponseTimeMetric responseTime) {
	this.responseTime = responseTime;
    }

    public ResponseTimeMetric getResponseTimeMetric() {
	return responseTime;
    }

    public ResourceLimits getResourceLimits() {
	return resourceLimits;
    }
}
