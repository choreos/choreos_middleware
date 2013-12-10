package org.ow2.choreos.ee.services.preparer;

import org.ow2.choreos.ee.nodes.cm.UpdateHandler;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class InstanceCreatorUpdateHandler implements UpdateHandler {

    private final DeployableService service;
    private final String instanceId;
    private final CloudNode node;

    private ServiceInstance instance;

    public InstanceCreatorUpdateHandler(DeployableService service, String instanceId, CloudNode node) {
	this.service = service;
	this.instanceId = instanceId;
	this.node = node;
    }

    @Override
    public void handle() {
	createInstance();
	setInstanceToService();
    }

    private void createInstance() {
	instance = new ServiceInstance();
	instance.setInstanceId(instanceId);
	instance.setServiceSpec(service.getSpec());
	instance.setNode(node);
    }

    private void setInstanceToService() {
	service.addInstance(instance);
    }

}
