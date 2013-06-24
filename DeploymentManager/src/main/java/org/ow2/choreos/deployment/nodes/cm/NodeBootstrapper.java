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

    private static String BOOTSTRAP_SCRIPT = "chef-solo/bootstrap.sh";
    private static String PREPARE_DEPLOYMENT_SCRIPT = "chef-solo/prepare_deployment.sh";

    private CloudNode node;

    private Logger logger = Logger.getLogger(NodeBootstrapper.class);

    public NodeBootstrapper(CloudNode node) {
	this.node = node;
    }

    public void bootstrapNode() throws NodeNotAccessibleException, NodeNotBootstrappedException {
	logger.info("Bootstrapping " + this.node.getIp());
	executeBootstrapCommand();
	savePrepareDeploymentScript();
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
	SshWaiter sshWaiter = new SshWaiter();
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

    private void savePrepareDeploymentScript() {
	Scp scp = new Scp(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	File script = getPrepareDeploymentScript();
	try {
	    scp.sendFile(script.getAbsolutePath());
	} catch (ScpFailed e) {
	    logger.error("It was not possible to save prepare_deployment.sh on node " + node.getId());
	    throw new IllegalStateException();
	}
    }

    private File getPrepareDeploymentScript() {
	URL scriptFileURL = this.getClass().getClassLoader().getResource(PREPARE_DEPLOYMENT_SCRIPT);
	File scriptFile = new File(scriptFileURL.getFile());
	if (!scriptFile.exists()) {
	    logger.error(PREPARE_DEPLOYMENT_SCRIPT + " not found! Should never happen!");
	    throw new IllegalStateException();
	}
	return scriptFile;
    }

}
