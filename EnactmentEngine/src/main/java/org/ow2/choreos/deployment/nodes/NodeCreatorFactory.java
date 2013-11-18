package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.deployment.CloudConfiguration;

public class NodeCreatorFactory {

    public static NodeCreator nodeCreatorForTesting;
    public static boolean testing;

    public NodeCreator getNewNodeCreator(CloudConfiguration cloudConfiguration) {
	if (testing) {
	    return nodeCreatorForTesting;
	} else {
	    return new NodeCreator(cloudConfiguration);
	}
    }

}
