package eu.choreos.servicedeployer;

import java.util.List;

import eu.choreos.servicedeployer.datamodel.Service;


public interface NodePoolManagerHandler {

	public Service createNode(String recipe, Service service);
	
	public String getNode(String nodeId);

	public List<String> getNodes();

	public void destroyNode(String id);

	public void initializeNode();

}
