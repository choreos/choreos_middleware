package eu.choreos.nodepoolmanager.selector;

import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;

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
