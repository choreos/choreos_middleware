package org.ow2.choreos.deployment.services.datamodel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.ow2.choreos.deployment.nodes.datamodel.Node;

public class ServiceInstance {
	
	/**
	 * Host name of node that hosts the legacy instance
	 */
	private String legacyHostname;
	
	/**
	 * Ip address of node that hosts the legacy instance
	 */
	private String legacyIp;
	
	/**
	 * The hosting node of a non-legacy instance
	 */
	private Node node;
	
	/**
	 * The node that handles bus calls
	 */
	private String easyEsbNodeAdminEndpoint;

	/**
	 * URI for access instance directly
	 */
	private String nativeUri;
	
	/**
	 * Map containing all URIs for access instance through the bus
	 */
	private Map<ServiceType, String> busUris = new HashMap<ServiceType, String>();	
	
	/**
	 * The effective service instance ID
	 */
	private String instanceId;
	
	/**
	 * Id of its service
	 */
	private String serviceId;
	
	
	// must have not this property
	private ServiceSpec myParentServiceSpec;
	
	
	
	
	
	/*
	 * Default constructor used by Java XML Bindings
	 */
	public ServiceInstance() {
		
	}
	
	public ServiceInstance(Node node, Service service) {
		this.setNode(node);
		this.setMyParentServiceSpec(service.getSpec());
		this.setInstanceId(UUID.randomUUID().toString());
		service.addInstance(this);
	}
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
		//this.setLegacyHostname(node.getHostname());
		//this.setLegacyIp(node.getIp());
	}
	
	/**
	 * 
	 * @param host It can be the IP or the host name where the service was deployed
	 */
	public void setLegacyHostname(String host) {
		this.legacyHostname = host;
	}
	
	/**
	 * 
	 * @return It can be the IP or the host name where the service was deployed
	 */
	public String getLegacyHostname() {
		return legacyHostname;
	}

	public String getLegacyIp() {
		return legacyIp;
	}

	public void setLegacyIp(String ip) {
		this.legacyIp = ip;
	}
	
	public ServiceSpec getMyParentServiceSpec() {
		return myParentServiceSpec;
	}

	public void setMyParentServiceSpec(ServiceSpec myParentServiceSpec) {
		this.myParentServiceSpec = myParentServiceSpec;
	}

	public String getBusUri(ServiceType type) {
		return this.busUris.get(type);
	}
	
	public void setBusUri(ServiceType type, String uri) {
		this.busUris.put(type, uri);
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setNativeUri(String uri) {
		this.nativeUri = uri;
	}

	public String getNativeUri() {	
		if (definedNativeUri()) {
			return nativeUri;
		} else {
			return getDefaultnativeUri();
		}
	}

	private boolean definedNativeUri() {
		return nativeUri != null && !nativeUri.isEmpty();
	}

	private String getDefaultnativeUri() {
		
		// interesting note about the ending slash
		// http://www.searchenginejournal.com/to-slash-or-not-to-slash-thats-a-server-header-question/6763/
		
		String _hostname, _ip;
		if(this.getMyParentServiceSpec().getPackageType() == PackageType.LEGACY) {
			_hostname = legacyHostname;
			_ip = legacyIp;
		} else {
			_hostname = node.getHostname();
			_ip = node.getIp();
		}
		
		if(_hostname == null && _ip == null)
			throw new IllegalStateException("Sorry, I don't know neither the hostname nor the IP yet");
		
		String uriContext;
		
		switch (getMyParentServiceSpec().getPackageType()) {
			case TOMCAT:
				uriContext = getMyParentServiceSpec().getName() + "/" + getMyParentServiceSpec().getEndpointName();
				break;
			case COMMAND_LINE:
				uriContext = getMyParentServiceSpec().getEndpointName() + "/";
				break;
			case EASY_ESB:
				uriContext = "services/" + getMyParentServiceSpec().getEndpointName() + "ClientProxyEndpoint/";
				break;
			default:
				throw new IllegalStateException(
						"Sorry, I don't know how to provide an URL to a "
								+ getMyParentServiceSpec().packageType + " service.");
		}
		
		if (_ip != null && !_ip.isEmpty())
			return "http://" + _ip + ":" + getMyParentServiceSpec().getPort() + "/" + uriContext;
		else
			return "http://" + _hostname + ":" + getMyParentServiceSpec().getPort() + "/" + uriContext;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		ServiceSpec spec = getMyParentServiceSpec();
		result = prime * result + ((nativeUri == null) ? 0 : nativeUri.hashCode());
		result = prime * result + ((spec == null) ? 0 : spec.hashCode());
		result = prime * result + ((instanceId == null) ? 0 : instanceId.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
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
		
		ServiceInstance other = (ServiceInstance) obj;
		
		if (nativeUri == null) {
			if (other.nativeUri != null)
				return false;
		} else if (!nativeUri.equals(other.nativeUri))
			return false;
		
		if (getMyParentServiceSpec() == null) {
			if (other.getMyParentServiceSpec() != null)
				return false;
		} else if (!getMyParentServiceSpec().equals(other.getMyParentServiceSpec()))
			return false;
		
		boolean equalsInstance = true;
		if(instanceId == null)  {
			if(other.instanceId != null)
				equalsInstance = false;
		} else if(!instanceId.equals(other.instanceId))
			equalsInstance = false;
		
		boolean equalsService = true;
		if(serviceId == null)  {
			if(other.serviceId != null)
				equalsService = false;
		} else if(!serviceId.equals(other.serviceId))
			equalsService = false;
		
		/*
		 * same instance of the same service
		 */
		if(!(equalsInstance && equalsService)) return false;
		
		return true;
	}

	public String getEasyEsbNodeAdminEndpoint() {
		return easyEsbNodeAdminEndpoint;
	}

	public void setEasyEsbNodeAdminEndpoint(String easyEsbNodeAdminEndpoint) {
		this.easyEsbNodeAdminEndpoint = easyEsbNodeAdminEndpoint;
	}

	@Override
	public String toString() {
		return "ServiceInstance [legacyHostname=" + legacyHostname
				+ ", legacyIp=" + legacyIp + ", node=" + node
				+ ", easyEsbNodeAdminEndpoint=" + easyEsbNodeAdminEndpoint
				+ ", nativeUri=" + nativeUri + ", instanceId=" + instanceId
				+ ", serviceId=" + serviceId + "]";
	}
	
}
