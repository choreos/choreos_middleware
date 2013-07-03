package org.ow2.choreos.deployment.services.update;

import java.util.Set;

import org.ow2.choreos.deployment.services.preparer.PrepareDeploymentFailedException;
import org.ow2.choreos.deployment.services.preparer.ServiceDeploymentPreparer;
import org.ow2.choreos.deployment.services.preparer.ServiceDeploymentPreparerFactory;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class IncreaseNumberOfReplicas extends BaseAction {

    private static final String NAME = "Increase number of replicas";

    private DeployableService currentService;
    private DeployableServiceSpec newSpec;

    public IncreaseNumberOfReplicas(DeployableService currentService, DeployableServiceSpec newSpec) {
        this.currentService = currentService;
        this.newSpec = newSpec;
    }

    @Override
    public void applyUpdate() throws UpdateActionFailedException {
        int increaseAmount = newSpec.getNumberOfInstances() - currentService.getSpec().getNumberOfInstances();
        DeployableServiceSpec deltaSpec = newSpec.clone(); 
        deltaSpec.setNumberOfInstances(increaseAmount);
        ServiceDeploymentPreparer deploymentPreparer = ServiceDeploymentPreparerFactory.getNewInstance(deltaSpec,
                currentService.getUUID());
        try {
            Set<CloudNode> nodes = deploymentPreparer.prepareDeployment();
            currentService.setSpec(newSpec);
            currentService.getSelectedNodes().addAll(nodes);
        } catch (PrepareDeploymentFailedException e) {
            throw new UpdateActionFailedException();
        }
    }
    
    @Override
    public String getName() {
        return NAME;
    }

}
