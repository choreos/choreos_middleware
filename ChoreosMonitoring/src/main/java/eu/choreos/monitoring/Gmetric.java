package eu.choreos.monitoring;

public class Gmetric {

	private String name;
	private String value;
	
	public Gmetric(String name, String value) {
		super();
		this.name = name;
		this.value = value;
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
