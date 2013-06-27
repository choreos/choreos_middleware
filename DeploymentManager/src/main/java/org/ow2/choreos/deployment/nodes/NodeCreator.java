/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.cm.NodeNotBootstrappedException;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

/**
 * Creates a new node and bootstrapped it
 * 
 * @author leonardo
 * 
 */
public class NodeCreator {

    private CloudProvider cp;

    public NodeCreator(CloudProvider cp) {
        this.cp = cp;
    }

    public CloudNode createBootstrappedNode(NodeSpec nodeSpec) throws NodeNotCreatedException {
        CloudNode node = createCloudNode(nodeSpec);
        waitFirstSsh(node);
        bootstrapNode(node);
        return node;
    }

    private CloudNode createCloudNode(NodeSpec nodeSpec) throws NodeNotCreatedException {
        CloudNodeCreationTask task = new CloudNodeCreationTask(nodeSpec);
        int timeout = TimeoutsAndTrials.get("NODE_CREATION_TIMEOUT");
        int trials = TimeoutsAndTrials.get("NODE_CREATION_TRIALS");
        Invoker<CloudNode> invoker = new Invoker<CloudNode>(task, trials, timeout, TimeUnit.SECONDS);
        try {
            CloudNode node = invoker.invoke();
            return node;
        } catch (InvokerException e) {
            throw new NodeNotCreatedException();
        }
    }

    private void waitFirstSsh(CloudNode node) throws NodeNotCreatedException {
        int timeout = TimeoutsAndTrials.get("FIRST_CONNECT_SSH_TIMEOUT");
        SshWaiter sshWaiter = new SshWaiter();
        try {
            SshUtil ssh = sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
            ssh.disconnect();
        } catch (SshNotConnected e) {
            throw new NodeNotCreatedException();
        }
    }

    private void bootstrapNode(CloudNode node) throws NodeNotCreatedException {
        BootstrapTask task = new BootstrapTask(node);
        int timeout = TimeoutsAndTrials.get("BOOTSTRAP_TIMEOUT");
        int trials = TimeoutsAndTrials.get("BOOTSTRAP_TRIALS");
        Invoker<Void> invoker = new Invoker<Void>(task, trials, timeout, TimeUnit.SECONDS);
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            throw new NodeNotCreatedException();
        }
    }

    private class CloudNodeCreationTask implements Callable<CloudNode> {

        NodeSpec nodeSpec;

        CloudNodeCreationTask(NodeSpec nodeSpec) {
            this.nodeSpec = nodeSpec;
        }

        @Override
        public CloudNode call() throws Exception {
            CloudNode node = cp.createNode(nodeSpec);
            return node;
        }
    }

    private class BootstrapTask implements Callable<Void> {

        CloudNode node;

        BootstrapTask(CloudNode node) {
            this.node = node;
        }

        @Override
        public Void call() throws Exception {
            try {
                NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
                bootstrapper.bootstrapNode();
            } catch (NodeNotBootstrappedException e) {
                throw new NodeNotCreatedException(node.getId());
            } catch (NodeNotAccessibleException e) {
                throw new NodeNotCreatedException(node.getId());
            }
            return null;
        }
    }

}
