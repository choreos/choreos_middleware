package org.ow2.choreos.ee.services.preparer;

import org.ow2.choreos.ee.nodes.cm.UpdateHandler;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class InstanceRemoverUpdateHandler implements UpdateHandler {

    private ServiceInstance instance;
    private DeployableService service;

    public InstanceRemoverUpdateHandler(DeployableService service, ServiceInstance instance) {
	this.instance = instance;
	this.service = service;
    }

    @Override
    public void handle() {
	service.getSelectedNodes().remove(instance.getNode());
	service.removeInstance(instance);
    }
}
