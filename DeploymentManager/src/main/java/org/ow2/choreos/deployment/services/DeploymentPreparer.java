package org.ow2.choreos.deployment.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.selector.NodeSelector;
import org.ow2.choreos.deployment.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

public class DeploymentPreparer {
    
    private Logger logger = Logger.getLogger(DeploymentPreparer.class);

    public List<CloudNode> prepareDeployment(DeploymentRequest deploymentRequest) throws PrepareDeploymentFailedException {

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
	    waitForSshAccess(node);

	    SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
	    String serviceInstanceId = "";

	    switch (deploymentRequest.getService().getSpec().getPackageType()) {
	    case COMMAND_LINE:
		serviceInstanceId = installJar(deploymentRequest, ssh, serviceInstanceId);
		break;
	    case TOMCAT:
		serviceInstanceId = installWar(deploymentRequest, ssh, serviceInstanceId);
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
	return nodes;
    }
    
    private String installWar(DeploymentRequest deploymentRequest, SshUtil ssh, String serviceInstanceId) {
	try {
	    serviceInstanceId = ssh.runCommand(getWarCommand(deploymentRequest));
	} catch (JSchException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SshCommandFailed e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return serviceInstanceId;
    }

    private String installJar(DeploymentRequest deploymentRequest, SshUtil ssh, String serviceInstanceId) {
	try {
	    serviceInstanceId = ssh.runCommand(getJarCommand(deploymentRequest));
	} catch (JSchException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SshCommandFailed e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return serviceInstanceId;
    }

    private void waitForSshAccess(CloudNode node) {
	SshWaiter sshWaiter = new SshWaiter();
	try {
	    sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), 60);
	} catch (SshNotConnected e) {
	    e.printStackTrace();
	    ;// throw new NodeNotAccessibleException(node.getIp() +
	     // " not accessible");
	}
    }

    private String getJarCommand(DeploymentRequest deploymentRequest) {
	return "bash -c" + " 'wget http://www.ime.usp.br/~tfurtado/generate_and_apply.tgz;"
		+ "tar xf generate_and_apply.tgz;" + ". generate_and_apply.sh " + "-jar "
		+ deploymentRequest.getService().getSpec().getPackageUri() + " "
		+ deploymentRequest.getDeploymentManagerURL() + "' ";
    }

    private String getWarCommand(DeploymentRequest deploymentRequest) {
	return "bash -c" + " 'wget http://www.ime.usp.br/~tfurtado/generate_and_apply.tgz;"
		+ "tar xf generate_and_apply.tgz;" + ". generate_and_apply.sh " + "-war "
		+ deploymentRequest.getService().getSpec().getPackageUri() + " "
		+ deploymentRequest.getDeploymentManagerURL() + "' ";
    }


}
