package eu.choreos.servicedeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Service {

	private String id; // Who will define the service ID?
	private String serviceType;
	private String uri;
	private String codeLocationURI;
	private String warFile;
	private ResourceImpact resourceImpact;

	public Service(ServiceSpec serviceSpec) {
		codeLocationURI = serviceSpec.getCodeUri();
		serviceType = serviceSpec.getType();
		resourceImpact = serviceSpec.getResourceImpact();

		// We assume that the codeLocationURI ends with "/warFileName.war
		String[] urlPieces = serviceSpec.getCodeUri().split("/");
		if (urlPieces[urlPieces.length - 1].contains(".war"))
			warFile = urlPieces[urlPieces.length - 1];

	}

	public Service() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getCodeLocationURI() {
		return codeLocationURI;
	}

	public void setCodeLocationURI(String codeLocationURI) {
		this.codeLocationURI = codeLocationURI;
	}

	public String getWarFile() {
		return warFile;
	}

	public void setWarFile(String warFile) {
		this.warFile = warFile;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Service other = (Service) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", uri=" + uri + "]";
	}

}
