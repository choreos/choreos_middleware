/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, cadu, felps
 * 
 */
public class NodeUpdater {

    // it is not the time to one update, but also the time waiting in the queue
    private static final int UPGRADE_TIMEOUT = 30;

    private Logger logger = Logger.getLogger(NodeUpdater.class);

    // this executor is shared among multiple upgrade invocations to the same
    // node
    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * Runs chef-client in a given node
     * 
     * @param node
     * @throws NodeNotUpdatedException
     *             if chef-client ends in error or if could not connect into the
     *             node
     */
    public void update(Node node) throws NodeNotUpdatedException {

	SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	ChefSoloRunner runner = new ChefSoloRunner(ssh, node.getId());
	CompletionService<Boolean> myExecutor = new ExecutorCompletionService<Boolean>(singleThreadExecutor);
	myExecutor.submit(runner);

	try {
	    Future<Boolean> taskCompleted = myExecutor.poll(UPGRADE_TIMEOUT, TimeUnit.MINUTES);
	    if (taskCompleted != null) {
		Boolean ok = taskCompleted.get();
		if (ok != null && ok.booleanValue()) {
		    logger.debug("Node " + node.getId() + " upgraded");
		} else {
		    fail(node);
		}
	    } else {
		fail(node);
	    }
	} catch (InterruptedException e) {
	    String message = "chef-solo timed out on node " + node.toString();
	    logger.error(message);
	    throw new NodeNotUpdatedException(node.getId());
	} catch (ExecutionException e) {
	    fail(node);
	}
    }

    private void fail(Node node) throws NodeNotUpdatedException {
	String message = "chef-client returned an error exit status on node " + node.toString();
	logger.error(message);
	throw new NodeNotUpdatedException(node.getId());
    }

    /**
     * Try to run chef client 5 times
     * 
     * This strategy is carried out since sometimes we try to ssh the VM when it
     * is not ready yet.
     * 
     */
    private class ChefSoloRunner implements Callable<Boolean> {

	SshUtil ssh;
	String nodeId;
	boolean ok = false;

	public ChefSoloRunner(SshUtil ssh, String nodeId) {
	    this.ssh = ssh;
	    this.nodeId = nodeId;
	}

	@Override
	public Boolean call() throws Exception {

	    logger.debug("upgrading node " + nodeId);

	    String logFile = DeploymentManagerConfiguration.get("CHEF_CLIENT_LOG");
	    if (logFile == null || logFile.isEmpty()) {
		logFile = "/tmp/chef-solo.log";
	    }

	    final String CHEF_SOLO_COMMAND = "sudo nohup bash -c 'chef-solo -c $HOME/chef-solo/solo.rb " + logFile
		    + "'";
	    final int MAX_TRIALS = 5;
	    final int SLEEPING_TIME = 5000;
	    int trials = 0;
	    ok = false;
	    boolean stop = false;

	    while (!ok && !stop) {
		try {
		    trials++;
		    ssh.runCommand(CHEF_SOLO_COMMAND);
		    ok = true;
		} catch (JSchException e) {
		    if (trials >= MAX_TRIALS) {
			stop = true;
		    }
		    sleep(SLEEPING_TIME);
		} catch (SshCommandFailed e) {
		    if (trials >= MAX_TRIALS) {
			stop = true;
		    }
		    sleep(SLEEPING_TIME);
		}
	    }

	    return ok;
	}

	private void sleep(long time) {
	    try {
		Thread.sleep(time);
	    } catch (InterruptedException e1) {
		logger.error("Exception at sleeping, should not happen");
	    }
	}
    }

}
