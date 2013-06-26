package org.ow2.choreos.deployment.services.preparer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.deployment.nodes.selector.NodeSelector;
import org.ow2.choreos.deployment.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.DeploymentRequest;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public abstract class DeploymentPreparer {

    protected DeploymentRequest deploymentRequest;

    private Logger logger = Logger.getLogger(DeploymentPreparer.class);

    public DeploymentPreparer(DeploymentRequest deploymentRequest) {
	this.deploymentRequest = deploymentRequest;
    }

    public List<CloudNode> prepareDeployment(DeploymentRequest deploymentRequest)
	    throws PrepareDeploymentFailedException {

	NodeSelector selector = NodeSelectorFactory.getFactoryInstance().getNodeSelectorInstance();
	List<CloudNode> nodes = null;
	try {
	    nodes = selector.select(deploymentRequest, deploymentRequest.getNumberOfInstances());
	    logger.info("Selected nodes to " + deploymentRequest.getService().toString() + ": " + nodes);
	} catch (NotSelectedException e) {
	    failDeployment();
	}

	if (nodes == null || nodes.isEmpty()) {
	    failDeployment();
	}

	List<ServiceInstance> instances = (deploymentRequest.getService().getServiceInstances() != null) ? deploymentRequest
		.getService().getServiceInstances() : new ArrayList<ServiceInstance>();

	for (CloudNode node : nodes) {
	    try {
		String serviceInstanceId = installOn(node);
		ServiceInstance instance = new ServiceInstance(node);
		instance.setServiceSpec(deploymentRequest.getService().getSpec());
		instance.setInstanceId(serviceInstanceId);
		instances.add(instance);
	    } catch (PrepareDeploymentFailedException e) {
		logger.error(e.getMessage());
	    }
	}

	deploymentRequest.getService().setServiceInstances(instances);

	return nodes;
    }

    private String installOn(CloudNode node) throws PrepareDeploymentFailedException {
	int timeout = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TIMEOUT");
	int trials = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TRIALS");
	String command = getCommand();
	PreparerTask task = new PreparerTask(command, node);
	Invoker<String> invoker = new Invoker<String>(task, trials, timeout, TimeUnit.SECONDS);
	String serviceInstanceId = null;
	try {
	    serviceInstanceId = invoker.invoke();
	} catch (InvokerException e) {
	    failDeploymentOn(node);
	}
	return serviceInstanceId;
    }
    
    private void failDeployment() throws PrepareDeploymentFailedException {
	String serviceName = deploymentRequest.getService().getSpec().getName();
	throw new PrepareDeploymentFailedException(serviceName);
    }

    private void failDeploymentOn(CloudNode node) throws PrepareDeploymentFailedException {
	String serviceName = deploymentRequest.getService().getSpec().getName();
	throw new PrepareDeploymentFailedException(serviceName, node);
    }

    protected abstract String getCommand();

    private class PreparerTask implements Callable<String> {

	String command;
	CloudNode node;

	public PreparerTask(String command, CloudNode node) {
	    this.command = command;
	    this.node = node;
	}

	@Override
	public String call() throws Exception {
	    SshUtil ssh = waitForSshAccess();
	    String serviceInstanceId = ssh.runCommand(command);
	    ssh.disconnect();
	    return serviceInstanceId;
	}

	private SshUtil waitForSshAccess() throws SshNotConnected {
	    SshWaiter sshWaiter = new SshWaiter();
	    int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
	    return sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
	}

    }

}
