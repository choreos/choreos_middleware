package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.services.datamodel.DeploymentRequest;

public class WARDeploymentPreparer extends DeploymentPreparer {

    public WARDeploymentPreparer(DeploymentRequest deploymentRequest) {
	super(deploymentRequest);
    }

    @Override
    protected String getCommand() {
	String packageUri = deploymentRequest.getService().getSpec().getPackageUri();
	return ". prepare_deployment.sh -war " + packageUri + " " + deploymentRequest.getDeploymentManagerURL();
    }
    
}
