package eu.choreos.ServiceDeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "service")
public class ServiceNode {

	private String id;
	private String correlationId;
	private String type; //bpel, jar, service in the wild
	private String codeLocation;//URI
	
	private ResourceImpact resourceImpact;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeLocation() {
		return codeLocation;
	}

	public void setCodeLocation(String codeLocation) {
		this.codeLocation = codeLocation;
	}

	public ResourceImpact getResourceImpact() {
		return resourceImpact;
	}

	public void setResourceImpact(ResourceImpact resourceImpact) {
		this.resourceImpact = resourceImpact;
	}
		
	
}
