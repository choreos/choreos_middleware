package org.ow2.choreos.ee.services.preparer;

import org.ow2.choreos.ee.nodes.cm.UpdateHandler;
import org.ow2.choreos.ee.services.registry.DeployedServicesRegistry;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class InstanceRemoverUpdateHandler implements UpdateHandler {

    private ServiceInstance instance;
    private String serviceUUID;

    public InstanceRemoverUpdateHandler(String serviceUUID, ServiceInstance instance) {
	this.instance = instance;
	this.serviceUUID = serviceUUID;
    }

    @Override
    public void handle() {
	DeployedServicesRegistry reg = DeployedServicesRegistry.getInstance();
	DeployableService service = reg.getService(serviceUUID);
	service.getSelectedNodes().remove(instance.getNode());
	service.removeInstance(instance);
    }
}
