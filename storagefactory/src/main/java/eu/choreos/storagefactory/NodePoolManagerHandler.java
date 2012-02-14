package eu.choreos.storagefactory;

import eu.choreos.storagefactory.datamodel.*;

import java.util.List;

public class NodePoolManagerHandler{

	 public boolean createNode(InfrastructureNodeData node) {
		node.setHostname("choreos-node");
		return true;
	 }
	 
	 public InfrastructureNodeData getNode(String nodeId) {
		return null;
	 }
	 
	 public List<InfrastructureNodeData> getNodes(){
		return null;
	 }
	 
	 public void destroyNode(String id){
	 }
	 
}
