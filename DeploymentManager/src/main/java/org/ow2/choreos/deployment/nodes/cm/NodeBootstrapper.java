/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Scp;
import org.ow2.choreos.utils.ScpFailed;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, thiago
 * 
 */
public class NodeBootstrapper {

    public static final String BOOTSTRAP_SCRIPT = "chef-solo/bootstrap.sh";
    public static final String INITIAL_NODE_JSON = "chef-solo/node.json";
    public static final String PREPARE_DEPLOYMENT_SCRIPT = "chef-solo/prepare_deployment.sh";
    public static final String CHEF_SOLO_FOLDER = "chef-solo";

    private CloudNode node;
    SshWaiter sshWaiter = new SshWaiter();

    private Logger logger = Logger.getLogger(NodeBootstrapper.class);

    public NodeBootstrapper(CloudNode node) {
	this.node = node;
    }

    public void bootstrapNode() throws NodeNotAccessibleException, NodeNotBootstrappedException {
	logger.info("Bootstrapping " + this.node.getIp());
	executeBootstrapCommand();
	saveFile(INITIAL_NODE_JSON, CHEF_SOLO_FOLDER);
	saveFile(PREPARE_DEPLOYMENT_SCRIPT, CHEF_SOLO_FOLDER);
	logger.info("Bootstrap completed at" + this.node);
    }

    private void executeBootstrapCommand() throws NodeNotBootstrappedException {
	try {
	    SshUtil ssh = getSsh();
	    String bootstrapScript = getBootStrapScript();
	    ssh.runCommand(bootstrapScript);
	    ssh.disconnect();
	} catch (SshNotConnected e) {
	    logFailMessage();
	    throw new NodeNotBootstrappedException(node.getId());
	} catch (JSchException e) {
	    logFailMessage();
	    throw new NodeNotBootstrappedException(node.getId());
	} catch (SshCommandFailed e) {
	    logFailMessage();
	    throw new NodeNotBootstrappedException(node.getId());
	}
    }

    private SshUtil getSsh() throws SshNotConnected {
	int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
	SshUtil ssh = sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
	return ssh;
    }

    private String getBootStrapScript() {
	URL scriptFile = this.getClass().getClassLoader().getResource(BOOTSTRAP_SCRIPT);
	String script = null;
	try {
	    script = FileUtils.readFileToString(new File(scriptFile.getFile()));
	} catch (IOException e) {
	    logger.error("Should not happen!", e);
	    throw new IllegalStateException();
	}
	return script;
    }

    private void logFailMessage() {
	logger.error("Node " + node.getId() + " not bootstrapped");
    }

    private void saveFile(String source, String target) {
	Scp scp = Scp.getInstance(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	File file = getFile(source);
	try {
	    scp.sendFile(file.getAbsolutePath(), target);
	} catch (ScpFailed e) {
	    logger.error("It was not possible to save " + file.getName() + " on node " + node.getId());
	    throw new IllegalStateException();
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

}
