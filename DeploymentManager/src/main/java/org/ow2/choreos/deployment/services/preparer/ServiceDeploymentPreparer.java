package org.ow2.choreos.deployment.services.preparer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.selector.NodeSelector;
import org.ow2.choreos.deployment.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class ServiceDeploymentPreparer {

    private DeploymentRequest deploymentRequest;
    private String serviceName;
    private List<CloudNode> nodes;

    private Logger logger = Logger.getLogger(ServiceDeploymentPreparer.class);

    public ServiceDeploymentPreparer(DeploymentRequest deploymentRequest) {
        this.deploymentRequest = deploymentRequest;
        this.serviceName = deploymentRequest.getService().getSpec().getName();
    }

    public void prepareDeployment() throws PrepareDeploymentFailedException {
        selectNodes();
        prepareInstances();
    }

    private void selectNodes() throws PrepareDeploymentFailedException {
        NodeSelector selector = NodeSelectorFactory.getFactoryInstance().getNodeSelectorInstance();
        try {
            nodes = selector.select(deploymentRequest, deploymentRequest.getNumberOfInstances());
            logger.info("Selected nodes to " + serviceName + ": " + nodes);
        } catch (NotSelectedException e) {
            failDeployment();
        }
        if (nodes == null || nodes.isEmpty()) {
            failDeployment();
        }
    }

    private void failDeployment() throws PrepareDeploymentFailedException {
        throw new PrepareDeploymentFailedException(serviceName);
    }

    private void prepareInstances() {
        List<ServiceInstance> instances = (deploymentRequest.getService().getServiceInstances() != null) ? deploymentRequest
                .getService().getServiceInstances() : new ArrayList<ServiceInstance>();

        for (CloudNode node : nodes) {
            try {
                InstanceDeploymentPreparer instanceDeploymentPreparer = new InstanceDeploymentPreparer(
                        deploymentRequest, node);
                String instanceId = instanceDeploymentPreparer.prepareDeployment();
                ServiceInstance instance = new ServiceInstance(node);
                instance.setServiceSpec(deploymentRequest.getService().getSpec());
                instance.setInstanceId(instanceId);
                instances.add(instance);
            } catch (PrepareDeploymentFailedException e) {
                logger.error(e.getMessage());
            }
        }

        deploymentRequest.getService().setServiceInstances(instances);
    }

}
