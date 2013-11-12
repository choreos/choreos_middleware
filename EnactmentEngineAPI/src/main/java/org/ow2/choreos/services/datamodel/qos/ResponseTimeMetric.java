package org.ow2.choreos.services.datamodel.qos;

import java.io.Serializable;

public class ResponseTimeMetric implements Serializable {

    private float acceptablePercentage;
    private float maxDesiredResponseTime;

    public float getAcceptablePercentage() {
	return acceptablePercentage;
    }

    public float getMaxDesiredResponseTime() {
	return maxDesiredResponseTime;
    }

    public void setAcceptablePercentage(float acceptablePercentage) {
	this.acceptablePercentage = acceptablePercentage;
    }

    public void setMaxDesiredResponseTime(float maxDesiredResponseTime) {
	this.maxDesiredResponseTime = maxDesiredResponseTime;
    }

}
