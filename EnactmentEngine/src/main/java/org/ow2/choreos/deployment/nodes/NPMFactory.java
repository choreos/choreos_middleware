package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.deployment.CloudConfiguration;
import org.ow2.choreos.nodes.NodePoolManager;

public class NPMFactory {

    public static NodePoolManager npmForTest;
    public static boolean testing = false;

    public static NodePoolManager getNewNPMInstance(String owner) {
	if (testing) {
	    return npmForTest;
	} else {
	    CloudConfiguration cloudConfiguration = getCloudConfiguration(owner);
	    return new NPMImpl(cloudConfiguration);
	}
    }

    private static CloudConfiguration getCloudConfiguration(String owner) {
	CloudConfiguration cloudConfiguration = null;
	if (owner == null || owner.isEmpty()) {
	    cloudConfiguration = CloudConfiguration.getCloudConfigurationInstance();
	} else {
	    cloudConfiguration = CloudConfiguration.getCloudConfigurationInstance(owner);
	}
	return cloudConfiguration;
    }

    public static NodePoolManager getNewNPMInstance(String owner, IdlePool idlePool) {
	if (testing) {
	    return npmForTest;
	} else {
	    CloudConfiguration cloudConfiguration = getCloudConfiguration(owner);
	    return new NPMImpl(cloudConfiguration, idlePool);
	}
    }
}
