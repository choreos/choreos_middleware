package eu.choreos.monitoring.platform.daemon.datatypes;

import eu.choreos.monitoring.platform.daemon.Threshold;

public class ThresholdSpec {

	public String attribute;
	public String comparison;
	public String limit_value;

	public ThresholdSpec() {

	}

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

	public String getLimit_value() {
		return limit_value;
	}

	public void setLimit_value(String limit_value) {
		this.limit_value = limit_value;
	}

	public int getComparisonConstant() {
		if (comparison.contentEquals("MAX"))
			return Threshold.MAX;
		if (comparison.contentEquals("MIN"))
			return Threshold.MIN;
		if (comparison.contentEquals("EQUALS"))
			return Threshold.EQUALS;
		if (comparison.contentEquals("DOWN"))
			return Threshold.DOWN;

		return -1;

	}

	public Threshold toThreshold() {

		int comparisonConstant = getComparisonConstant();
		double value = Double.parseDouble(limit_value);

		return new Threshold(this.attribute, comparisonConstant, value);
	}

}
