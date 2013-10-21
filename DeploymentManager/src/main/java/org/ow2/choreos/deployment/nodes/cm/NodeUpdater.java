/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerFactory;
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

    public static final String CHEF_SOLO_COMMAND = "sudo chef-solo -c $HOME/chef-solo/solo.rb >> /tmp/chef-solo-update";
    private static final String TASK_NAME = "NODE_UPDATE";
    
    // this executor is shared among multiple updates to the same node
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private CloudNode node;
    private UpdateHandlers handlers = new UpdateHandlers();

    SshWaiter sshWaiter = new SshWaiter();

    private Logger logger = Logger.getLogger(NodeUpdater.class);

    public NodeUpdater(CloudNode node) {
        this.node = node;
    }

    public void addHandler(UpdateHandler handler) {
        handlers.addHandler(handler);
    }

    public void update() throws NodeNotUpdatedException {
        UpdateInvokerTask updateTask = new UpdateInvokerTask();
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

        @Override
        public Void call() throws Exception {
            ChefSoloTask task = new ChefSoloTask();
            InvokerFactory<Void> factory = new InvokerFactory<Void>();
            Invoker<Void> invoker = factory.geNewInvokerInstance(TASK_NAME, task);
            invoker.invoke();
            return null;
        }
    }

    private class ChefSoloTask implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            logger.debug("updating node " + node.getId());
            handlers.fetchHandlers();
            SshUtil ssh = getSsh();
            ssh.runCommand(CHEF_SOLO_COMMAND);
            // if ssh did not throw an exception, then:
            processHandlers();
            return null;
        }

        private SshUtil getSsh() throws SshNotConnected {
            int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
            return sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
        }

        private void processHandlers() {
            for (UpdateHandler h : handlers.getHandlersForProcessing())
                h.handle();
        }
    }

}
