package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.services.datamodel.PackageType;

public class PrepareDeploymentCommandFactory {

    public static PrepareDeploymentCommand getInstance(DeploymentRequest deploymentRequest) {
	PackageType packageType = deploymentRequest.getService().getSpec().getPackageType();
	switch (packageType) {
	case COMMAND_LINE:
	    return new JARDeploymentPreparerCommand(deploymentRequest);
	case TOMCAT:
	    return new WARDeploymentPreparerCommand(deploymentRequest);
	default:
	    throw new IllegalStateException("No DeploymentPreparer to " + packageType + " package type");
	}
    }

}
