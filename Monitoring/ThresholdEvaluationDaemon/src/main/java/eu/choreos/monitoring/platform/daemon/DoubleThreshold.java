package eu.choreos.monitoring.platform.daemon;

public class DoubleThreshold extends AbstractThreshold {
	
	public static final int BETWEEN = 1;

	private double upperBound, lowerBound;
	
	public DoubleThreshold(String name, int comparison, double lowerBound, double upperBound) {
		super(name, comparison);
		
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	protected Boolean isWithinBoundaries(Double metricValue) {
		lastMeasurement = metricValue;
		switch (comparison) {
		case BETWEEN:
			return (lastMeasurement >= lowerBound) && (lastMeasurement <= upperBound);
		default:
		}
		return false;
	}

	@Override
	protected String getComparisonAsString() {
		switch (comparison) {
		case BETWEEN:
			return "between";
		default:
			return "undefined";
		}
	}

	@Override
	public String toString() {
		return "Triggered: " + name + " " + getComparisonAsString() + " "
				+ lowerBound + " and " + upperBound + ". Measured: " + lastMeasurement ;
	}
}
