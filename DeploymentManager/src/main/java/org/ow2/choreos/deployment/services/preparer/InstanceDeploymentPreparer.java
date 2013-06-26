package org.ow2.choreos.deployment.services.preparer;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public class InstanceDeploymentPreparer {

    private DeploymentRequest deploymentRequest;
    private CloudNode node;
    private String serviceName;
    
    public InstanceDeploymentPreparer(DeploymentRequest deploymentRequest, CloudNode node) {
	this.deploymentRequest = deploymentRequest;
	this.node = node;
	this.serviceName = deploymentRequest.getService().getSpec().getName();
    }

    public String prepareDeployment() throws PrepareDeploymentFailedException {
	int timeout = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TIMEOUT");
	int trials = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TRIALS");
	String command = getCommand();
	System.out.println(command);
	PreparerTask task = new PreparerTask(command, node);
	Invoker<String> invoker = new Invoker<String>(task, trials, timeout, TimeUnit.SECONDS);
	String serviceInstanceId = null;
	try {
	    serviceInstanceId = invoker.invoke();
	} catch (InvokerException e) {
	    throw new PrepareDeploymentFailedException(serviceName, node);
	}
	return serviceInstanceId;
    }
    
    private String getCommand() {
	String packageUri = deploymentRequest.getService().getSpec().getPackageUri();
	String cookbookTemplateName = deploymentRequest.getService().getSpec().getPackageType().getExtension();
	return ". chef-solo/prepare_deployment.sh " + packageUri + " " + cookbookTemplateName;
    }

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
