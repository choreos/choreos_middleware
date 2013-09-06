package org.ow2.choreos.deployment.services;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.NodeCreator;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdater;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdaters;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public class InfrastructureMonitoringConfigurator {

    private CloudNode infrastructureMonitoringNode;

    private Logger logger = Logger.getLogger(InfrastructureMonitoringConfigurator.class);

    private SshWaiter sshWaiter = new SshWaiter();

    public CloudNode getConfiguredInfrastructureMonitoringNode() {
	infrastructureMonitoringNode = createInfrastructureMonitoringNode();

	prepareInfrastructureMonitoringNode();
	updateInfrastructureMonitoringNode();

	return infrastructureMonitoringNode;
    }

    private CloudNode createInfrastructureMonitoringNode() {
	String cloudProviderProperty = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
	NodeCreator nodeCreator = new NodeCreator(CloudProviderFactory.getInstance(cloudProviderProperty));
	CloudNode infrastructureMonitoringNode = null;
	try {
	    infrastructureMonitoringNode = nodeCreator.createBootstrappedNode(new NodeSpec());
	} catch (NodeNotCreatedException e) {
	    logger.warn("Could not set up infrastruture monitoring");
	}
	return infrastructureMonitoringNode;
    }

    private void prepareInfrastructureMonitoringNode() {
	InfrastructureMonitoringPreparerTask task = new InfrastructureMonitoringPreparerTask();
	Invoker<String> invoker = new Invoker<String>(task, 3, 10, 0, TimeUnit.SECONDS);
	try {
	    invoker.invoke();
	} catch (InvokerException e) {
	    logger.warn("Could not add glimpse recipe to node " + infrastructureMonitoringNode.getId());
	}
    }

    private void updateInfrastructureMonitoringNode() {
	NodeUpdater nodeUpdater = NodeUpdaters.getUpdaterFor(infrastructureMonitoringNode);
	try {
	    nodeUpdater.update();
	} catch (NodeNotUpdatedException e) {
	    logger.warn("Could not run chef-solo on node " + infrastructureMonitoringNode.getId());
	}
    }

    /**
     * 
     * @author thiago
     * 
     *         Installs glimpse and platform into a cloud node to be used for
     *         reconfiguration purpose
     */
    private class InfrastructureMonitoringPreparerTask implements Callable<String> {

	private final static String monitoringInstallCommand = ". chef-solo/add_recipe_to_node.sh monitoring";
	private final static String harakiriUninstallCommand = ". chef-solo/remove_recipe_from_node.sh harakiri";

	@Override
	public String call() throws Exception {
	    SshUtil ssh = getSsh();
	    String output = ssh.runCommand(harakiriUninstallCommand);
	    output += "\n" + ssh.runCommand(monitoringInstallCommand);
	    return output;
	}

	protected SshUtil getSsh() throws SshNotConnected {
	    int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
	    return sshWaiter.waitSsh(infrastructureMonitoringNode.getIp(), infrastructureMonitoringNode.getUser(),
		    infrastructureMonitoringNode.getPrivateKeyFile(), timeout);
	}
    }

}
