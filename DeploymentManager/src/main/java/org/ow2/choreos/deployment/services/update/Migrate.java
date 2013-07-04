package org.ow2.choreos.deployment.services.update;

import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Migrate extends BaseAction {

    private static final String NAME = "Migrate instance";

    private DeployableService currentService;
    private DeployableServiceSpec newSpec;

    public Migrate(DeployableService currentService, DeployableServiceSpec newSpec) {
        this.currentService = currentService;
        this.newSpec = newSpec;
    }

    @Override
    public void applyUpdate() {
//        currentService.setSpec(newSpec);
//        currentService.getServiceInstances().clear();
//        migrateServiceInstances(currentService);
//        ServiceDeploymentPreparer deploymentPreparer = ServiceDeploymentPreparerFactory.getNewInstance(newSpec,
//                currentService.getUUID());
//        try {
//            deploymentPreparer.prepareDeployment();
//        } catch (PrepareDeploymentFailedException e) {
//            throw new UpdateActionFailedException();
//        }
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
