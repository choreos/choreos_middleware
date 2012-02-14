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

	
	@Override
	public String toString() {
		return "ServiceNode [id=" + id + ", correlationId=" + correlationId
				+ ", type=" + type + ", codeLocation=" + codeLocation
				+ ", resourceImpact=" + resourceImpact + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeLocation == null) ? 0 : codeLocation.hashCode());
		result = prime * result
				+ ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((resourceImpact == null) ? 0 : resourceImpact.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ServiceNode other = (ServiceNode) obj;
		if (codeLocation == null) {
			if (other.codeLocation != null)
				return false;
		} else if (!codeLocation.equals(other.codeLocation))
			return false;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resourceImpact == null) {
			if (other.resourceImpact != null)
				return false;
		} else if (!resourceImpact.equals(other.resourceImpact))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
		
	
}
