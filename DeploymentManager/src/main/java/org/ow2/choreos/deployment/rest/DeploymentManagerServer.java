/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.rest;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.rest.NodesResource;
import org.ow2.choreos.deployment.nodes.rest.RunListResource;
import org.ow2.choreos.deployment.services.rest.ServicesResource;
import org.ow2.choreos.rest.RESTServer;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * Stand alone server that makes the REST API available to clients.
 * 
 * @author alfonso, leonardo
 * 
 */
public class DeploymentManagerServer {

    public final String NAME = "Deployment Manager";
    public static String URL;
    private RESTServer restServer;

    static {
        String port = DeploymentManagerConfiguration.get("DEPLOYMENT_MANAGER_PORT");
        URL = "http://0.0.0.0:" + port + "/deploymentmanager/";
    }

    public DeploymentManagerServer() {

        this.restServer = new RESTServer(NAME, URL, new Class[] { NodesResource.class, RunListResource.class,
                ServicesResource.class });
    }

    public void start() {

        this.restServer.start();
    }

    public void stop() {

        this.restServer.stop();
    }

    public static void main(String[] args) {

        LogConfigurator.configLog();
        DeploymentManagerServer server = new DeploymentManagerServer();
        server.start();
    }
}
