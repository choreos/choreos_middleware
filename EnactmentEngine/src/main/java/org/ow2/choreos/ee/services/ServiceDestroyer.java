package org.ow2.choreos.ee.services;

import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparer;
import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparerFactory;
import org.ow2.choreos.ee.services.update.UpdateActionFailedException;
import org.ow2.choreos.services.ServiceNotDeletedException;
import org.ow2.choreos.services.datamodel.DeployableService;

public class ServiceDestroyer {

    public void deleteService(DeployableService service) throws ServiceNotDeletedException {
	try {
	    this.executeUndeployment(service);
	} catch (UpdateActionFailedException e) {
	    throw new ServiceNotDeletedException(service.getUUID());
	}
    }

    private void executeUndeployment(DeployableService service) throws UpdateActionFailedException {
	ServiceUndeploymentPreparer undeploymentPreparer = ServiceUndeploymentPreparerFactory.getNewInstance(service,
		service.getServiceInstances().size());

	try {
	    undeploymentPreparer.prepareUndeployment();
	} catch (Exception e) {
	    throw new UpdateActionFailedException();
	}
    }
}