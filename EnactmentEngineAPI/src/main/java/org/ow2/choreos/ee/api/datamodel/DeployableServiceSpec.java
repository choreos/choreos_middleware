package org.ow2.choreos.ee.api.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeployableServiceSpec extends ServiceSpec {
	
	protected ResourceImpact resourceImpact; 
	protected String version; 
	
	protected String packageUri; 
	protected int port;
	protected String endpointName;
	private int numberOfInstances = 1;
	
	public DeployableServiceSpec() {
		super(null, null);
	}
	
	/**
	 * @param serviceType
	 * @param packageType
	 * @param resourceImpact
	 * @param version
	 * @param packageUri
	 * @param port Sets the port for run a JAR package
	 * @param endpointName
	 * @param numberOfInstances
	 */
	public DeployableServiceSpec(ServiceType serviceType,
			PackageType packageType, ResourceImpact resourceImpact,
			String version, String packageUri, int port, String endpointName,
			int numberOfInstances) {
		super(serviceType, packageType);
		this.resourceImpact = resourceImpact;
		this.version = version;
		this.packageUri = packageUri;
		this.port = port;
		this.endpointName = endpointName;
		this.numberOfInstances = numberOfInstances;
	}

	/**
	 * @param serviceType
	 * @param packageType
	 * @param resourceImpact
	 * @param version
	 * @param packageUri
	 * @param endpointName
	 * @param numberOfInstances
	 */
	public DeployableServiceSpec(ServiceType serviceType,
			PackageType packageType, ResourceImpact resourceImpact,
			String version, String packageUri, String endpointName,
			int numberOfInstances) {
		super(serviceType, packageType);
		this.resourceImpact = resourceImpact;
		this.version = version;
		this.packageUri = packageUri;
		this.endpointName = endpointName;
		this.numberOfInstances = numberOfInstances;
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
	public int getNumberOfInstances() {
		return numberOfInstances;
	}

	@Override
	public void setNumberOfInstances(int numberOfInstances) {
		if(numberOfInstances > 0) 
			this.numberOfInstances = numberOfInstances;
		else
			this.numberOfInstances = 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((resourceImpact == null) ? 0 : resourceImpact.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((packageUri == null) ? 0 : packageUri.hashCode());
		result = prime * result + ((endpointName == null) ? 0 : endpointName.hashCode());
		result = prime * result + (port);
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
		if (!super.equals(obj))
			return false;
		
		DeployableServiceSpec other = (DeployableServiceSpec) obj;
		
		if (resourceImpact == null) {
			if (other.resourceImpact != null)
				return false;
		} else if (!resourceImpact.equals(other.resourceImpact))
			return false;
		
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		
		if (packageUri == null) {
			if (other.packageUri != null)
				return false;
		} else if (!packageUri.equals(other.packageUri))
			return false;
		
		if (endpointName == null) {
			if (other.endpointName != null)
				return false;
		} else if (!endpointName.equals(other.endpointName))
			return false;		
		
		if (! (port == other.port) )
			return false;
		
		if (! (numberOfInstances == other.numberOfInstances) )
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "ServiceSpec [type=" + serviceType
				+ ", artifactType=" + packageType + ", packageUri=" + packageUri
				+ ", port=" + port + ", endpointName=" + endpointName
				+ ", version=" + version
				+ ", #instances=" + numberOfInstances +"]";
	}
}