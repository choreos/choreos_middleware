/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes.cm;


import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.ow2.choreos.ee.DeploymentManagerConfiguration;
import org.ow2.choreos.ee.Locations;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.invoker.InvokerFactory;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Scp;
import org.ow2.choreos.utils.ScpFailed;

/**
 * 
 * @author leonardo, thiago
 * 
 */
public class NodeBootstrapper {

    public static final String BOOTSTRAP_SCRIPT = "chef-solo/bootstrap.sh";
    public static final String SETUP_HARAKIRI_SCRIPT = "chef-solo/setup_harakiri.sh";
    public static final String SETUP_MONITORING_SCRIPT = "chef-solo/setup_ganglia.sh";
    public static final String INITIAL_NODE_JSON = "chef-solo/node.json";
    public static final String PREPARE_DEPLOYMENT_SCRIPT = "chef-solo/prepare_deployment.sh";
    public static final String PREPARE_UNDEPLOYMENT_SCRIPT = "chef-solo/prepare_undeployment.sh";
    public static final String ADD_RECIPE_SCRIPT = "chef-solo/add_recipe_to_node.sh";
    public static final String REMOVE_RECIPE_FROM_NODE = "chef-solo/remove_recipe_from_node.sh";
    public static final String CHEF_SOLO_FOLDER = "chef-solo";

    private CloudNode node;
    private boolean usingHarakiri = false;
    private boolean usingMonitoring = false;

    private Logger logger = Logger.getLogger(NodeBootstrapper.class);

    public NodeBootstrapper(CloudNode node) {
	this.node = node;
	this.usingHarakiri = Boolean.parseBoolean(DeploymentManagerConfiguration.get("HARAKIRI"));
	this.usingMonitoring = Boolean.parseBoolean(DeploymentManagerConfiguration.get("MONITORING"));
    }

    public void bootstrapNode() throws NodeNotAccessibleException, NodeNotBootstrappedException {
	logger.info("Bootstrapping " + this.node.getIp());
	executeBootstrapCommand();
	saveFile(PREPARE_DEPLOYMENT_SCRIPT, CHEF_SOLO_FOLDER);
	saveFile(PREPARE_UNDEPLOYMENT_SCRIPT, CHEF_SOLO_FOLDER);
	saveFile(ADD_RECIPE_SCRIPT, CHEF_SOLO_FOLDER);
	saveFile(REMOVE_RECIPE_FROM_NODE, CHEF_SOLO_FOLDER);
	saveFile(INITIAL_NODE_JSON, CHEF_SOLO_FOLDER);
	saveFile(INITIAL_NODE_JSON, CHEF_SOLO_FOLDER + "/node.json.backup");
	if (usingHarakiri)
	    setUpHarakiri();
	if (usingMonitoring)
	    setUpMonitoring();
	logger.info("Bootstrap completed at " + this.node);
    }

    private void executeBootstrapCommand() throws NodeNotBootstrappedException {
	Map<String, String> substitutions = new HashMap<String, String>();
	String cookbooksUrl = Locations.get("COOKBOOKS");
	if (cookbooksUrl == null || cookbooksUrl.isEmpty()) {
	    logger.error("Field COOKBOOKS in locations.properties should be filled!");
	    throw new NodeNotBootstrappedException(node.getId());
	}
	substitutions.put("$THE_COOKBOOKS_URL", cookbooksUrl);
	int timeout = 3;
	NodeSetup bootstrapSetup = NodeSetup.getInstance(node, BOOTSTRAP_SCRIPT, timeout, substitutions);
	try {
	    bootstrapSetup.setup();
	} catch (NodeSetupException e) {
	    logger.error("Could not bootstrap node " + node.getId());
	    throw new NodeNotBootstrappedException(node.getId());
	}
    }

    private void setUpHarakiri() {
	Map<String, String> substitutions = new HashMap<String, String>();
	String externalDeplManURL = DeploymentManagerConfiguration.get("EXTERNAL_DEPLOYMENT_MANAGER_URL");
	substitutions.put("$THE_URL", externalDeplManURL);
	substitutions.put("$THE_ID", node.getId());
	int timeout = 1;
	NodeSetup harakiriSetup = NodeSetup.getInstance(node, SETUP_HARAKIRI_SCRIPT, timeout, substitutions);
	try {
	    harakiriSetup.setup();
	} catch (NodeSetupException e) {
	    logger.error("Could not properly setup harakiri on node " + node.getId());
	}
    }

    private void setUpMonitoring() {
	Map<String, String> substitutions = new HashMap<String, String>();
	substitutions.put("$THE_IP", DeploymentManagerConfiguration.get("RESOURCE_METRIC_AGGREGATOR"));
	int timeout = 1;
	NodeSetup monitoringSetup = NodeSetup.getInstance(node, SETUP_MONITORING_SCRIPT, timeout, substitutions);
	try {
	    monitoringSetup.setup();
	} catch (NodeSetupException e) {
	    logger.error("Could not properly setup monitoring on node " + node.getId());
	}
    }

    private void saveFile(String source, String target) throws NodeNotBootstrappedException {
	ScpTask task = new ScpTask(source, target);
        InvokerFactory<Void> factory = new InvokerFactory<Void>();
        Invoker<Void> invoker = factory.geNewInvokerInstance("SCP", task);
	try {
	    invoker.invoke();
	} catch (InvokerException e) {
	    File file = getFile(source);
	    logger.error("It was not possible to save " + file.getName() + " on node " + node.getId());
	    throw new NodeNotBootstrappedException(node.getId());
	}
    }

    private File getFile(String path) {
	URL url = this.getClass().getClassLoader().getResource(path);
	File file = new File(url.getFile());
	if (!file.exists()) {
	    logger.error(path + " not found! Should never happen!");
	    throw new IllegalStateException();
	}
	return file;
    }

    private class ScpTask implements Callable<Void> {

	String source, target;

	public ScpTask(String source, String target) {
	    this.source = source;
	    this.target = target;
	}

	@Override
	public Void call() throws Exception {
	    Scp scp = Scp.getInstance(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	    File file = getFile(source);
	    try {
		scp.sendFile(file.getAbsolutePath(), target);
	    } catch (ScpFailed e) {
		logger.error("It was not possible to save " + file.getName() + " on node " + node.getId());
		throw new NodeNotBootstrappedException(node.getId());
	    }
	    return null;
	}

    }

}
