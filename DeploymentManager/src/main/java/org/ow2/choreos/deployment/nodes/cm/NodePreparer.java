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
     * @param cookbookTemplateName ex: jar, war, cd
     * @return the id of the prepared instance
     * @throws NodeNotPreparedException
     */
    public String prepareNode(String packageUri, String cookbookTemplateName) throws NodeNotPreparedException {
        PreparerInvokerTask task = new PreparerInvokerTask(packageUri, cookbookTemplateName);
        Future<String> future = singleThreadExecutor.submit(task);
        String instanceId = checkFuture(future);
        return instanceId;
    }
    
    private String checkFuture(Future<String> future) throws NodeNotPreparedException {
        try {
            // since the task been executed is basically the invoker invocation,
            // we hope do not get stuck here (invoker already has a timeout)
            // this is why we did not set a timeout in the "future.get()"
            String instanceId = future.get();
            if (instanceId == null)
                return fail();
            return instanceId;
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

    private class PreparerInvokerTask implements Callable<String> {

        String packageUri; 
        String cookbookTemplateName;

        public PreparerInvokerTask(String packageUri, String cookbookTemplateName) {
            this.packageUri = packageUri;
            this.cookbookTemplateName = cookbookTemplateName;
        }

        @Override
        public String call() throws Exception {
            PrepareNodeTask task = new PrepareNodeTask(packageUri, cookbookTemplateName);
            Invoker<String> invoker = new Invoker<String>(task, trials, timeout, TimeUnit.SECONDS);
            String instanceId = invoker.invoke();
            System.out.println(instanceId);
            return instanceId;
        }
    }

    private class PrepareNodeTask implements Callable<String> {

        String packageUri; 
        String cookbookTemplateName;

        public PrepareNodeTask(String packageUri, String cookbookTemplateName) {
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

        private SshUtil getSsh() throws SshNotConnected {
            int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
            return sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
        }

        private String getCommand() {
            return ". chef-solo/prepare_deployment.sh " + packageUri + " " + cookbookTemplateName;
        }
    }

}