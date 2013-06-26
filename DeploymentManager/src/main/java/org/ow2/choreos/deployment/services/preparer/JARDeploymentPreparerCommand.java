package org.ow2.choreos.deployment.services.preparer;


public class JARDeploymentPreparerCommand implements PrepareDeploymentCommand {

    private DeploymentRequest deploymentRequest;
    
    public JARDeploymentPreparerCommand(DeploymentRequest deploymentRequest) {
	this.deploymentRequest = deploymentRequest;
    }

    @Override
    public String getCommand() {
	String packageUri = deploymentRequest.getService().getSpec().getPackageUri();
	return ". prepare_deployment.sh -jar " + packageUri + " " + deploymentRequest.getDeploymentManagerURL();
    }

}
