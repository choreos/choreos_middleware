package eu.choreos.servicedeployer.npm;

import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;

public interface NodePoolManager {

	/**
	 * 
	 * @param config corresponds to the chef recipe name
	 * 
	 * @return the node location; use "GET location" to retrieve more details about the node
	 */
	public String applyConfig(String configName);
	
	public NodeRestRepresentation getNode(String nodeId); 
}
