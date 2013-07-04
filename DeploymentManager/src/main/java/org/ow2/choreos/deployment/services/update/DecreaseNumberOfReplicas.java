package org.ow2.choreos.deployment.services.update;

import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DecreaseNumberOfReplicas extends BaseAction {

    private static final String NAME = "Decrease number of instances";

    private DeployableService currentService;
    private DeployableServiceSpec newSpec;

    public DecreaseNumberOfReplicas(DeployableService currentService, DeployableServiceSpec newSpec) {
        this.currentService = currentService;
        this.newSpec = newSpec;
    }

    @Override
    public void applyUpdate() {
//        int decreaseAmount = currentService.getSpec().getNumberOfInstances() - newSpec.getNumberOfInstances();
//        if (decreaseAmount < currentService.getServiceInstances().size()) {
//            for (int i = 0; i < decreaseAmount; i++) {
//                executeServiceInstanceUndeployment(currentService.getServiceInstances().get(0));
//                currentService.getServiceInstances().remove(0);
//            }
//        } else if (decreaseAmount < currentService.getServiceInstances().size()) {
//            try {
//                this.deleteService(currentService.getUUID());
//            } catch (ServiceNotDeletedException e) {
//                throw new UpdateActionFailedException();
//            }
//        }
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
