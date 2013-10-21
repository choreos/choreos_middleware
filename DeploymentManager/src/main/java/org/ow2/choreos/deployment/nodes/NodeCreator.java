/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;


import java.util.concurrent.Callable;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.deployment.nodes.cm.NodeNotBootstrappedException;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.invoker.InvokerFactory;
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

    private static final String TASK_NAME = "BOOTSTRAP";
    
    private CloudProvider cp;

    public NodeCreator() {
        String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
	this.cp = CloudProviderFactory.getFactoryInstance().getCloudProviderInstance(cloudProviderType);
    }

    public CloudNode createBootstrappedNode(NodeSpec nodeSpec) throws NodeNotCreatedException {
	CloudNode node = cp.createNode(nodeSpec);
	waitFirstSsh(node);
	bootstrapNode(node);
	return node;
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
	InvokerFactory<Void> factory = new InvokerFactory<Void>();
	Invoker<Void> invoker = factory.geNewInvokerInstance(TASK_NAME, task);
	try {
	    invoker.invoke();
	} catch (InvokerException e) {
	    throw new NodeNotCreatedException();
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
