package org.ow2.choreos.npm.selector;

import org.ow2.choreos.npm.datamodel.Config;
import org.ow2.choreos.npm.datamodel.Node;

/**
 * Selects a node to apply a given configuration
 * 
 * The selection can consider functional requirements, which is provided by <code>config.name</code>
 * and non-functional requirements, which is provided by <config>config.resourceImpact</code>
 * Implementing classes must receive a CloudProvider
 * 
 * @author leonardo
 *
 */
public interface NodeSelector {

	public Node selectNode(Config config);
}
