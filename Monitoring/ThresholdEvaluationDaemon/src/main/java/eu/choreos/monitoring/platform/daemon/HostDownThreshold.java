package eu.choreos.monitoring.platform.daemon;

public class HostDownThreshold extends AbstractThreshold {
	
	public static final int DOWN = 1;

	public HostDownThreshold(String name, int comparison) {
		super(name, comparison);
	}

	@Override
	protected Boolean isWithinBoundaries(Double metricValue) {
		return lastMeasurement == 0; // 0 if not down, 1 otherwise
	}

	@Override
	protected String getComparisonAsString() {
		return "host down";
	}

	@Override
	public String toString() {
		boolean down = false;
		if(lastMeasurement == 1) down = true;
		return "Triggered: " + name + " " + getComparisonAsString() + " "
				+ "false" + ". Measured: " + down;
	}
}
