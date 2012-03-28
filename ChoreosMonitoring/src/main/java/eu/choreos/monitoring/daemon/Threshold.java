package eu.choreos.monitoring.daemon;

import eu.choreos.monitoring.Gmetric;

public class Threshold {

    public static final int MIN = 1;
    public static final int EQUALS = 2;
    public static final int MAX = 3;

    private String name;
    private double value;
    private int comparison;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public double getValue() {
	return value;
    }

    public void setValue(double value) {
	this.value = value;
    }

    public int getComparison() {
	return comparison;
    }

    public void setComparison(int comparison) {
	this.comparison = comparison;
    }

    public Threshold(String name, int comparison, double value) {
	this.name = name;
	this.comparison = comparison;
	this.value = value;
    }

    public Boolean evaluate(Double metricValue) {
	switch (comparison) {
	case MIN:
	    return (metricValue <= value);
	case MAX:
	    return (metricValue >= value);
	case EQUALS:
	    return (metricValue == value);
	default:
	}

	return null;
    }

}
