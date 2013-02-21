package org.ow2.choreos.deployment.services.datamodel;

import java.util.HashMap;
import java.util.Map;

import org.ow2.choreos.deployment.nodes.datamodel.Node;

public class ServiceInstance {
	
	private String uri;
	private String hostname;
	private String ip;
	private String nodeId;
	private Node node;
	
	private Map<ServiceType, String> busUris = new HashMap<ServiceType, String>();	

	//private String myParentServiceId;
	
	private ServiceSpec myParentServiceSpec;
	
//	private DeployedServicesRegistry registry = new DeployedServicesRegistry();
	
	public ServiceInstance(Node node, Service service) {
		this.setNode(node);
//		this.setParentServiceId(service.getId());
		this.setMyParentServiceSpec(service.getSpec());
		service.addInstance(this);
	}
	
	public ServiceInstance() {
		
	}
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
		
		this.setHost(node.getHostname());
		this.setIp(node.getIp());
		this.setNodeId(node.getId());
	}

/*	public String getParentServiceId() {
		return myParentServiceId;
	}

	public void setParentServiceId(String parentServiceId) {
		this.myParentServiceId = parentServiceId;
	}*/

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
		
		//Service myService = registry.getService(myParentServiceId);
		
		switch (getMyParentServiceSpec().getArtifactType()) {
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
								+ getMyParentServiceSpec().artifactType + " service.");
		}
		
		if (ip != null && !ip.isEmpty())
			return "http://" + ip + ":" + getMyParentServiceSpec().getPort() + "/" + uriContext;
		else
			return "http://" + hostname + ":" + getMyParentServiceSpec().getPort() + "/" + uriContext;
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
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((getMyParentServiceSpec() == null) ? 0 : getMyParentServiceSpec().hashCode());
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
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		if (getMyParentServiceSpec() == null) {
			if (other.getMyParentServiceSpec() != null)
				return false;
		} else if (!getMyParentServiceSpec().equals(other.getMyParentServiceSpec()))
			return false;
		return true;
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

}
