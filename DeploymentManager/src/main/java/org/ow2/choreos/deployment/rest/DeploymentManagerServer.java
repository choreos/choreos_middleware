/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.rest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.DeploymentManagerPreferences;
import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.deployment.nodes.rest.NodesResource;
import org.ow2.choreos.deployment.nodes.rest.RunListResource;
import org.ow2.choreos.deployment.services.InfrastructureMonitoringConfigurator;
import org.ow2.choreos.deployment.services.rest.ServicesResource;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.rest.RESTServer;
import org.ow2.choreos.utils.LogConfigurator;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshWaiter;

/**
 * Stand alone server that makes the REST API available to clients.
 * 
 * @author alfonso, leonardo
 * 
 */
public class DeploymentManagerServer {

    private static final String MONITORING_NODE = "infraMonitoringNode";
    public final String NAME = "Deployment Manager";
    public static String URL;
    private RESTServer restServer;
    private CloudNode infrastructureMonitoringNode;

    private Logger logger = Logger.getLogger(DeploymentManagerServer.class);
    private DeploymentManagerPreferences prefs;

    static {
	String port = DeploymentManagerConfiguration.get("DEPLOYMENT_MANAGER_PORT");
	URL = "http://0.0.0.0:" + port + "/deploymentmanager/";
    }

    public DeploymentManagerServer() {
	prefs = new DeploymentManagerPreferences();
	loadMonitoring();
	this.restServer = new RESTServer(NAME, URL, new Class[] { NodesResource.class, RunListResource.class,
		ServicesResource.class });
    }

    public void start() {
	this.restServer.start();
    }

    public void stop() {
	this.restServer.stop();
	if (deleteMonitoringNode()) {
	    logger.info("Flushing monitoring preferences...");
	    boolean nodeDestroyed = false;
	    NodePoolManager npm = NPMFactory.getNewNPMInstance();
	    try {
		npm.destroyNode(infrastructureMonitoringNode.getId());
		nodeDestroyed = true;
	    } catch (NodeNotDestroyed e) {
		logger.warn("Could not destroy node");
	    } catch (NodeNotFoundException e) {
		logger.warn("Could not destroy node");
	    }
	    if (nodeDestroyed)
		prefs.getPrefs().remove(MONITORING_NODE);
	}
    }

    protected void finalize() throws Throwable {
	stop();
    }

    private void loadMonitoring() {
	if (usingMonitoring()) {
	    logger.info("Monitoring is enabled. Setting it up now!");
	    if (!deleteMonitoringNode()) {
		logger.info("Keep monitoring node is enabled");
		try {
		    infrastructureMonitoringNode = tryLoadExistingNode();
		    logger.info("Monitoring node loaded (" + infrastructureMonitoringNode.getIp() + ").");
		} catch (IOException e) {
		    logger.info("Could not load existing node successfully.");
		    setUpNewMonitoringNode();
		} catch (ClassNotFoundException e) {
		    logger.info("Could not load existing node successfully.");
		    setUpNewMonitoringNode();
		}
		if (!monitoringNodeIsReachable()) {
		    setUpNewMonitoringNode();
		}
	    } else {
		logger.info("Not keeping monitoring node. Bootstraping and setting up one.");
		setUpNewMonitoringNode();
	    }
	    savePrefs();
	} else {
	    logger.info("Nothing to do about monitoring. Flag is false");
	}
    }

    private boolean monitoringNodeIsReachable() {
	SshWaiter waiter = new SshWaiter();
	try {
	    waiter.waitSsh(infrastructureMonitoringNode.getIp(), infrastructureMonitoringNode.getUser(),
		    infrastructureMonitoringNode.getPrivateKeyFile(), 30);
	} catch (SshNotConnected e) {
	    return false;
	}
	return true;
    }

    private void savePrefs() {
	try {
	    logger.info("Saving monitoring node preferences...");
	    prefs.putObject(MONITORING_NODE, infrastructureMonitoringNode);
	    logger.info("Saving monitoring node preferences (done).");
	} catch (IOException e) {
	    logger.warn("Error on saving infrastructure node preferences");
	}
    }

    private void setUpNewMonitoringNode() {
	logger.info("Setting up infrastructure monitoring...");
	InfrastructureMonitoringConfigurator infrastructureMonitoringConfigurator = new InfrastructureMonitoringConfigurator();
	infrastructureMonitoringNode = infrastructureMonitoringConfigurator.getConfiguredInfrastructureMonitoringNode();
    }

    private CloudNode tryLoadExistingNode() throws IOException, ClassNotFoundException {
	logger.info("Trying to load existing node...");
	return prefs.getObject(MONITORING_NODE);
    }

    private boolean deleteMonitoringNode() {
	return Boolean.parseBoolean(DeploymentManagerConfiguration.get("DELETE_GLIMPSE_NODE_ON_STOP_SERVER"));
    }

    private boolean usingMonitoring() {
	return Boolean.parseBoolean(DeploymentManagerConfiguration.get("MONITORING"));
    }

    public static void main(String[] args) {
	LogConfigurator.configLog();
	DeploymentManagerServer server = new DeploymentManagerServer();
	server.start();
    }
}
