package eu.choreos.servicedeployer.npm;


import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import eu.choreos.nodepoolmanager.datamodel.Config;


public class NodePoolManagerClient implements NodePoolManager {

	private static String HOST = "http://localhost:9100/";
	protected static final WebClient client = WebClient.create(HOST);
	
	@Override
	public String applyConfig(String configName) {

		client.path("nodes/configs");   	
		Config config = new Config();
    	config.setName(configName);
        Response response = client.post(config);

        return (String) response.getMetadata().get("Location").get(0);
	}
    
	

}