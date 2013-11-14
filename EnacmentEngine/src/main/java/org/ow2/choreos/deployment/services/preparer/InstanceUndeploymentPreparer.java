package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.deployment.nodes.cm.NodeNotPreparedException;
import org.ow2.choreos.deployment.nodes.cm.NodePreparer;
import org.ow2.choreos.deployment.nodes.cm.NodePreparers;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdater;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdaters;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class InstanceUndeploymentPreparer {

    private ServiceInstance instance;
    private CloudNode node;
    private String serviceUUID;

    public InstanceUndeploymentPreparer(String serviceUUID, ServiceInstance serviceInstance) {
	this.serviceUUID = serviceUUID;
	this.instance = serviceInstance;
	this.node = instance.getNode();
    }

    public void prepareUndeployment() throws PrepareUndeploymentFailedException {
	runUndeploymentPrepare();
	scheduleHandler();
    }

    private void runUndeploymentPrepare() throws PrepareUndeploymentFailedException {
	NodePreparer nodePreparer = NodePreparers.getPreparerFor(node);
	try {
	    nodePreparer.prepareNodeForUndeployment(instance.getInstanceId());
	} catch (NodeNotPreparedException e1) {
	    throw new PrepareUndeploymentFailedException();
	}
    }

    private void scheduleHandler() {
	InstanceRemoverUpdateHandler handler = new InstanceRemoverUpdateHandler(serviceUUID, instance);
	NodeUpdater nodeUpdater = NodeUpdaters.getUpdaterFor(node);
	nodeUpdater.addHandler(handler);

    }

}
