package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;

public class IdlePoolFactory {

    public static IdlePool idlePoolForTesting;
    public static boolean testing;

    public IdlePool getNewIdlePool() {
        if (testing) {
            return idlePoolForTesting;
        } else {
            return loadPool();
        }
    }

    private IdlePool loadPool() {
        int poolSize = getPoolSize();
        int threshold = getThreshold();
        return IdlePool.getInstance(poolSize, threshold);
    }

    private int getPoolSize() {
        int poolSize = 0;
        try {
            poolSize = Integer.parseInt(DeploymentManagerConfiguration.get("IDLE_POOL_INITIAL_SIZE"));
        } catch (NumberFormatException e) {
            ; // no problem, poolSize is zero
        }
        return poolSize;
    }
    
    private int getThreshold() {
        int threshold = -1;
        try {
            threshold = Integer.parseInt(DeploymentManagerConfiguration.get("IDLE_POOL_THRESHOLD"));
        } catch (NumberFormatException e) {
            ; // no problem, threshold is -1
        }
        return threshold;
    }
    
}
