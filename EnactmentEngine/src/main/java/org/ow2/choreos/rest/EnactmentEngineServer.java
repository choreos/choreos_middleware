/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.rest;

import org.ow2.choreos.chors.rest.ChorResource;
import org.ow2.choreos.deployment.nodes.rest.NodesResource;
import org.ow2.choreos.utils.LogConfigurator;

public class EnactmentEngineServer {

    public static final String NAME = "Choreography Deployer";
    public static String URL;

    private static final String ENACTMENT_ENGINE_PORT = "9100";

    private RESTServer restServer;

    static {
	URL = "http://0.0.0.0:" + ENACTMENT_ENGINE_PORT + "/enactmentengine/";
    }

    public EnactmentEngineServer() {
	this.restServer = new RESTServer(NAME, URL, new Class[] { ChorResource.class, NodesResource.class });
    }

    public void start() {
	this.restServer.start();
    }

    public void stop() {
	this.restServer.stop();
    }

    public static void main(String[] args) throws InterruptedException {
	LogConfigurator.configLog();
	EnactmentEngineServer server = new EnactmentEngineServer();
	server.start();
    }
}
