package org.ow2.choreos.servicedeployer.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.npm.datamodel.ResourceImpact;

@XmlRootElement
public class ServiceSpec {

	protected String name;
	protected ServiceType type;
	protected ArtifactType artifactType;
	protected String codeUri;
	protected int port;
	protected String endpointName;
	protected String version;
	protected ResourceImpact resourceImpact;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public ArtifactType getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(ArtifactType artifactType) {
		this.artifactType = artifactType;
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
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFileName() {
		
		// We assume that the codeLocationURI ends with "/fileName.[war,jar]
		String fileName = "";
		String extension = this.artifactType.getExtension();
		String[] urlPieces = this.getCodeUri().split("/");
		if (urlPieces[urlPieces.length - 1].contains("." + extension)) {
			fileName = urlPieces[urlPieces.length - 1];
		}
		return fileName;
	}
	
	public int getPort() {
		
		int effectivePort = port;

		if (notDefinedPort()) {
			if (artifactType == ArtifactType.TOMCAT)
				effectivePort = 8080;
			if (artifactType == ArtifactType.EASY_ESB)
				effectivePort = 8180;
		}
		
		return effectivePort;
	}

	private boolean notDefinedPort() {
		return this.port == 0;
	}

	public void setPort(int port) {
		this.port = port;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeUri == null) ? 0 : codeUri.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return "ServiceSpec [name=" + name + ", type = " + type
				+ ", artifactType=" + artifactType + ", codeUri=" + codeUri
				+ ", port=" + port + ", endpointName=" + endpointName
				+ ", version=" + version + "]";
	}

}
