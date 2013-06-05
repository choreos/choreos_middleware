/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.List;

import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

/**
 * Provides access to cloud service functions to create nodes on the cloud
 * 
 * Each specific provider (e.g. AmazonWS) must have an implementing class of
 * this interface.
 * 
 * @author leonardo, felps, furtado
 * 
 */
public interface CloudProvider {

    public String getProviderName();

    public Node createNode(Node node, ResourceImpact resourceImpact) throws NodeNotCreatedException;

    public Node getNode(String nodeId) throws NodeNotFoundException;

    public List<Node> getNodes();

    public void destroyNode(String id) throws NodeNotDestroyed, NodeNotFoundException;

    public Node createOrUseExistingNode(Node node, ResourceImpact resourceImpact) throws NodeNotCreatedException;

}
