package eu.choreos.servicedeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceSpec {

	private String type; 
	private String codeUri;
	private String port;
	private String endpointName;
	private ResourceImpact resourceImpact;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeUri() {
		return codeUri;
	}

	public void setCodeUri(String codeUri) {
		this.codeUri = codeUri;
	}

	public ResourceImpact getResourceImpact() {
		return resourceImpact;
	}

	public void setResourceImpact(ResourceImpact resourceImpact) {
		this.resourceImpact = resourceImpact;
	}
	
	public String getEndpointName() {
		return endpointName;
	}

	public void setEndpointName(String name) {
		this.endpointName = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeUri == null) ? 0 : codeUri.hashCode());
		result = prime * result + ((endpointName == null) ? 0 : endpointName.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
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
		ServiceSpec other = (ServiceSpec) obj;
		if (codeUri == null) {
			if (other.codeUri != null)
				return false;
		} else if (!codeUri.equals(other.codeUri))
			return false;
		if (endpointName == null) {
			if (other.endpointName != null)
				return false;
		} else if (!endpointName.equals(other.endpointName))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceSpec [type=" + type + ", codeUri=" + codeUri + ", port="
				+ port + ", name=" + endpointName + "]";
	}

}
