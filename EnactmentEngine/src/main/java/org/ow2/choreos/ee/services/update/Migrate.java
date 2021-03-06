package org.ow2.choreos.ee.services.update;

import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class Migrate extends BaseAction {

    private static final String NAME = "Migrate instance";

    @SuppressWarnings("unused")
    private DeployableService currentService;
    @SuppressWarnings("unused")
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
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
