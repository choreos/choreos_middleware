/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

/**
 * 
 * @author leonardo
 * 
 */
public class NodePreparer {

    // this executor is shared among multiple executions to the same node
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private CloudNode node;
    private final int timeout;
    private final int trials;

    SshWaiter sshWaiter = new SshWaiter();

    private Logger logger = Logger.getLogger(NodePreparer.class);

    public NodePreparer(CloudNode node) {
	this.node = node;
	this.timeout = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TIMEOUT");
	this.trials = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TRIALS");
    }

    /**
     * 
     * @param packageUri
     * @param cookbookTemplateName
     *            ex: jar, war, cd
     * @return the id of the prepared instance
     * @throws NodeNotPreparedException
     */
    public String prepareNodeForDeployment(String packageUri, String cookbookTemplateName) throws NodeNotPreparedException {
	DeploymentPreparerInvokerTask task = new DeploymentPreparerInvokerTask(packageUri, cookbookTemplateName);
	Future<String> future = singleThreadExecutor.submit(task);
	String instanceId = checkFuture(future);
	return instanceId;
    }

    public void prepareNodeForUndeployment(String instanceId) throws NodeNotPreparedException {
	UndeploymentPreparerInvokerTask task = new UndeploymentPreparerInvokerTask(instanceId);
	Future<String> future = singleThreadExecutor.submit(task);
	checkFuture(future);
    }

    private String checkFuture(Future<String> future) throws NodeNotPreparedException {
	try {
	    // since the task been executed is basically the invoker invocation,
	    // we hope do not get stuck here (invoker already has a timeout)
	    // this is why we did not set a timeout in the "future.get()"
	    String result = future.get();
	    if (result == null)
		return fail();
	    return result;
	} catch (InterruptedException e) {
	    return fail();
	} catch (ExecutionException e) {
	    return fail();
	}
    }

    private String fail() throws NodeNotPreparedException {
	NodeNotPreparedException e = new NodeNotPreparedException(node.getId());
	logger.error(e.getMessage());
	throw e;
    }

    private class DeploymentPreparerInvokerTask implements Callable<String> {

	String packageUri;
	String cookbookTemplateName;

	public DeploymentPreparerInvokerTask(String packageUri, String cookbookTemplateName) {
	    this.packageUri = packageUri;
	    this.cookbookTemplateName = cookbookTemplateName;
	}

	@Override
	public String call() throws Exception {
	    DeploymentPrepareNodeTask task = new DeploymentPrepareNodeTask(packageUri, cookbookTemplateName);
	    Invoker<String> invoker = new Invoker<String>(task, trials, timeout, 0, TimeUnit.SECONDS);
	    String instanceId = invoker.invoke();
	    return instanceId;
	}
    }

    private class UndeploymentPreparerInvokerTask implements Callable<String> {

	private String instanceId;

	/**
	 * @param instanceId
	 */
	public UndeploymentPreparerInvokerTask(String instanceId) {
	    this.instanceId = instanceId;
	}

	@Override
	public String call() throws Exception {
	    UndeploymentPrepareNodeTask task = new UndeploymentPrepareNodeTask(instanceId);
	    Invoker<String> invoker = new Invoker<String>(task, trials, timeout, 0, TimeUnit.SECONDS);
	    String result = invoker.invoke();
	    return result;
	}
    }

    private abstract class AbstractPreparerInvokerTask {
	protected SshUtil getSsh() throws SshNotConnected {
	    int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
	    return sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
	}
    }

    private class DeploymentPrepareNodeTask extends AbstractPreparerInvokerTask implements Callable<String> {

	String packageUri;
	String cookbookTemplateName;

	public DeploymentPrepareNodeTask(String packageUri, String cookbookTemplateName) {
	    this.packageUri = packageUri;
	    this.cookbookTemplateName = cookbookTemplateName;
	}

	@Override
	public String call() throws Exception {
	    SshUtil ssh = getSsh();
	    String command = getCommand();
	    String instanceId = ssh.runCommand(command);
	    return instanceId;
	}

	private String getCommand() {
	    return ". chef-solo/prepare_deployment.sh " + packageUri + " " + cookbookTemplateName;
	}
    }

    private class UndeploymentPrepareNodeTask extends AbstractPreparerInvokerTask implements Callable<String> {

	private String instanceId;

	public UndeploymentPrepareNodeTask(String instanceId) {
	    this.instanceId = instanceId;
	}

	@Override
	public String call() throws Exception {
	    SshUtil ssh;
	    String result = "";
	    try {
		ssh = getSsh();
		String command = getCommand();
		result = ssh.runCommand(command);
	    } catch (Exception e) {
		throw e;
	    }
	    return result;
	}

	private String getCommand() {
	    return ". chef-solo/prepare_undeployment.sh " + instanceId;
	}
    }

}
