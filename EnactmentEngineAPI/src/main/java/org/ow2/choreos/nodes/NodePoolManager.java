/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes;

import java.util.List;

import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public interface NodePoolManager {

    /**
     * Create a node on the cloud infrastructure.
     * 
     * @param nodeSpec
     * @return the representation of the created node
     * @throws NodeNotCreatedException
     *             if node was not created
     */
    public CloudNode createNode(NodeSpec nodeSpec) throws NodeNotCreatedException;

    /**
     * Retrieve information about all the nodes managed by this Node Pool
     * Manager
     * 
     * @return
     */
    public List<CloudNode> getNodes();

    /**
     * Retrieve information of a node according to a given node id.
     * 
     * @param nodeId
     * @return the representation of the requested node
     * @throws NodeNotFoundException
     *             if the node does not exist
     */
    public CloudNode getNode(String nodeId) throws NodeNotFoundException;

    /**
     * Destroys the Virtual Machine node
     * 
     * @param nodeId
     *            the id of the node to be destroyed
     * @throws NodeNotDestroyed
     *             if could not destroy node
     * @throws NodeNotFoundException
     *             if the node does not exist
     */
    public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException;

    /**
     * Destroys all the Virtual Machine nodes
     * 
     * @throws NodeNotDestroyed
     *             if could not destroy some node
     */
    public void destroyNodes() throws NodeNotDestroyed;

    /**
     * Apply configurations on selected node.
     * 
     * Such configurations are the ones requested through the
     * <code>applyConfig</code> operation
     * 
     * @param nodeId
     *            the id of the node to be upgraded
     * @throws NodeNotUpdatedException
     *             if could not upgrade the node
     * @throws NodeNotFoundException
     *             if the node does not exist
     */
    public void updateNode(String nodeId) throws NodeNotUpdatedException, NodeNotFoundException;

    /**
     * 
     * @param deploymentRequest
     *            what must be deployed
     * @return the representation of the node where the configuration will be
     *         applied,
     * @throws PrepareDeploymentFailedException
     *             if it was not possible to allocate any node
     */
    public List<CloudNode> prepareDeployment(DeploymentRequest deploymentRequest) throws PrepareDeploymentFailedException;
}
