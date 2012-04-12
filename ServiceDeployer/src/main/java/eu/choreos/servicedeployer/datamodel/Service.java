package eu.choreos.servicedeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Service {

	private String id; // Who will define the service ID?
	private ServiceType serviceType;
	private String uri;
	private String codeLocationURI;
	private String file; 
	private ResourceImpact resourceImpact;
	// port and name are provided if serviceTye == JAR
	private int port;
	private String name; 
	private String hostname;

	public Service(ServiceSpec serviceSpec) {
		
		codeLocationURI = serviceSpec.getCodeUri();
		name = serviceSpec.getName();

		try {
			port = Integer.parseInt(serviceSpec.getPort());
		}
		catch (NumberFormatException e) {
			port = 0;
		}
		
		try {
			serviceType = ServiceType.valueOf(serviceSpec.getType());
		} catch (IllegalArgumentException e) {
			serviceType = ServiceType.OTHER;
		}
		
		resourceImpact = serviceSpec.getResourceImpact();

		// We assume that the codeLocationURI ends with "/fileName.[war,jar]
		String extension = getExtension();
		String[] urlPieces = serviceSpec.getCodeUri().split("/");
		if (urlPieces[urlPieces.length - 1].contains("." + extension)) {
			file = urlPieces[urlPieces.length - 1];
			id = file.substring(0, file.length()-4);
		}
	}

	public Service() {
	}

	/**
	 * 
	 * @return deploy file extension
	 */
	public String getExtension() {
		
		if (serviceType == ServiceType.JAR || serviceType == ServiceType.WAR) {
			return serviceType.toString().toLowerCase();
		} else if (serviceType == ServiceType.PETALS) {
			return "zip";
		} else {
			return null;
		}
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
		
		if (hostname == null)
			throw new IllegalStateException("Sorry, I don't know the hostname yet");
		
		String uriContext;
		switch (serviceType) {
			case WAR:
				uriContext = "/service" + id + "Deploy/";
				break;
			case JAR:
				uriContext = name + "/";
				break;
			case PETALS:
				String suffix = "";
				if (codeLocationURI.contains("provide"))
					suffix = "PortService";
				uriContext = "petals/services/" + id + suffix;
				break;
			default:
				throw new IllegalStateException(
						"Sorry, I don't know how to provide an URL to a "
								+ serviceType + " service.");
		}
		
		return "http://" + hostname + ":" + getPort() + "/" + uriContext;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public void setHostname(String hostname) {
		this.hostname = hostname;
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
		
		if (serviceType == ServiceType.PETALS)
			effectivePort = 8084;
		
		return effectivePort;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public ResourceImpact getResourceImpact() {
		return resourceImpact;
	}

	public void setResourceImpact(ResourceImpact resourceImpact) {
		this.resourceImpact = resourceImpact;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
