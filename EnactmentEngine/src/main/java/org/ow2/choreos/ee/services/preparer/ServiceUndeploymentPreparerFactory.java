package org.ow2.choreos.ee.services.preparer;

import org.ow2.choreos.services.datamodel.DeployableService;

public class ServiceUndeploymentPreparerFactory {

    public static boolean testing = false;
    public static ServiceUndeploymentPreparer preparerForTest;

    public static ServiceUndeploymentPreparer getNewInstance(DeployableService service, int decreaseAmount) {
	if (!testing)
	    return new ServiceUndeploymentPreparer(service, decreaseAmount);
	else
	    return preparerForTest;
    }

}
