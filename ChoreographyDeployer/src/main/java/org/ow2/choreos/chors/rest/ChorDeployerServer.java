/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.rest;

import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.rest.RESTServer;
import org.ow2.choreos.utils.LogConfigurator;

public class ChorDeployerServer {

    public static final String NAME = "Choreography Deployer";
    public static String URL;

    private static final String CHOR_DEPLOYER_PORT_PROPERTY = "CHOR_DEPLOYER_PORT";
    
    private RESTServer restServer;

    static {
	String port = ChoreographyDeployerConfiguration.get(CHOR_DEPLOYER_PORT_PROPERTY);
	URL = "http://0.0.0.0:" + port + "/choreographydeployer/";
    }

    public ChorDeployerServer() {

	this.restServer = new RESTServer(NAME, URL, new Class[] { ChorResource.class });

    }

    public void start() {

	this.restServer.start();
    }

    public void stop() {

	this.restServer.stop();
    }

    public static void main(String[] args) throws InterruptedException {

	LogConfigurator.configLog();
	ChorDeployerServer server = new ChorDeployerServer();
	server.start();
    }
}
