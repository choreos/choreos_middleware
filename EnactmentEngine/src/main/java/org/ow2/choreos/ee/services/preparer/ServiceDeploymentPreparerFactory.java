package org.ow2.choreos.ee.services.preparer;

import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ServiceDeploymentPreparerFactory {
    
    public static boolean testing = false;
    public static ServiceDeploymentPreparer preparerForTest;

    public static ServiceDeploymentPreparer getNewInstance(DeployableServiceSpec spec, String serviceUUID) {
        if (!testing)
            return new ServiceDeploymentPreparer(spec, serviceUUID);
        else
            return preparerForTest;
    }

}
