package org.ow2.choreos.deployment.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.selector.NodeSelector;
import org.ow2.choreos.deployment.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.DeploymentRequest;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

public class DeploymentPreparer {
    
    SshUtil ssh;

    private Logger logger = Logger.getLogger(DeploymentPreparer.class);

    public List<CloudNode> prepareDeployment(DeploymentRequest deploymentRequest)
	    throws PrepareDeploymentFailedException {

	NodeSelector selector = NodeSelectorFactory.getFactoryInstance().getNodeSelectorInstance();
	List<CloudNode> nodes = null;
	try {
	    nodes = selector.select(deploymentRequest, deploymentRequest.getNumberOfInstances());
	    logger.info("Selected nodes to " + deploymentRequest.getService().toString() + ": " + nodes);
	} catch (NotSelectedException e) {
	    throw new PrepareDeploymentFailedException(deploymentRequest.getService().toString());
	}

	if (nodes == null || nodes.isEmpty()) {
	    throw new PrepareDeploymentFailedException(deploymentRequest.getService().toString());
	}

	List<ServiceInstance> instances = (deploymentRequest.getService().getServiceInstances() != null) ? deploymentRequest
		.getService().getServiceInstances() : new ArrayList<ServiceInstance>();

	for (CloudNode node : nodes) {
	    try {
		ssh = waitForSshAccess(node);
	    } catch (NodeNotAccessibleException e) {
		throw new PrepareDeploymentFailedException(deploymentRequest.getRecipeName());
	    }

	    String serviceInstanceId = "";
	    switch (deploymentRequest.getService().getSpec().getPackageType()) {
	    case COMMAND_LINE:
		serviceInstanceId = installJar(deploymentRequest, serviceInstanceId);
		break;
	    case TOMCAT:
		serviceInstanceId = installWar(deploymentRequest, serviceInstanceId);
		break;
	    case EASY_ESB:
		break;
	    default:
		break;
	    }

	    ServiceInstance instance = new ServiceInstance(node);
	    instance.setServiceSpec(deploymentRequest.getService().getSpec());
	    instance.setInstanceId(serviceInstanceId);
	    instances.add(instance);
	}

	deploymentRequest.getService().setServiceInstances(instances);
	ssh.disconnect();
	return nodes;
    }

    private String installWar(DeploymentRequest deploymentRequest, String serviceInstanceId) {
	String command = getWarCommand(deploymentRequest);
	try {
	    serviceInstanceId = ssh.runCommand(command);
	} catch (JSchException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SshCommandFailed e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return serviceInstanceId;
    }

    private String installJar(DeploymentRequest deploymentRequest, String serviceInstanceId) {
	String command = getJarCommand(deploymentRequest);
	try {
	    serviceInstanceId = ssh.runCommand(command);
	} catch (JSchException e) {
	    e.printStackTrace();
	} catch (SshCommandFailed e) {
	    e.printStackTrace();
	}
	return serviceInstanceId;
    }

    private SshUtil waitForSshAccess(CloudNode node) throws NodeNotAccessibleException {
	SshWaiter sshWaiter = new SshWaiter();
	try {
	    return sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), 60);
	} catch (SshNotConnected e) {
	    throw new NodeNotAccessibleException(node.getIp());
	}
    }

    private String getJarCommand(DeploymentRequest deploymentRequest) {
	return ". prepare_deployment.sh " + "-jar " + deploymentRequest.getService().getSpec().getPackageUri() + " "
		+ deploymentRequest.getDeploymentManagerURL();
    }

    private String getWarCommand(DeploymentRequest deploymentRequest) {
	return ". prepare_deployment.sh " + "-war " + deploymentRequest.getService().getSpec().getPackageUri() + " "
		+ deploymentRequest.getDeploymentManagerURL();
    }

}
