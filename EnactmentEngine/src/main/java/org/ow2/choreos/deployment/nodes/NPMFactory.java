package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.nodes.NodePoolManager;

public class NPMFactory {

    public static NodePoolManager npmForTest;
    public static boolean testing = false;

    public static NodePoolManager getNewNPMInstance() {
        if (testing) {
            return npmForTest;
        } else {
            return new NPMImpl();
        }
    }
    
    public static NodePoolManager getNewNPMInstance(IdlePool idlePool) {
        if (testing) {
            return npmForTest;
        } else {
            return new NPMImpl(idlePool);
        }
    }
}
