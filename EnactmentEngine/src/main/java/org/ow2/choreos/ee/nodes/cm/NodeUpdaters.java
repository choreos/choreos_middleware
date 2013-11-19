/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes.cm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ow2.choreos.nodes.datamodel.CloudNode;

public class NodeUpdaters {

    // the key is the node id
    private static Map<String, NodeUpdater> updaters = new ConcurrentHashMap<String, NodeUpdater>();

    public static NodeUpdater updaterForTest;
    public static boolean testing = false;
    
    public static NodeUpdater getUpdaterFor(CloudNode node) {
        String nodeId = node.getId();
        if (testing) {
            return updaterForTest;
        } else {
            synchronized (NodeUpdaters.class) {
                if (!updaters.containsKey(nodeId)) {
                    NodeUpdater updater = new NodeUpdater(node);
                    updaters.put(nodeId, updater);
                }
            }
            return updaters.get(nodeId);
        }
    }

}
