package eu.choreos.servicedeployer.npm;


import javax.ws.rs.core.Response;

import org.apache.commons.lang.NotImplementedException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;
import eu.choreos.servicedeployer.Configuration;


public class NodePoolManagerClient implements NodePoolManager {

	private String HOST; 
	private WebClient client;
	
	public NodePoolManagerClient() {
		HOST = Configuration.get("NODE_POOL_MANAGER");
		client = WebClient.create(HOST);
		
		// remove time out
		// not proud of it!
		HTTPConduit http = (HTTPConduit)WebClient.getConfig(client).getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(0);//indefined
		httpClientPolicy.setReceiveTimeout(0);//indefined
		http.setClient(httpClientPolicy);
	}
	
	@Override
	public String applyConfig(String configName) {

		client.path("nodes/configs");   	
		Config config = new Config();
    	config.setName(configName);
        Response response = client.post(config);

        return (String) response.getMetadata().get("Location").get(0);
	}

	@Override
	public NodeRestRepresentation getNode(String nodeId) {
		throw new NotImplementedException();
	}
    
	

}
