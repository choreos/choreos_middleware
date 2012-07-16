package org.ow2.choreos.servicedeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.npm.datamodel.ResourceImpact;


@XmlRootElement
public class Service {

	private String id; // Who will define the service ID?
	private ServiceType type;
	private String uri;
	private String codeLocationURI;
	private String file; 
	private ResourceImpact resourceImpact;
	// port and name are provided if serviceTye == JAR
	private int port;
	private String endpointName; 
	private String hostname;

	public Service(ServiceSpec serviceSpec) {
		
		codeLocationURI = serviceSpec.getCodeUri();
		endpointName = serviceSpec.getEndpointName();

		port = serviceSpec.getPort();
		
		if (serviceSpec.getType() != null)
			type = serviceSpec.getType();
		else
			type = ServiceType.OTHER;
		
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
		
		if (type == ServiceType.JAR || type == ServiceType.WAR) {
			return type.toString().toLowerCase();
		} else if (type == ServiceType.PETALS) {
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

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType serviceType) {
		this.type = serviceType;
	}

	public String getUri() {
		
		// interesting note about the ending slash
		// http://www.searchenginejournal.com/to-slash-or-not-to-slash-thats-a-server-header-question/6763/
		
		if (hostname == null)
			throw new IllegalStateException("Sorry, I don't know the hostname yet");
		
		String uriContext;
		switch (type) {
			case WAR:
				uriContext = "service" + id + "Deploy/";
				break;
			case JAR:
				uriContext = endpointName + "/";
				break;
			case PETALS:
				uriContext = "petals/services/" + endpointName + "/";
				break;
			default:
				throw new IllegalStateException(
						"Sorry, I don't know how to provide an URL to a "
								+ type + " service.");
		}
		
		return "http://" + hostname + ":" + getPort() + "/" + uriContext;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	/**
	 * 
	 * @param host It can be the IP or the host name where the service was deployed
	 */
	public void setHost(String host) {
		this.hostname = host;
	}
	
	/**
	 * 
	 * @return It can be the IP or the host name where the service was deployed
	 */
	public String getHost() {
		return hostname;
	}

	public String getCodeLocationURI() {
		return codeLocationURI;
	}

	public void setCodeLocationURI(String codeLocationURI) {
		this.codeLocationURI = codeLocationURI;
	}

	public int getPort() {
		int effectivePort = port;

		if (type == ServiceType.WAR)
			effectivePort = 8080;
		
		if (type == ServiceType.PETALS)
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
		return endpointName;
	}

	public void setName(String name) {
		this.endpointName = name;
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
