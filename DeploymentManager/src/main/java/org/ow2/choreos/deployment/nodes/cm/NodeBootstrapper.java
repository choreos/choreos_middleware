/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, cadu, felps
 * 
 */
public class NodeBootstrapper {

    public static final int SSH_TIMEOUT_IN_SECONDS = 250;

    private int sshTimeoutInSeconds;

    private Logger logger = Logger.getLogger(NodeBootstrapper.class);

    private Node node;

    public NodeBootstrapper(Node node) {
	this.node = node;
	this.sshTimeoutInSeconds = SSH_TIMEOUT_IN_SECONDS;
    }

    public void bootstrapNode() throws NodeNotAccessibleException, KnifeException, NodeNotBootstrappedException {

	SshWaiter sshWaiter = new SshWaiter();
	try {
	    sshWaiter.waitSsh(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile(),
		    this.sshTimeoutInSeconds);
	} catch (SshNotConnected e) {
	    throw new NodeNotAccessibleException(this.node.getIp() + " not accessible");
	}

	logger.info("Bootstrapping " + this.node.getIp());

	String bootstrapCommand = "bash -c " + "'wget http://www.ime.usp.br/~tfurtado/bootstrap.tgz; "
		+ "tar xf bootstrap.tgz; " + ". bootstrap.sh' ";
	SshUtil ssh = new SshUtil(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile());
	try {
	    ssh.runCommand(bootstrapCommand);
	} catch (JSchException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SshCommandFailed e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	logger.info("Bootstrap completed at" + this.node);
    }
}
