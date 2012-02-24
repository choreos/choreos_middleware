package eu.choreos.storagefactory.utils;

import java.util.List;

public interface NodePoolManagerHandler {
	public String createNode(String recipe);

	public String getNode(String nodeId);

	public List<String> getNodes();

	public void destroyNode(String id);

}
