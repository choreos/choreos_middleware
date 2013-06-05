/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chef;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

public class ChefNodeNameRetriever {

    private static final String CHEF_NAME_SCRIPT = "chef/my_chef_name.sh";

    private Logger logger = Logger.getLogger(ChefNodeNameRetriever.class);

    private String getScript() {

	URL scriptFile = this.getClass().getClassLoader().getResource(CHEF_NAME_SCRIPT);
	String command = null;
	try {
	    command = FileUtils.readFileToString(new File(scriptFile.getFile()));
	} catch (IOException e) {
	    logger.error("Should not happen!", e);
	}
	return command;
    }

    /**
     * Connects to the node via ssh and returns the chef-node name.
     * 
     * @param host
     *            hostname or IP
     * @param user
     *            ssh connection
     * @param pKeyPath
     *            path to the file containing the private key to be used to the
     *            ssh connection
     * @return chef node name, to be used on chef commands to handle the node on
     *         <code>host</code>
     * @throws JSchException
     *             if it is not possible to connect on <code>host</code>
     */
    public String getChefNodeName(String host, String user, String pKeyPath) throws JSchException, SshCommandFailed {

	String script = getScript();
	SshUtil ssh = new SshUtil(host, user, pKeyPath);
	String chefNodeName = ssh.runCommand(script, true);

	if (chefNodeName == null || chefNodeName.isEmpty())
	    chefNodeName = getHostname(ssh);
	chefNodeName = chefNodeName.replace("\n", "").trim();

	return chefNodeName;
    }

    private String getHostname(SshUtil ssh) throws JSchException, SshCommandFailed {

	return ssh.runCommand("hostname");
    }

    /**
     * 
     * @param bootstrapLog
     * @throws IllegalArgumentException
     *             if could not retrieve chef node name
     * @return
     */
    public String retrieveChefNodeNameFromBootstrapLog(String bootstrapLog) {

	Pattern pattern = Pattern.compile("Creating a new client identity for (.*) using the validator key");
	Matcher matcher = pattern.matcher(bootstrapLog);

	if (matcher.find()) {
	    String chefNodeName = matcher.group(1);
	    return chefNodeName;
	} else {
	    throw new IllegalArgumentException("Could not extract chef node name from bootstrap log.");
	}
    }

}
