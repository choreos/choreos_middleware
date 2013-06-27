package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodePoolManager;

public class NPMFactory {

    private static final String CLOUD_PROVIDER_PROPERTY = "CLOUD_PROVIDER";

    private static NodePoolManager npmForTest;
    private static boolean testing = false;
    
    public static NodePoolManager getNewNPMInstance() {
	if (testing) {
	    return npmForTest;
	} else {
	    String cloudProviderType = DeploymentManagerConfiguration.get(CLOUD_PROVIDER_PROPERTY);
	    return new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	}
    }
    
}
