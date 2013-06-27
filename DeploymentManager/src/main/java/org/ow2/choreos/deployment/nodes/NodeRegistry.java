/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.CloudNode;

/**
 * Local node registry.
 * 
 * The nodes managed by the NodePoolManager
 * 
 * @author leonardo
 * 
 */
public class NodeRegistry {

    private static NodeRegistry instance = new NodeRegistry();

    private Map<String, CloudNode> nodes = new ConcurrentHashMap<String, CloudNode>();

    private NodeRegistry() {

    }

    public static NodeRegistry getInstance() {
        return instance;
    }

    public synchronized void putNode(CloudNode node) {
        this.nodes.put(node.getId(), node);
    }

    public CloudNode getNode(String nodeId) throws NodeNotFoundException {
        CloudNode node = this.nodes.get(nodeId);
        if (node == null) {
            throw new NodeNotFoundException(nodeId);
        }
        return node;
    }

    public void deleteNode(String nodeId) {
        this.nodes.remove(nodeId);
    }

    public List<CloudNode> getNodes() {
        return new ArrayList<CloudNode>(this.nodes.values());
    }

    public void clear() {
        this.nodes.clear();
    }
}
