package eu.choreos.storagefactory.utils;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import eu.choreos.nodepoolmanager.datamodel.Config;

public class NodePoolManager implements NodePoolManagerHandler {
	
	private static String HOST = "http://localhost:9100/";
	protected static final WebClient client = WebClient.create(HOST);
    
	public NodePoolManager(){
		
	}
	@Override
	public String createNode(String recipe) {

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
