package org.ow2.choreos.deployment.services.preparer;

public class WARDeploymentPreparerCommand implements PrepareDeploymentCommand {

    private DeploymentRequest deploymentRequest;
    
    public WARDeploymentPreparerCommand(DeploymentRequest deploymentRequest) {
	this.deploymentRequest = deploymentRequest;
    }

    @Override
    public String getCommand() {
	String packageUri = deploymentRequest.getService().getSpec().getPackageUri();
	return ". prepare_deployment.sh -war " + packageUri + " " + deploymentRequest.getDeploymentManagerURL();
    }
    
}
