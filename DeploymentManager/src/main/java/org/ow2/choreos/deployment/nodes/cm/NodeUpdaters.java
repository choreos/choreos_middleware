/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NodeUpdaters {

    // the key is the node id
    private static Map<String, NodeUpdater> updaters = new ConcurrentHashMap<String, NodeUpdater>();

    public static NodeUpdater getUpdaterFor(String nodeId) {
        synchronized (NodeUpdaters.class) {
            if (!updaters.containsKey(nodeId)) {
                NodeUpdater updater = new NodeUpdater();
                updaters.put(nodeId, updater);
            }
        }
        return updaters.get(nodeId);
    }

}
