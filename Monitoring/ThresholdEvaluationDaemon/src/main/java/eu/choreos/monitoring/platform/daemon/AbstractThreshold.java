package eu.choreos.monitoring.platform.daemon;

public abstract class AbstractThreshold {
	
	protected String name;
	protected double lastMeasurement;
	protected int comparison;
	
	public AbstractThreshold(String name, int comparison) {
			this.name = name;
			this.comparison = comparison;
	}

	public String getName() {
		return name;
	}
	
	public Boolean wasSurpassed(Double metricValue) {
		return !isWithinBoundaries(metricValue);
	}
	
	abstract protected Boolean isWithinBoundaries(Double metricValue);
	
	abstract protected String getComparisonAsString();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + comparison;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp = name.hashCode() + comparison;
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
		AbstractThreshold other = (AbstractThreshold) obj;
		if (comparison != other.comparison)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		return true;
	}
	
	public String toString() {
		return "No message defined!";
	}

	public String toEventRuleData() {
		return null;
	}
}
