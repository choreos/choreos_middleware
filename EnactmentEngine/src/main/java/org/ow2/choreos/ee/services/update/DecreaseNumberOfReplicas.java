package org.ow2.choreos.ee.services.update;

import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparer;
import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparerFactory;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class DecreaseNumberOfReplicas extends BaseAction {

    private static final String NAME = "Decrease number of instances";

    private DeployableService currentService;
    private DeployableServiceSpec newSpec;

    public DecreaseNumberOfReplicas(DeployableService currentService, DeployableServiceSpec newSpec) {
	this.currentService = currentService;
	this.newSpec = newSpec;
    }

    @Override
    public void applyUpdate() throws UpdateActionFailedException {
	int decreaseAmount = currentService.getSpec().getNumberOfInstances() - newSpec.getNumberOfInstances();

	ServiceUndeploymentPreparer undeploymentPreparer = ServiceUndeploymentPreparerFactory.getNewInstance(
		currentService, decreaseAmount);

	try {
	    undeploymentPreparer.prepareUndeployment();
	    currentService.setSpec(newSpec);

	} catch (Exception e) {
	    throw new UpdateActionFailedException();
	}
    }

    @Override
    public String getName() {
	return NAME;
    }

}
