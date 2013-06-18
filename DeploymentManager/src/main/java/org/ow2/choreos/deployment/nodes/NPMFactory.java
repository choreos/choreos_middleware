package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NPMStub;
import org.ow2.choreos.nodes.NodePoolManager;

public class NPMFactory {

    private static final String TESTING_PROPERTY = "TESTING";
    private static final String CLOUD_PROVIDER_PROPERTY = "CLOUD_PROVIDER";

    /**
     * The CloudProvider used is the one configured in the properties file. 
     * If TESTING=true on properties, NPMStub is returned.
     * 
     * @return
     */
    public static NodePoolManager getNewNPMInstance() {
	Boolean testing = Boolean.parseBoolean(DeploymentManagerConfiguration.get(TESTING_PROPERTY));
	if (testing) {
	    return new NPMStub();
	} else {
	    String cloudProviderType = DeploymentManagerConfiguration.get(CLOUD_PROVIDER_PROPERTY);
	    return new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	}
    }
    
}
