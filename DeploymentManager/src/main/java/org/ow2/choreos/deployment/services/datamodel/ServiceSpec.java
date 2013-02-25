package org.ow2.choreos.deployment.services.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.deployment.nodes.datamodel.ResourceImpact;

@XmlRootElement
public class ServiceSpec {

	protected String name;
	protected ServiceType type;
	protected PackageType packageType;
	protected ResourceImpact resourceImpact;
	protected String version;
	protected int numberOfInstances = 1; // for default value, at least one
	
	// Deployable service attribs
	protected String packageUri; 
	protected int port;
	protected String endpointName;

	// Legacy service attribs
	protected List<String> serviceUris;
	
	
	public void addServiceUri(String uri) {
		if(serviceUris == null)
			serviceUris = new ArrayList<String>();
		serviceUris.add(uri);
	}
	
	public void addServiceUris(List<String> uris) {
		if(serviceUris == null)
			serviceUris = new ArrayList<String>();
		serviceUris.addAll(uris);
	}
	
	public void resetServiceUris() {
		if(serviceUris == null)
			serviceUris = new ArrayList<String>();
		else
			serviceUris.clear();
	}
	
	public int getNumberOfInstances() {
		if(packageType == PackageType.LEGACY)
			return serviceUris.size();
		else
			return numberOfInstances;
	}
	
	public void setNumberOfInstances(int number) throws IllegalArgumentException {
		if(packageType == PackageType.LEGACY)
			throw new IllegalArgumentException("Trying to set number of instances of a legacy service.");
		
		this.numberOfInstances = (number > 0) ? number : 1;
	}

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

	public PackageType getPackageType() {
		return packageType;
	}

	public void setPackageType(PackageType packageType) {
		this.packageType = packageType;
	}

	public String getPackageUri() {
		return packageUri;
	}

	public void setPackageUri(String uri) {
		this.packageUri = uri;
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
		String extension = this.packageType.getExtension();
		String[] urlPieces = this.getPackageUri().split("/");
		if (urlPieces[urlPieces.length - 1].contains("." + extension)) {
			fileName = urlPieces[urlPieces.length - 1];
		}
		return fileName;
	}
	
	public int getPort() {
		
		int effectivePort = port;

		if (notDefinedPort()) {
			if (packageType == PackageType.TOMCAT)
				effectivePort = 8080;
			if (packageType == PackageType.EASY_ESB)
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
		result = prime * result + ((packageUri == null) ? 0 : packageUri.hashCode());
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
		if (packageUri == null) {
			if (other.packageUri != null)
				return false;
		} else if (!packageUri.equals(other.packageUri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceSpec [name=" + name + ", type=" + type
				+ ", artifactType=" + packageType + ", codeUri=" + packageUri
				+ ", port=" + port + ", endpointName=" + endpointName
				+ ", # instances=" + numberOfInstances
				+ ", version=" + version + "]";
	}

	public List<String> getLegacyServicesUris() {
		return this.serviceUris;
	}

}
