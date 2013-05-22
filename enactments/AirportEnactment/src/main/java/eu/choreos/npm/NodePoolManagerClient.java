package eu.choreos.npm;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.NotImplementedException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import eu.choreos.enactment.Configuration;
import eu.choreos.npm.datamodel.Config;
import eu.choreos.npm.datamodel.NodeRestRepresentation;


public class NodePoolManagerClient implements NodePoolManager {

	private String HOST; 
	
	public NodePoolManagerClient() {
		HOST = Configuration.get("NODE_POOL_MANAGER");
	}

	private WebClient setupClient() {
		
		WebClient client = WebClient.create(HOST);
		
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
	public String applyConfig(String configName) {

		WebClient client = setupClient();
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

	@Override
	public String createNode() {

		WebClient client = setupClient();
		client.path("nodes");   	
		client.type(MediaType.APPLICATION_XML);
        Response response = client.post("<node/>");

        String result = (String) response.getMetadata().get("Location").get(0);
        Pattern pattern = Pattern.compile(".*/([0-9.]*?)");
        Matcher matcher = pattern.matcher(result);
        String publicIp = null;
        if (matcher.matches()) {
    		publicIp = matcher.group(1);
        }
        return publicIp;
	}

	@Override
	public void upgradeNodes() {
	    WebClient client = setupClient();
        client.path("nodes/upgrade");
        Response response = client.post(null);

        if (response.getStatus() == 500) {
            System.out.println("Error on upgrading nodes @ NPMClient.");
        }
	}
}
