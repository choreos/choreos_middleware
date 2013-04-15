package org.ow2.choreos.deployment.nodes.cm;

import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

/**
 * Considers that a node is bootstrapped if the /etc/chef folder contains the files:
 * client.pem  client.rb  first-boot.json  validation.pem
 * 
 * @author leonardo
 *
 */
public class BootstrapChecker {

	public boolean isBootstrapped(Node node) {
		
		SshWaiter sshWaiter = new SshWaiter();
		SshUtil ssh = null;
		try {
			ssh = sshWaiter.waitSsh(node.getIp(), 
					node.getUser(), node.getPrivateKeyFile(), NodeBootstrapper.SSH_TIMEOUT_IN_SECONDS);
		} catch (SshNotConnected e) {
			return false;
		}
		
		String result = "";
		try {
			result = ssh.runCommand("ls /etc/chef");
		} catch (JSchException e) {
			return false;
		} catch (SshCommandFailed e) {
			return false;
		}
		
		if (result.contains("client.pem") && result.contains("client.rb")
				&& result.contains("first-boot.json")
				&& result.contains("validation.pem")) {
			return true;
		} else {
			return false;
		}
	}
}
