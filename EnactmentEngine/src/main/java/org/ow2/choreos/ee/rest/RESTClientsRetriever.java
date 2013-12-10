/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.rest;

import org.ow2.choreos.ee.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;

/**
 * Retrieve DeploymentManager clients using the URIs configured on
 * owners.properties.
 * 
 * @author leonardo
 * 
 */
public class RESTClientsRetriever {

    public static NodePoolManager npmForTest;
    public static boolean testing = false;

    public static NodePoolManager getNodePoolManager(String owner) {
	if (!testing) {
	    NodePoolManager nodePoolManager = NPMFactory.getNewNPMInstance(owner);
	    return nodePoolManager;
	} else {
	    return npmForTest;
	}
    }
}
