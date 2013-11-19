/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes.cm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ow2.choreos.nodes.datamodel.CloudNode;

public class NodePreparers {

    // the key is the node id
    private static Map<String, NodePreparer> preparers = new ConcurrentHashMap<String, NodePreparer>();

    public static NodePreparer preparerForTest;
    public static boolean testing = false;
    
    public static NodePreparer getPreparerFor(CloudNode node) {
        String nodeId = node.getId();
        if (testing) {
            return preparerForTest;
        } else {
            synchronized (NodePreparers.class) {
                if (!preparers.containsKey(nodeId)) {
                    NodePreparer preparer = new NodePreparer(node);
                    preparers.put(nodeId, preparer);
                }
            }
            return preparers.get(nodeId);
        }
    }

}
