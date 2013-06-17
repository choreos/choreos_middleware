/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

/**
 * Considers that a node is bootstrapped if the /etc/chef folder contains the
 * files: client.pem client.rb first-boot.json validation.pem
 * 
 * @author leonardo
 * 
 */
public class BootstrapChecker {

    private static final int SSH_TIMEOUT_IN_SECONDS = 250;

    public boolean isBootstrapped(CloudNode node) {

	SshWaiter sshWaiter = new SshWaiter();
	SshUtil ssh = null;
	try {
	    ssh = sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), SSH_TIMEOUT_IN_SECONDS);
	} catch (SshNotConnected e) {
	    return false;
	}

	String result = "";
	try {
	    result = ssh.runCommand("ls $HOME/chef-solo");
	} catch (JSchException e) {
	    return false;
	} catch (SshCommandFailed e) {
	    return false;
	}

	if (result.contains("solo.rb") && result.contains("node.json") && result.contains("cookbooks")) {
	    return true;
	} else {
	    return false;
	}
    }
}
