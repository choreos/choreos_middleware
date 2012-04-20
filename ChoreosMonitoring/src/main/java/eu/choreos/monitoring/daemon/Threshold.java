package eu.choreos.monitoring.daemon;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import eu.choreos.monitoring.Gmetric;
import eu.choreos.monitoring.utils.ShellHandler;

public class Threshold {

	public static final int MIN = 1;
	public static final int EQUALS = 2;
	public static final int MAX = 3;

	private String name;
	private double limitValue;
	private double lastMeasurement;
	private int comparison;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return limitValue;
	}

	public void setValue(double value) {
		this.limitValue = value;
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
		this.limitValue = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + comparison;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(limitValue);
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
		if (Double.doubleToLongBits(limitValue) != Double
				.doubleToLongBits(other.limitValue))
			return false;
		return true;
	}

	public Boolean wasSurpassed(Double metricValue) {
		return !isWithinBoundaries(metricValue);
	}

	private Boolean isWithinBoundaries(Double metricValue) {
		lastMeasurement = metricValue;
		switch (comparison) {
		case MIN:
			return (lastMeasurement >= limitValue);
		case MAX:
			return (lastMeasurement <= limitValue);
		case EQUALS:
			return (lastMeasurement == limitValue);
		default:
		}

		return null;
	}

	private String comparisonString() {
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
		return "Triggered: " + name + " " + comparisonString() + " "
				+ limitValue + ". Measured: " + lastMeasurement + " in "
				+ getHostName();
	}

	public String getHostName() {
		return ShellHandler.runLocalCommand(getScriptCommand()).replace("\n",
				"");
	}

	public String getScriptCommand() {

		String command;
		URL location = this.getClass().getClassLoader().getResource("hostname.sh");		
		
		File file = new File(location.getFile());
	
		if (file.exists()){
			file.setExecutable(true);
			command = "/bin/bash "
					+ (file.getAbsolutePath()).replace("%20", " ");
		}
		else{
			command = "/bin/bash hostname.sh";
		}

		return command;
	}

}