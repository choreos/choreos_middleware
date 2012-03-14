package eu.choreos.servicedeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceSpec {

	private String type; // bpel, jar, service in the wild // TODO enum
	private String codeUri;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeUri == null) ? 0 : codeUri.hashCode());
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
		ServiceSpec other = (ServiceSpec) obj;
		if (codeUri == null) {
			if (other.codeUri != null)
				return false;
		} else if (!codeUri.equals(other.codeUri))
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

	@Override
	public String toString() {
		return "ServiceSpec [type=" + type + ", codeUri=" + codeUri
				+ ", resourceImpact=" + resourceImpact + "]";
	}

}
