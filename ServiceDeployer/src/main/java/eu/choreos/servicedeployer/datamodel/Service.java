package eu.choreos.servicedeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

import eu.choreos.servicedeployer.ServiceType;

@XmlRootElement
public class Service {

	private String id; // Who will define the service ID?
	private ServiceType serviceType;
	private String uri;
	private String codeLocationURI;
	private String warFile;
	private ResourceImpact resourceImpact;
	private int port;

	public Service(ServiceSpec serviceSpec) {
		codeLocationURI = serviceSpec.getCodeUri();
		try {
			serviceType = ServiceType.valueOf(serviceSpec.getType());
		} catch (IllegalArgumentException e) {
			serviceType = ServiceType.OTHER;
		}
		
		resourceImpact = serviceSpec.getResourceImpact();

		// We assume that the codeLocationURI ends with "/warFileName.war
		String[] urlPieces = serviceSpec.getCodeUri().split("/");
		if (urlPieces[urlPieces.length - 1].contains(".war")) {
			warFile = urlPieces[urlPieces.length - 1];
			id = warFile;
		}
	}

	public Service() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
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

	public int getPort() {
		int effectivePort = port;

		if (serviceType == ServiceType.WAR)
			effectivePort = 8080;
		
		return effectivePort;
	}

	public void setPort(int port) {
		this.port = port;
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
