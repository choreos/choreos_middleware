/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.cloudprovider.FixedCloudProvider;
import org.ow2.choreos.deployment.nodes.cm.NodeUpgrader;
import org.ow2.choreos.deployment.nodes.cm.NodeUpgraderFactory;
import org.ow2.choreos.deployment.nodes.selector.NodeSelector;
import org.ow2.choreos.deployment.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpgradedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, furtado
 * 
 */
public class NPMImpl implements NodePoolManager {

    private Logger logger = Logger.getLogger(NPMImpl.class);

    private CloudProvider cloudProvider;
    private NodeRegistry nodeRegistry;
    private NodeCreator nodeCreator;
    private IdlePool idlePool;

    /**
     * The CloudProvider used is the one configured in the properties file
     * 
     * @return
     */
    public static NodePoolManager getNewInstance() {

	String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
	return new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
    }

    public NPMImpl(CloudProvider provider) {

	int poolSize = 0;
	try {
	    poolSize = Integer.parseInt(DeploymentManagerConfiguration.get("IDLE_POOL_SIZE"));
	} catch (NumberFormatException e) {
	    ; // no problem, poolSize is zero
	}

	cloudProvider = provider;
	nodeRegistry = NodeRegistry.getInstance();
	nodeCreator = new NodeCreator(cloudProvider, true);
	idlePool = IdlePool.getInstance(poolSize, nodeCreator);
    }

    public NPMImpl(CloudProvider provider, NodeCreator creator, IdlePool pool) {
	cloudProvider = provider;
	nodeRegistry = NodeRegistry.getInstance();
	nodeCreator = creator;
	idlePool = pool;
    }

    @Override
    public Node createNode(NodeSpec nodeSpec) throws NodeNotCreatedException {

	Node node = new Node();
	try {
	    node = nodeCreator.create(nodeSpec);
	    nodeRegistry.putNode(node);
	    idlePool.fillPool(); // we want the pool to be always filled
				 // whenever requests are coming
	} catch (NodeNotCreatedException e1) {
	    // if node creation has failed, let's retrieve a node from the pool
	    // wait for a new node would take too much time!
	    // TODO: maybe the failed node only took too much time to be ready
	    // in such situation, this node could go to the pool!
	    try {
		logger.warn("*** Node creation failed, let's retrieve a node from the pool ***");
		node = idlePool.retriveNode();
		nodeRegistry.putNode(node);
		idlePool.fillPool();
	    } catch (NodeNotCreatedException e2) {
		// OK, now we give up =/
		throw new NodeNotCreatedException(node.getId());
	    }
	}
	return node;
    }

    @Override
    public List<Node> getNodes() {

	if (this.cloudProvider.getProviderName() == FixedCloudProvider.FIXED_CLOUD_PROVIDER) {
	    return this.cloudProvider.getNodes();
	} else {
	    return nodeRegistry.getNodes();
	}
    }

    @Override
    public Node getNode(String nodeId) throws NodeNotFoundException {
	if (this.cloudProvider.getProviderName() == FixedCloudProvider.FIXED_CLOUD_PROVIDER) {
	    return this.cloudProvider.getNode(nodeId);
	} else {
	    return nodeRegistry.getNode(nodeId);
	}
    }

    @Override
    public List<Node> prepareDeployment(DeploymentRequest deploymentRequest) throws PrepareDeploymentFailedException {

	NodeSelector selector = NodeSelectorFactory.getFactoryInstance().getNodeSelectorInstance();
	List<Node> nodes = null;
	try {
	    nodes = selector.select(deploymentRequest, deploymentRequest.getNumberOfInstances());
	    logger.info("Selected nodes to " + deploymentRequest.getService().toString() + ": " + nodes);
	} catch (NotSelectedException e) {
	    throw new PrepareDeploymentFailedException(deploymentRequest.getService().toString());
	}

	if (nodes == null || nodes.isEmpty()) {
	    throw new PrepareDeploymentFailedException(deploymentRequest.getService().toString());
	}

	for (Node node : nodes) {
	    // ssh to generate recipe
	    logger.info("Going to copy and run script into node " + node);
	    SshWaiter sshWaiter = new SshWaiter();
	    try {
		sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), 60);
	    } catch (SshNotConnected e) {
		e.printStackTrace();
		;// throw new NodeNotAccessibleException(node.getIp() +
		 // " not accessible");
	    }

	    SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	    logger.info("SSHzando...");
	    switch (deploymentRequest.getService().getSpec().getPackageType()) {
	    case COMMAND_LINE:
		try {
		    ssh.runCommand(getJarCommand(deploymentRequest));
		} catch (JSchException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (SshCommandFailed e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		break;
	    case TOMCAT:

		try {
		    String r = ssh.runCommand(getWarCommand(deploymentRequest));
		    logger.info("returned ssh res " + r);
		} catch (JSchException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (SshCommandFailed e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		break;
	    default:
		break;
	    }
	}

	return nodes;
    }

    private String getJarCommand(DeploymentRequest deploymentRequest) {
	return "nohup bash -c" + " 'wget http://valinhos.ime.usp.br:54080/choreos/generate_and_apply.tgz;"
		+ "tar xf generate_and_apply.tgz;" + ". generate_and_apply.sh " + "-jar "
		+ deploymentRequest.getService().getSpec().getPackageUri() + " "
		+ deploymentRequest.getDeploymentManagerURL() + "'";
    }

    private String getWarCommand(DeploymentRequest deploymentRequest) {
	return "nohup bash -c" + " 'wget http://valinhos.ime.usp.br:54080/choreos/generate_and_apply.tgz;"
		+ "tar xf generate_and_apply.tgz;" + ". generate_and_apply.sh " + "-war "
		+ deploymentRequest.getService().getSpec().getPackageUri() + " "
		+ deploymentRequest.getDeploymentManagerURL() + "'";
    }

    @Override
    public void updateNode(String nodeId) throws NodeNotUpgradedException, NodeNotFoundException {

	Node node = this.getNode(nodeId);
	NodeUpgrader upgrader = NodeUpgraderFactory.getInstance(nodeId);
	upgrader.upgradeNodeConfiguration(node);
    }

    @Override
    public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException {

	this.cloudProvider.destroyNode(nodeId);
	this.nodeRegistry.deleteNode(nodeId);
    }

    @Override
    public void destroyNodes() throws NodeNotDestroyed {

	List<Thread> trds = new ArrayList<Thread>();
	List<NodeDestroyer> destroyers = new ArrayList<NodeDestroyer>();

	for (Node node : this.getNodes()) {
	    NodeDestroyer destroyer = new NodeDestroyer(node, this.cloudProvider);
	    Thread trd = new Thread(destroyer);
	    destroyers.add(destroyer);
	    trds.add(trd);
	    trd.start();
	}

	Concurrency.waitThreads(trds);

	for (NodeDestroyer destroyer : destroyers) {
	    if (destroyer.isOK()) {
		this.nodeRegistry.deleteNode(destroyer.getNode().getId());
	    } else {
		throw new NodeNotDestroyed(destroyer.getNode().getId());
	    }
	}
    }
}
