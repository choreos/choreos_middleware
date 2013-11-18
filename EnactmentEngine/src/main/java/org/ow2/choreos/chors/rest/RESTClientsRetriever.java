/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.rest;

import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.deployment.services.ServicesManagerImpl;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.ServicesManager;

/**
 * Retrieve DeploymentManager clients using the URIs configured on
 * owners.properties.
 * 
 * @author leonardo
 * 
 */
public class RESTClientsRetriever {

    public static NodePoolManager npmForTest;
    public static ServicesManager servicesManagerForTest;
    public static boolean testing = false;

    public static ServicesManager getServicesManager() {
	if (!testing) {
	    ServicesManager servicesManager = new ServicesManagerImpl();
	    return servicesManager;
	} else {
	    return servicesManagerForTest;
	}
    }

    public static NodePoolManager getNodePoolManager(String owner) {
	if (!testing) {
	    NodePoolManager nodePoolManager = NPMFactory.getNewNPMInstance(owner);
	    return nodePoolManager;
	} else {
	    return npmForTest;
	}
    }
}
