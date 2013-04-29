package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.ResourceImpactDefs.MemoryTypes;

enum NodeSelectorMapperPolicy {
	ANY_FIT,
	EXACT_FIT
};

public class NodeSelectorMapper {

	private static final String[] NODE_TYPES = Configuration.getMultiple("NODE_TYPES");
	private NodeSelectorMapperPolicy policy = null;
	
	public NodeSelectorMapper (String policy) {
		if(policy.compareTo("ANY_FIT") == 0)
			this.policy = NodeSelectorMapperPolicy.ANY_FIT;
		else if(policy.compareTo("EXACT_FIT") == 0)
			this.policy = NodeSelectorMapperPolicy.EXACT_FIT;
		else
			throw new IllegalArgumentException();
	}
	
	public static String[] types () {
		return NODE_TYPES;
	}

	public List<Node> filterByResourceImpact(ResourceImpact resourceImpact, List<Node> allNodes) {
		List<Node> filtered = new ArrayList<Node>();
		for (Node node: allNodes) {
			if (isAcceptable(resourceImpact, node)) {
				filtered.add(node);
			}
		}
		return filtered;
	}
	
	private boolean near(int a, int b) {
		if(Math.abs(a-b) < 0.1*(double)a) 
			return true;
		
		return false;
	}

	private int getBaseMemoryFromType(MemoryTypes memory) {
		switch (memory) {
		case SMALL :
			return 256;
		case MEDIUM :
			return 512;
		case LARGE :
			return 768;
		default:
			return 256;
		}
	}

	public boolean isAcceptable(ResourceImpact resourceImpact, Node selected) {
		if(resourceImpact == null || resourceImpact.getMemory() == null) {
			return true;
		}
		switch(this.policy) {
		case ANY_FIT :
			return getBaseMemoryFromType(resourceImpact.getMemory()) <= selected.getRam();
		case EXACT_FIT:
			return near(getBaseMemoryFromType(resourceImpact.getMemory()), selected.getRam());
		default:
			return false;
		}
	}	
}
