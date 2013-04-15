package org.ow2.choreos.deployment.services.datamodel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.ow2.choreos.deployment.nodes.datamodel.Node;

public class ServiceInstance {
	
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
	
	/*
	 * The reference to the service object that this service instance is its
	 */
	private DeployedService service;
	

	/*
	 * Default constructor used by Java XML Bindings
	 */
	public ServiceInstance() {
		
	}

	public Map<ServiceType, String> getBusUris() {
		return busUris;
	}
	
	public void setBusUris(Map<ServiceType, String> busUris) {
		this.busUris = busUris;
	}
	
	public DeployedService getService() {
		return service;
	}
	
	public void setService(DeployedService service) {
		if (this.service != null) {
			throw new IllegalStateException("This instance already has a Service defined!");
		}
		this.service = service;
	}
	
	public ServiceInstance(Node node, DeployedService service) {
		this.setNode(node);
		this.setInstanceId(UUID.randomUUID().toString());
		service.addInstance(this);
		this.service = service;
	}
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
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
		_hostname = node.getHostname();
		_ip = node.getIp();
		
		if(_hostname == null && _ip == null)
			throw new IllegalStateException("Sorry, I don't know neither the hostname nor the IP yet");
		
		String uriContext;		
		String endpointName = ((DeployedServiceSpec)service.getSpec()).getEndpointName();
		PackageType packageType = service.getSpec().packageType;
		
		switch (packageType) {
			case TOMCAT:
			uriContext = service.getSpec().getUUID() + "/" + 
						endpointName;
				break;
			case COMMAND_LINE:
				uriContext = endpointName + "/";
				break;
			case EASY_ESB:
				uriContext = "services/" + endpointName + "ClientProxyEndpoint/";
				break;
			default:
			throw new IllegalStateException(
						"Sorry, I don't know how to provide an URL to a "
								+ packageType + " service.");
		}
		
		int port = ((DeployedServiceSpec)service.getSpec()).getPort();
		if (_ip != null && !_ip.isEmpty()) {
			return "http://" + _ip + ":" + port + "/" + uriContext;
		} else
			return "http://" + _hostname + ":" + port + "/" + uriContext;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nativeUri == null) ? 0 : nativeUri.hashCode());
		result = prime * result + ((instanceId == null) ? 0 : instanceId.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
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
		
		boolean equalsInstance = true;
		if(instanceId == null)  {
			if(other.instanceId != null)
				equalsInstance = false;
		} else if(!instanceId.equals(other.instanceId))
			equalsInstance = false;
		
		boolean equalsService = true;
		if(service == null)  {
			if(other.service != null)
				equalsService = false;
		} else if(!service.equals(other.service))
			equalsService = false;
		
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
