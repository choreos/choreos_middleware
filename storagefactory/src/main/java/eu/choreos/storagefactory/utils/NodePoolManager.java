package eu.choreos.storagefactory.utils;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.storagefactory.Configuration;

public class NodePoolManager implements NodePoolManagerHandler {
	
	private static String HOST; 
	protected static WebClient client;
	
	static {
		HOST = Configuration.get("NODE_POOL_MANAGER");
		client = WebClient.create(HOST);
	}
    
	public NodePoolManager(){
		client = WebClient.create(HOST);
		
		HTTPConduit http = (HTTPConduit)WebClient.getConfig(client).getConduit();
		 
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(0);//indefined
		httpClientPolicy.setReceiveTimeout(0);//indefined
		 
		http.setClient(httpClientPolicy);
	}
	@Override
	public String createNode(String recipe) throws Exception {

		client.path("nodes/configs");   	
		Config config = new Config();
    	config.setName(recipe);
        Response response = client.post(config);

        return (String) response.getMetadata().get("Location").get(0);

	}

	@Override
	public String getNode(String nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyNode(String id) {
		// TODO Auto-generated method stub
		
	}

}
