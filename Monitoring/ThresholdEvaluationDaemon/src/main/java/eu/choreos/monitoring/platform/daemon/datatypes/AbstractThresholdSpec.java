package eu.choreos.monitoring.platform.daemon.datatypes;

import eu.choreos.monitoring.platform.daemon.AbstractThreshold;

public abstract class AbstractThresholdSpec {
	
	public String attribute;
	public String comparison;
	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getComparison() {
		return comparison;
	}

	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	abstract public int getComparisonConstant();
	
	abstract public AbstractThreshold toThreshold();

}
