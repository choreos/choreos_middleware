/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.selector;

import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.Selector;

/**
 * Selects a node to apply a given configuration
 * 
 * The selection can consider functional requirements, which is provided by
 * config.name and non-functional requirements, which is provided by
 * config.resourceImpact. Implementing classes must use the NodePoolManager to
 * retrieve nodes AND/OR create new nodes (the use of other operations of
 * NodePoolManager are not allowed). NodeSelectors are always accessed as
 * singletons. Implementing classes must consider concurrent access to the
 * selectNodes method.
 * 
 * @author leonardo
 * 
 */
public interface NodeSelector extends Selector<Node, DeploymentRequest> {

}
