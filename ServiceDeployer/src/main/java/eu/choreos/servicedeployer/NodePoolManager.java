package eu.choreos.servicedeployer;

import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;

import eu.choreos.servicedeployer.datamodel.Service;


public class NodePoolManager extends NodePoolManagerHandler{

	private static String HOST; 
	protected static WebClient client;
	
	static {
		HOST = Configuration.get("NODE_POOL_MANAGER");
		client = WebClient.create(HOST);
	}

	public NodePoolManager(){
		
	}
	
	@Override
	public Service createNode(String recipe, Service service) {
		//TODO To integrate with NodePoolManager 
//		client.path("nodes/configs");   	
//		Config config = new Config();
//    	config.setName(recipe);
//        Response response = client.post(config);
//
//        return (String) response.getMetadata().get("Location").get(0);

		return null;
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

	@Override
	public void initializeNode() {
		// TODO Auto-generated method stub
		
	}

	
}
