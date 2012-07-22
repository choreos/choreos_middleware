package eu.choreos.monitoring.platform.daemon.datatypes;

import eu.choreos.monitoring.platform.daemon.AbstractThreshold;
import eu.choreos.monitoring.platform.daemon.DoubleThreshold;

public class DoubleThresholdSpec extends AbstractThresholdSpec {
	
	public String limit_inf;
	public String limit_sup;

	@Override
	public int getComparisonConstant() {
		if (comparison.contentEquals("BETWEEN"))
			return DoubleThreshold.BETWEEN;
		return -1;
	}

	@Override
	public AbstractThreshold toThreshold() {
		
		int comparisonConstant = getComparisonConstant();
		double inf = Double.parseDouble(limit_inf);
		double sup = Double.parseDouble(limit_sup);

		return new DoubleThreshold(this.attribute, comparisonConstant, inf, sup);

	}

}
