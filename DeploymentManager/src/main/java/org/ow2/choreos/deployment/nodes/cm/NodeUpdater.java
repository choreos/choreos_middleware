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
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

/**
 * 
 * @author leonardo, cadu, felps
 * 
 */
public class NodeUpdater {

    public static final String CHEF_SOLO_COMMAND = "sudo chef-solo -c $HOME/chef-solo/solo.rb";

    // this executor is shared among multiple updates to the same node
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private final int timeout;
    private final int trials;
    
    SshWaiter sshWaiter = new SshWaiter();

    private Logger logger = Logger.getLogger(NodeUpdater.class);

    public NodeUpdater() {
        this.timeout = TimeoutsAndTrials.get("UPDATE_TIMEOUT");
        this.trials = TimeoutsAndTrials.get("UPDATE_TRIALS");
    }

    public void update(CloudNode node) throws NodeNotUpdatedException {
        UpdateInvokerTask updateTask = new UpdateInvokerTask(node);
        Future<Void> future = singleThreadExecutor.submit(updateTask);
        checkFuture(node, future);
    }

    private void checkFuture(CloudNode node, Future<Void> future) throws NodeNotUpdatedException {
        try {
            // since the task been executed is basically the invoker invocation,
            // we hope do not get stuck here (invoker already has a timeout)
            // this is why we did not set a timeout in the "future.get()"
            future.get();
        } catch (InterruptedException e) {
            fail(node);
        } catch (ExecutionException e) {
            fail(node);
        }
    }

    private void fail(CloudNode node) throws NodeNotUpdatedException {
        NodeNotUpdatedException e = new NodeNotUpdatedException(node.getId());
        logger.error(e.getMessage());
        throw e;
    }

    private class UpdateInvokerTask implements Callable<Void> {

        CloudNode node;

        public UpdateInvokerTask(CloudNode node) {
            this.node = node;
        }

        @Override
        public Void call() throws Exception {
            ChefSoloTask task = new ChefSoloTask(node);
            Invoker<Void> invoker = new Invoker<Void>(task, trials, timeout, TimeUnit.SECONDS);
            invoker.invoke();
            return null;
        }
    }

    private class ChefSoloTask implements Callable<Void> {

        CloudNode node;

        public ChefSoloTask(CloudNode node) {
            this.node = node;
        }

        @Override
        public Void call() throws Exception {
            logger.debug("updating node " + node.getId());
            SshUtil ssh = getSsh();
            ssh.runCommand(CHEF_SOLO_COMMAND);
            return null;
        }

        private SshUtil getSsh() throws SshNotConnected {
            int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
            return sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
        }
    }

}
