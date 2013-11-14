package org.ow2.choreos.deployment.nodes;

public class NodeCreatorFactory {

    public static NodeCreator nodeCreatorForTesting;
    public static boolean testing;
    
    public NodeCreator getNewNodeCreator() {
        if (testing) {
            return nodeCreatorForTesting;
        } else {
            return new NodeCreator();
        }
    }

}
