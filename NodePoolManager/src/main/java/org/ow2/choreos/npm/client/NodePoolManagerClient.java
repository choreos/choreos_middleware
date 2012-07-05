package org.ow2.choreos.npm.client;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;

public class NodePoolManagerClient implements NodePoolManager {

	private String host; 

	/**
	 * 
	 * @param host ex: 'http://localhost:9100/'
	 * 
	 */
	public NodePoolManagerClient(String host) {
		
		this.host = host;
	}

	private WebClient setupClient() {
		
		WebClient client = WebClient.create(host);
		
		// remove time out
		// not proud of it!
		HTTPConduit http = (HTTPConduit)WebClient.getConfig(client).getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(0);//indefined
		httpClientPolicy.setReceiveTimeout(0);//indefined
		http.setClient(httpClientPolicy);
		
		return client;
	}
	
	@Override
	public NodeRestRepresentation applyConfig(String configName) {

		WebClient client = setupClient();
		client.path("nodes/configs");   	
		Config config = new Config();
    	config.setName(configName);
    	NodeRestRepresentation node = null;
    	try {
    		node = client.post(config, NodeRestRepresentation.class);
    	} catch (WebApplicationException e) {
    		; // node remains null
    	}

        return node;
	}

	@Override
	public NodeRestRepresentation getNode(String nodeId) {

		WebClient client = setupClient();
		client.path("nodes/" + nodeId);
		NodeRestRepresentation nodeRest = null;
		try {
			nodeRest = client.get(NodeRestRepresentation.class);
		} catch (WebApplicationException e) {
			; // nodeRest remains null
		}
		
		return nodeRest;
	}

	@Override
	public NodeRestRepresentation createNode() {

		WebClient client = setupClient();
		client.path("nodes");   	
		client.type(MediaType.APPLICATION_XML);
        Response response = client.post("<node/>");

        NodeRestRepresentation node = null;
        try {
        	node = (NodeRestRepresentation) response.getEntity();
        } catch (ClassCastException e) {
        	; // node remains null
        }
        
        return node;
	}

	@Override
	public boolean upgradeNodes() {
	    
		WebClient client = setupClient();
        client.path("nodes/upgrade");
        Response response = client.post(null);

        if (response.getStatus() == 200) {
        	return true;
        } else {
        	return false;
        }
	}
}
