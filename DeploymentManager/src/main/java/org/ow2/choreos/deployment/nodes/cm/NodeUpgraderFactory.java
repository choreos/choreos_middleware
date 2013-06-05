/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NodeUpgraderFactory {

    // the key is the node id
    private static Map<String, NodeUpgrader> upgraders = new ConcurrentHashMap<String, NodeUpgrader>();

    public static NodeUpgrader getInstance(String nodeId) {

	synchronized (NodeUpgraderFactory.class) {
	    if (!upgraders.containsKey(nodeId)) {
		NodeUpgrader upgrader = new NodeUpgrader();
		upgraders.put(nodeId, upgrader);
	    }
	}

	return upgraders.get(nodeId);
    }

}
