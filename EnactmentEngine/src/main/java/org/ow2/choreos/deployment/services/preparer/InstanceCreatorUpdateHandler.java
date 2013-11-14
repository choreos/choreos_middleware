package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.deployment.nodes.cm.UpdateHandler;
import org.ow2.choreos.deployment.services.registry.DeployedServicesRegistry;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class InstanceCreatorUpdateHandler implements UpdateHandler {

    private final String serviceUUID;
    private final String instanceId;
    private final DeployableServiceSpec spec;
    private final CloudNode node;
    
    private ServiceInstance instance;
    
    public InstanceCreatorUpdateHandler(String serviceId, String instanceId, DeployableServiceSpec spec, CloudNode node) {
        this.serviceUUID = serviceId;
        this.instanceId = instanceId;
        this.spec = spec;
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
        instance.setServiceSpec(spec);
        instance.setNode(node);
    }

    private void setInstanceToService() {
        DeployedServicesRegistry reg = DeployedServicesRegistry.getInstance();
        DeployableService service = reg.getService(serviceUUID);
        service.addInstance(instance);
    }
    
}
