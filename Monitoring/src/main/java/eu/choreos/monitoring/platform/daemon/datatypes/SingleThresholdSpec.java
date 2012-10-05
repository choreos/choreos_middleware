package eu.choreos.monitoring.platform.daemon.datatypes;

import eu.choreos.monitoring.platform.daemon.SingleThreshold;

public class SingleThresholdSpec extends AbstractThresholdSpec {

	public String limit_value;

	public SingleThresholdSpec() {

	}

	public String getLimit_value() {
		return limit_value;
	}

	public void setLimit_value(String limit_value) {
		this.limit_value = limit_value;
	}

	@Override
	public int getComparisonConstant() {
		if (comparison.contentEquals("MAX"))
			return SingleThreshold.MAX;
		if (comparison.contentEquals("MIN"))
			return SingleThreshold.MIN;
		if (comparison.contentEquals("EQUALS"))
			return SingleThreshold.EQUALS;
		if (comparison.contentEquals("DOWN"))
			return SingleThreshold.DOWN;

		return -1;

	}

	@Override
	public SingleThreshold toThreshold() {

		int comparisonConstant = getComparisonConstant();
		double value = Double.parseDouble(limit_value);

		return new SingleThreshold(this.attribute, comparisonConstant, value);
	}

}
