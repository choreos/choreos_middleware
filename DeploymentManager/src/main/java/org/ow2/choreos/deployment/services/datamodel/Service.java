package org.ow2.choreos.deployment.services.datamodel;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Service {

	private String name; 
	private ServiceSpec spec;
	private String uri;
	private String hostname;
	private String ip;
	private String nodeId; 

	public Service(ServiceSpec serviceSpec) {
		
		this.spec = serviceSpec;
		if (this.spec.getArtifactType() == null) {
			this.spec.setArtifactType(ArtifactType.OTHER);
		}
		
		if (serviceSpec.getName() == null || serviceSpec.getName().isEmpty()) {
			name = getDefaultName();
		} else {
			name = serviceSpec.getName();
		}
		
		if (serviceSpec.artifactType == ArtifactType.LEGACY) {
			URIInfoRetriever info = new URIInfoRetriever(serviceSpec.getCodeUri());
			this.hostname = info.getHostname();
			this.ip = info.getIp();
			this.uri = serviceSpec.getCodeUri();
		}
	}
	
	private String getDefaultName() {
		
		String fileName = this.spec.getFileName();
		String defaultName = fileName.substring(0, fileName.length()-4);
		return defaultName;
	}

	public Service() {
		
	}

	public ServiceSpec getSpec() {
		return spec;
	}

	public void setSpec(ServiceSpec spec) {
		this.spec = spec;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * 
	 * @return the id of the node where the service was deployed
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * 
	 * @param nodeId the id of the node where the service was deployed
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getUri() {
		
		if (definedUri()) {
			return uri;
		} else {
			return getDefaultUri();
		}
	}

	private boolean definedUri() {
		return uri != null && !uri.isEmpty();
	}

	private String getDefaultUri() {
		
		// interesting note about the ending slash
		// http://www.searchenginejournal.com/to-slash-or-not-to-slash-thats-a-server-header-question/6763/
		
		if (hostname == null && ip == null)
			throw new IllegalStateException("Sorry, I don't know neither the hostname nor the IP yet");
		
		String uriContext;
		switch (this.spec.artifactType) {
			case TOMCAT:
				if (this.spec.getEndpointName() != null && !this.spec.getEndpointName().isEmpty())
					uriContext = this.spec.endpointName + "/";
				else
				        uriContext = name + "/";
				break;
			case COMMAND_LINE:
				uriContext = this.spec.endpointName + "/";
				break;
			case EASY_ESB:
				uriContext = "services/" + this.spec.endpointName + "ClientProxyEndpoint/";
				break;
			default:
				throw new IllegalStateException(
						"Sorry, I don't know how to provide an URL to a "
								+ this.spec.artifactType + " service.");
		}
		
		if (ip != null && !ip.isEmpty())
			return "http://" + ip + ":" + this.spec.getPort() + "/" + uriContext;
		else
			return "http://" + hostname + ":" + this.spec.getPort() + "/" + uriContext;
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		return "Service [name=" + name + ", uri=" + getUri() + "]";
	}

}
