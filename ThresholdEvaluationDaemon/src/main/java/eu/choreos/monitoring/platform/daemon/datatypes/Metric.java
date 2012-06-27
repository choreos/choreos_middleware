package eu.choreos.monitoring.platform.daemon.datatypes;

public class Metric {

	private String name;
	private String value;
	private int tn;
	private int tmax;
	private int dmax;

	public Metric(String name, String value, int tn, int tmax, int dmax) {
		super();
		this.name = name;
		this.value = value;
		this.tn = tn;
		this.tmax = tmax;
		this.dmax = dmax;
	}

	public int getTn() {
		return tn;
	}

	public void setTn(int tn) {
		this.tn = tn;
	}

	public int getTmax() {
		return tmax;
	}

	public void setTmax(int tmax) {
		this.tmax = tmax;
	}

	public int getDmax() {
		return dmax;
	}

	public void setDmax(int dmax) {
		this.dmax = dmax;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
