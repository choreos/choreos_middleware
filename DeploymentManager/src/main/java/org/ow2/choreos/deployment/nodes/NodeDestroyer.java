/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;

/**
 * Invokes the cloud provider to destroy the node and, if successful, removes
 * the node from the nodes registry.
 * 
 * @author leonardo
 */
public class NodeDestroyer {

    private static Logger logger = Logger.getLogger(NodeDestroyer.class);

    private final String nodeId;
    private final CloudProvider cp;
    private final NodeRegistry nodeRegistry;

    public NodeDestroyer(String nodeId) {
        this.nodeId = nodeId;
        String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
        this.cp = CloudProviderFactory.getFactoryInstance().getCloudProviderInstance(cloudProviderType);
        this.nodeRegistry = NodeRegistry.getInstance();
    }

    public void destroyNode() throws NodeNotDestroyed, NodeNotFoundException {
        if (nodeRegistry.getNode(nodeId) == null)
            throw new NodeNotFoundException(nodeId);
        cp.destroyNode(nodeId);
        nodeRegistry.deleteNode(nodeId);
        logger.info("Node " + nodeId + " destroyed");
    }

}
