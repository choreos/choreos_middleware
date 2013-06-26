package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.services.datamodel.DeploymentRequest;
import org.ow2.choreos.services.datamodel.PackageType;

public class DeploymentPreparers {

    public static DeploymentPreparer getInstance(DeploymentRequest deploymentRequest) {
	PackageType packageType = deploymentRequest.getService().getSpec().getPackageType();
	switch (packageType) {
	case COMMAND_LINE:
	    return new JARDeploymentPreparer(deploymentRequest);
	case TOMCAT:
	    return new WARDeploymentPreparer(deploymentRequest);
	default:
	    throw new IllegalStateException("No DeploymentPreparer to " + packageType + " package type");
	}
    }

}
