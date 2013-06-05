/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;

/**
 * Selects a node to apply a given configuration
 * 
 * The selection can consider functional requirements, which is provided by
 * <code>config.name</code> and non-functional requirements, which is provided
 * by <config>config.resourceImpact</code> Implementing classes must receive a
 * NodePoolManager to retrieve nodes OR create new nodes (the use of other
 * operations of NodePoolManager are not allowed). NodeSelectors are always
 * accessed as singletons. Implementing class must consider concurrent access to
 * the selectNodes method.
 * 
 * @author leonardo
 * 
 */
public interface NodeSelector {

    public List<Node> selectNodes(Config config, NodePoolManager npm) throws NodeNotSelectedException;
}
