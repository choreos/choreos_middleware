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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + comparison;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	long temp;
	temp = Double.doubleToLongBits(value);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	Threshold other = (Threshold) obj;
	if (comparison != other.comparison)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
	    return false;
	return true;
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
    
    private String comparisonString() {
	switch (comparison) {
	case MIN:
	    return "<=";
	case MAX:
	    return ">=";
	case EQUALS:
	    return "==";
	default:
	    return "undefined";
	}
    }
    
    public String toString() {
	return name + " " + comparisonString() + " " + value;
    }

}
