package eu.choreos.monitoring.platform.daemon;

public class SingleThreshold extends AbstractThreshold {
	
	public static final int MIN = 1;
	public static final int EQUALS = 2;
	public static final int MAX = 3;
	public static final int DOWN = 4;

	private double limitValue;

	public SingleThreshold(String name, int comparison, double value) {
		super(name, comparison);
		this.limitValue = value;
	}
	
	@Override
	protected Boolean isWithinBoundaries(Double metricValue) {
		lastMeasurement = metricValue;
		switch (comparison) {
		case MIN:
			return (lastMeasurement >= limitValue);
		case MAX:
			return (lastMeasurement <= limitValue);
		case EQUALS:
		case DOWN:
			return (lastMeasurement == limitValue);
		default:
		}
		return false;
	}

	@Override
	protected String getComparisonAsString() {
		switch (comparison) {
		case MIN:
			return ">=";
		case MAX:
			return "<=";
		case EQUALS:
			return "==";
		default:
			return "undefined";
		}
	}

	public String toString() {
		return "Triggered: " + name + " " + getComparisonAsString() + " "
				+ limitValue + ". Measured: " + lastMeasurement ;
	}
	
	@Override
	public String toEventRuleData() {
		return this.toString();
	}
}