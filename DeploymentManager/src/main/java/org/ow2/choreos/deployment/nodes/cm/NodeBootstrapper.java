/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.Timeouts;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, thiago
 * 
 */
public class NodeBootstrapper {

    // TODO save bootstrap log

    private static final int BOOTSTRAP_TRIALS = 1;
    private static String BOOTSTRAP_COMMAND = "bash -c 'wget http://www.ime.usp.br/~tfurtado/bootstrap.tgz; "
	    + "tar xf bootstrap.tgz; chmod +x bootstrap.sh; ./bootstrap.sh'";

    private Node node;
    private final int sshTimeoutInSeconds;
    private final int bootstrapTimeoutInSeconds;

    private Logger logger = Logger.getLogger(NodeBootstrapper.class);

    public NodeBootstrapper(Node node) {
	this.node = node;
	this.sshTimeoutInSeconds = getSshTimeoutInSeconds();
	this.bootstrapTimeoutInSeconds = getBootstrapTimeoutInSeconds();
    }

    private int getSshTimeoutInSeconds() {
	final String property = "LOG_IN_WITH_SSH";
	try {
	    return Integer.parseInt(Timeouts.get(property));
	} catch (NumberFormatException e) {
	    throw new IllegalStateException(property + " not configured on timeouts.properties");
	}
    }

    private int getBootstrapTimeoutInSeconds() {
	final String property = "BOOTSTRAP";
	try {
	    return Integer.parseInt(Timeouts.get(property));
	} catch (NumberFormatException e) {
	    throw new IllegalStateException(property + " not configured on timeouts.properties");
	}
    }

    public void bootstrapNode() throws NodeNotAccessibleException, NodeNotBootstrappedException {
	waitSsh();
	logger.info("Bootstrapping " + this.node.getIp());
	executeBootstrapCommand();
	logger.info("Bootstrap completed at" + this.node);
    }

    private void waitSsh() throws NodeNotAccessibleException {
	SshWaiter sshWaiter = new SshWaiter();
	try {
	    sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), sshTimeoutInSeconds);
	} catch (SshNotConnected e) {
	    throw new NodeNotAccessibleException(this.node.getIp());
	}
    }

    private void executeBootstrapCommand() throws NodeNotBootstrappedException {
	BootstrapTask task = new BootstrapTask();
	Invoker<String> invoker = new Invoker<String>(task, BOOTSTRAP_TRIALS, bootstrapTimeoutInSeconds,
		TimeUnit.SECONDS);
	try {
	    invoker.invoke();
	} catch (InvokerException e) {
	    throw new NodeNotBootstrappedException(node.getId(), e.getCause());
	}
    }

    private class BootstrapTask implements Callable<String> {

	@Override
	public String call() throws Exception {
	    SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	    try {
		String log = ssh.runCommand(BOOTSTRAP_COMMAND);
		return log;
	    } catch (JSchException e) {
		logFailMessage();
		throw new NodeNotAccessibleException(node.getId());
	    } catch (SshCommandFailed e) {
		logFailMessage();
		throw new NodeNotBootstrappedException(node.getId());
	    }
	}

	private void logFailMessage() {
	    logger.error("Node " + node.getId() + " not bootstrapped");
	}
    }

}
