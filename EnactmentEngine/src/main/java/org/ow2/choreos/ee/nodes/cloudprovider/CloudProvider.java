/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes.cloudprovider;

import java.util.List;

import org.ow2.choreos.ee.CloudConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

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

    public String getCloudProviderName();

    public CloudNode createNode(NodeSpec nodeSpec) throws NodeNotCreatedException;

    public CloudNode getNode(String nodeId) throws NodeNotFoundException;

    public List<CloudNode> getNodes();

    public void destroyNode(String id) throws NodeNotDestroyed, NodeNotFoundException;

    public CloudNode createOrUseExistingNode(NodeSpec nodeSpec) throws NodeNotCreatedException;

    public void setCloudConfiguration(CloudConfiguration cloudConfiguration);

}
