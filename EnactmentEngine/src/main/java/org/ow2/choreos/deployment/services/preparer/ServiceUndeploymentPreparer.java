package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.services.datamodel.DeployableService;

public class ServiceUndeploymentPreparer {

    private DeployableService service;
    private int numberOfReplicasToUndeploy;

    public ServiceUndeploymentPreparer(DeployableService service, int decreaseAmount) {
	this.service = service;
	this.numberOfReplicasToUndeploy = decreaseAmount;
    }

    public void prepareUndeployment() throws PrepareUndeploymentFailedException {

	int delta = numberOfReplicasToUndeploy;

	if (delta < 0)
	    delta = 0;
	else if (numberOfReplicasToUndeploy > service.getServiceInstances().size())
	    delta = service.getServiceInstances().size();

	for (int i = 0; i < delta; i++) {
	    InstanceUndeploymentPreparer instanceUndeploymentPreparer = new InstanceUndeploymentPreparer(
		    service.getUUID(), service.getServiceInstances().get(i));
	    instanceUndeploymentPreparer.prepareUndeployment();
	}

    }

}
