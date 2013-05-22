package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;

/**
 * Selects a node to apply a given configuration
 * 
 * The selection can consider functional requirements, which is provided by <code>config.name</code>
 * and non-functional requirements, which is provided by <config>config.resourceImpact</code>
 * Implementing classes must receive a NodePoolManager to retrieve nodes OR create new nodes
 * (the use of other operations of NodePoolManager are not allowed).
 * 
 * Obs: NodeSelectors are always accessed as singletons.
 * 
 * @author leonardo
 *
 */
public interface NodeSelector {
	
	public List<Node> selectNodes(Config config, NodePoolManager npm) throws NodeNotSelectedException;
}
