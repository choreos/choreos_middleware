/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.rest;

import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.client.ServicesClient;

/**
 * Retrieve DeploymentManager clients using the URIs configured on
 * owners.properties.
 * 
 * @author leonardo
 * 
 */
public class RESTClientsRetriever {

    public ServicesManager getServicesClient(String owner) {

        String uri = Owners.get(owner);
        ServicesManager client = new ServicesClient(uri);
        return client;
    }

    public NodePoolManager getNodesClient(String owner) {

        String uri = Owners.get(owner);
        NodePoolManager client = new NodesClient(uri);
        return client;
    }
}
